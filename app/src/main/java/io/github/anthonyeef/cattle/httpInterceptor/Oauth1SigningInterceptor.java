package io.github.anthonyeef.cattle.httpInterceptor;

/*
 * Copyright (C) 2015 Jake Wharton
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import io.github.anthonyeef.cattle.utils.StringUtils;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import okio.ByteString;

public final class Oauth1SigningInterceptor implements Interceptor {
    private static final String OAUTH_CONSUMER_KEY = "oauth_consumer_key";
    private static final String OAUTH_NONCE = "oauth_nonce";
    private static final String OAUTH_SIGNATURE = "oauth_signature";
    private static final String OAUTH_SIGNATURE_METHOD = "oauth_signature_method";
    private static final String OAUTH_SIGNATURE_METHOD_VALUE = "HMAC-SHA1";
    private static final String OAUTH_TIMESTAMP = "oauth_timestamp";
    private static final String OAUTH_ACCESS_TOKEN = "oauth_token";

    private final String consumerKey;
    private final String consumerSecret;
    private final String accessToken;
    private final String accessSecret;
    private final Random random;
    private final Clock clock;

    private Oauth1SigningInterceptor(String consumerKey, String consumerSecret, String accessToken,
                                     String accessSecret, Random random, Clock clock) {
        this.consumerKey = consumerKey;
        this.consumerSecret = consumerSecret;
        this.accessToken = accessToken;
        this.accessSecret = accessSecret;
        this.random = random;
        this.clock = clock;
    }


    @Override public Response intercept(Chain chain) throws IOException {
        return chain.proceed(signRequest(chain.request()));
    }

    public Request signRequest(Request request) throws IOException {
        byte[] nonce = new byte[32];
        random.nextBytes(nonce);
        String oauthNonce = ByteString.of(nonce).base64().replaceAll("\\W", "");
        String oauthTimestamp = clock.millis();

        String consumerKeyValue = UrlEscapeUtils.escape(consumerKey);

        SortedMap<String, String> parameters = new TreeMap<>();
        parameters.put(OAUTH_CONSUMER_KEY, consumerKeyValue);
        parameters.put(OAUTH_NONCE, oauthNonce);
        parameters.put(OAUTH_SIGNATURE_METHOD, OAUTH_SIGNATURE_METHOD_VALUE);
        parameters.put(OAUTH_TIMESTAMP, oauthTimestamp);

        String accessTokenValue = null;
        if (!StringUtils.isEmptyOrNull(accessToken)) {
            accessTokenValue = UrlEscapeUtils.escape(accessToken);
            parameters.put(OAUTH_ACCESS_TOKEN, accessTokenValue);
        }

        HttpUrl url = request.url();
        for (int i = 0; i < url.querySize(); i++) {
            parameters.put(UrlEscapeUtils.escape(url.queryParameterName(i)),
                    UrlEscapeUtils.escape(url.queryParameterValue(i)));
        }

        Buffer body = new Buffer();

        RequestBody requestBody = request.body();
        if (requestBody != null) {
            requestBody.writeTo(body);
        }

        while (!body.exhausted()) {
            long keyEnd = body.indexOf((byte) '=');
            if (keyEnd == -1) throw new IllegalStateException("Key with no value: " + body.readUtf8());
            String key = body.readUtf8(keyEnd);
            body.skip(1); // Equals.

            long valueEnd = body.indexOf((byte) '&');
            String value = valueEnd == -1 ? body.readUtf8() : body.readUtf8(valueEnd);
            if (valueEnd != -1) body.skip(1); // Ampersand.

            parameters.put(key, value);
        }

        Buffer base = new Buffer();
        String method = request.method();
        base.writeUtf8(method);
        base.writeByte('&');
        base.writeUtf8(
                UrlEscapeUtils.escape(request.url().newBuilder().query(null).build().toString()));
        base.writeByte('&');

        boolean first = true;
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            if (!first) base.writeUtf8(UrlEscapeUtils.escape("&"));
            first = false;
            base.writeUtf8(UrlEscapeUtils.escape(entry.getKey()));
            base.writeUtf8(UrlEscapeUtils.escape("="));
            base.writeUtf8(UrlEscapeUtils.escape(entry.getValue()));
        }

        String signingKey =
                UrlEscapeUtils.escape(consumerSecret) + "&" + UrlEscapeUtils.escape(accessSecret);

        SecretKeySpec keySpec = new SecretKeySpec(signingKey.getBytes(), "HmacSHA1");
        Mac mac;
        try {
            mac = Mac.getInstance("HmacSHA1");
            mac.init(keySpec);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new IllegalStateException(e);
        }

        byte[] result = mac.doFinal(base.readByteArray());
        String signature = ByteString.of(result).base64();

        // 这里判断了 accessTokenValue 是否为空，
        // 是因为第一次换取 requestToken 时签名不带有 accessToken
        String authorization;
        if (!StringUtils.isEmptyOrNull(accessTokenValue)) {
            authorization =
                "OAuth " + OAUTH_CONSUMER_KEY + "=\"" + consumerKeyValue + "\", " + OAUTH_NONCE + "=\""
                        + oauthNonce + "\", " + OAUTH_SIGNATURE + "=\"" + UrlEscapeUtils.escape(signature)
                        + "\", " + OAUTH_SIGNATURE_METHOD + "=\"" + OAUTH_SIGNATURE_METHOD_VALUE + "\", "
                        + OAUTH_TIMESTAMP + "=\"" + oauthTimestamp + "\", " + OAUTH_ACCESS_TOKEN + "=\""
                        + accessTokenValue + "\"";
        } else {
            authorization =
                "OAuth " + OAUTH_CONSUMER_KEY + "=\"" + consumerKeyValue + "\", " + OAUTH_NONCE + "=\""
                        + oauthNonce + "\", " + OAUTH_SIGNATURE + "=\"" + UrlEscapeUtils.escape(signature)
                        + "\", " + OAUTH_SIGNATURE_METHOD + "=\"" + OAUTH_SIGNATURE_METHOD_VALUE + "\", "
                        + OAUTH_TIMESTAMP + "=\"" + oauthTimestamp + "\"";
        }

        return request.newBuilder().addHeader("Authorization", authorization).build();
    }

    public boolean needUpdate(String token, String secret) {
        return !Objects.equals(accessToken, token) || !Objects.equals(accessSecret, secret);
    }

    public Oauth1SigningInterceptor update(String token, String secret) {
        if (!Objects.equals(accessToken, token)) {
             return new Builder()
                    .consumerKey(consumerKey)
                    .consumerSecret(consumerSecret)
                    .accessToken(token)
                    .accessSecret(secret)
                    .build();
        }
        return this;
    }

    public static final class Builder {
        private String consumerKey;
        private String consumerSecret;
        private String accessToken;
        private String accessSecret;
        private Random random = new SecureRandom();
        private Clock clock = new Clock();

        public Builder consumerKey(String consumerKey) {
            if (consumerKey == null) throw new NullPointerException("consumerKey = null");
            this.consumerKey = consumerKey;
            return this;
        }

        public Builder consumerSecret(String consumerSecret) {
            if (consumerSecret == null) throw new NullPointerException("consumerSecret = null");
            this.consumerSecret = consumerSecret;
            return this;
        }

        public Builder accessToken(String accessToken) {
            if (accessToken == null) throw new NullPointerException("accessToken == null");
            this.accessToken = accessToken;
            return this;
        }

        public Builder accessSecret(String accessSecret) {
            if (accessSecret == null) throw new NullPointerException("accessSecret == null");
            this.accessSecret = accessSecret;
            return this;
        }

        public Builder random(Random random) {
            if (random == null) throw new NullPointerException("random == null");
            this.random = random;
            return this;
        }

        public Builder clock(Clock clock) {
            if (clock == null) throw new NullPointerException("clock == null");
            this.clock = clock;
            return this;
        }

        public Oauth1SigningInterceptor build() {
            if (consumerKey == null) throw new IllegalStateException("consumerKey not set");
            if (consumerSecret == null) throw new IllegalStateException("consumerSecret not set");
            if (accessToken == null) accessToken = "";
            if (accessSecret == null) accessSecret = "";
            return new Oauth1SigningInterceptor(consumerKey, consumerSecret, accessToken, accessSecret,
                    random, clock);
        }
    }

    /** Simple clock like class, to allow time mocking. */
    static class Clock {
        /** Returns the current time in milliseconds divided by 1K. */
        public String millis() {
            return Long.toString(System.currentTimeMillis() / 1000L);
        }
    }
}
