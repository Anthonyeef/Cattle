package io.github.anthonyeef.cattle.Oauth;

import android.util.Log;

import org.oauthsimple.builder.ServiceBuilder;
import org.oauthsimple.builder.api.FanfouApi;
import org.oauthsimple.http.OAuthRequest;
import org.oauthsimple.http.Response;
import org.oauthsimple.model.OAuthToken;
import org.oauthsimple.model.SignatureType;
import org.oauthsimple.oauth.OAuthService;

import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;

import io.github.anthonyeef.cattle.BuildConfig;
import io.github.anthonyeef.cattle.Exception.ApiException;



/**
 *
 */

final class FanFouApi implements Api {
    private static final String TAG = "API";
    private static final String API_HOST = "http://api.fanfou.com";
    private static final String API_KEY = BuildConfig.API_KEY;
    private static final String API_SECRET = BuildConfig.API_SECRET;
    private static final String CALLBACK_URL = "http://m.fanfou.com";

    private static final boolean DEBUG = true/*AppContext.DEBUG*/;
    private OAuthService mOAuthService;
    private OAuthToken mAccessToken;
    private ApiParser mParser;
    private String account;

    public FanFouApi() {
        initialize(null);
    }

    public FanFouApi(OAuthToken token) {
        initialize(token);
    }

    private void initialize(OAuthToken token) {
        this.mParser = ApiFactory.getDefaultParser();
        this.mOAuthService = buildOAuthService(null);
    }

    private OAuthService buildOAuthService(OAuthToken token) {
        ServiceBuilder builder = new ServiceBuilder().apiKey(API_KEY)
                .apiSecret(API_SECRET).callback(CALLBACK_URL)
                .provider(FanfouApi.class)
                .signatureType(SignatureType.HEADER_OAUTH);
        if (DEBUG) {
            builder.debug().debugStream(new PrintStream(System.out));
        }
        return builder.build();
    }

    private void debug(String message) {
        Log.d(TAG, message);
    }

    private String makeUrl(String url) {
        return new StringBuilder().append(API_HOST).append(url).append(".json")
                .toString();
    }

    @Override
    public String getAccount() {
        return account;
    }

    @Override
    public void setAccount(String account) {
        this.account = account;
        if (mParser != null) {
            this.mParser.setAccount(account);
        }
    }
    @Override
    public OAuthToken getOAuthRequestToken() throws ApiException {
        try {
            return mOAuthService.getRequestToken();
        } catch (Exception e) {
            throw new ApiException(ApiException.IO_ERROR, e.getMessage(), e);
        }
    }

    @Override
    public OAuthToken getOAuthAccessToken(String username, String password)
            throws IOException, ApiException {
        try {
            return mOAuthService.getAccessToken(username, password);
        } catch (Exception e) {
            throw new ApiException(ApiException.IO_ERROR, e.getMessage(), e);
        }
    }

    @Override
    public void setAccessToken(OAuthToken token) {
        this.mAccessToken = token;
    }

    private String utf8Encode(String text) {
        try {
            return URLEncoder.encode(text, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return text;
        }
    }

    /**
     * @param builder
     * @return
     * @throws ApiException
     */
    private String fetch(final RequestBuilder builder) throws ApiException {
        OAuthRequest request = builder.build();
        request.setConnectTimeout(5, TimeUnit.SECONDS);
        request.setReadTimeout(10, TimeUnit.SECONDS);
        try {
            if (mOAuthService != null && mAccessToken != null) {
                mOAuthService.signRequest(mAccessToken, request);
            }

            Response response = request.send();
            int statusCode = response.getCode();
            String body = response.getBody();
            if (DEBUG) {
                debug("fetch() statusCode=" + statusCode + " builder=" + builder);
            }
            if (statusCode >= 200 && statusCode < 300) {
                return body;
            }
            throw new ApiException(statusCode, FanFouParser.error(body));
        } catch (IOException e) {
            if (DEBUG) {
                Log.e(TAG, e.toString());
            }
            throw new ApiException(ApiException.IO_ERROR, e.toString(),
                    e);
        }
    }

}
