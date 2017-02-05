package io.github.anthonyeef.cattle.service

import io.github.anthonyeef.cattle.BuildConfig
import io.github.anthonyeef.cattle.constant.FanfouApiBaseUrl
import io.github.anthonyeef.cattle.constant.KEY_ACCESS_TOKEN
import io.github.anthonyeef.cattle.constant.KEY_ACCESS_TOKEN_SECRET
import io.github.anthonyeef.cattle.httpInterceptor.Oauth1SigningInterceptor
import io.github.anthonyeef.cattle.utils.SharedPreferenceUtils
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

/**
 * Network service generator
 */
object ServiceGenerator {
    private var logging = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)// default level

    private var sBuilder = Retrofit.Builder().baseUrl(FanfouApiBaseUrl)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())

    private var httpClient = OkHttpClient
            .Builder()
            .addNetworkInterceptor(logging)

    /**
     * Method to create a service for given service class
     * with default token and secret
     */
    fun <S> createDefaultService(serviceClass: Class<S>): S {
        return createService(serviceClass, SharedPreferenceUtils.getString(KEY_ACCESS_TOKEN),
                SharedPreferenceUtils.getString(KEY_ACCESS_TOKEN_SECRET))
    }

    /**
     * Method to create a service for given service class
     * with given token and secret
     *
     * @param token
     * @param secret
     *
     * @return service
     */
    fun <S> createService(serviceClass: Class<S>, token: String, secret: String): S {
        if (token.isNotEmpty().and(secret.isNotEmpty())) {
            val signingInterceptor = Oauth1SigningInterceptor.Builder()
                    .consumerKey(BuildConfig.API_KEY)
                    .consumerSecret(BuildConfig.API_SECRET)
                    .accessToken(token)
                    .accessSecret(secret)
                    .build()
            if (!httpClient.networkInterceptors().contains(signingInterceptor)) {
                httpClient.addNetworkInterceptor(signingInterceptor)
            }
            return createService(serviceClass)
        } else {
            val signingInterceptor = Oauth1SigningInterceptor.Builder()
                .consumerKey(BuildConfig.API_KEY)
                .consumerSecret(BuildConfig.API_SECRET)
                .build()
            val tempClient = OkHttpClient.Builder()
                    .addNetworkInterceptor(logging)
                    .addNetworkInterceptor(signingInterceptor).build()
            return createService(serviceClass, tempClient)
        }
    }

    /**
     * Method to create a service for given service class
     * with customize client.
     *
     * @param serviceClass
     * @param client
     *
     * @return service
     */
    fun <S> createService(serviceClass: Class<S>, client: OkHttpClient): S {
        return sBuilder.client(client).build().create(serviceClass)
    }

    /**
     * Method to create a service for given service class
     *
     * @param serviceClass
     * @return target service
     */
    fun <S> createService(serviceClass: Class<S>): S {
        if (!httpClient.networkInterceptors().contains(logging)) {
            httpClient.addNetworkInterceptor(logging)
        }
        sBuilder.client(httpClient.build())
        val retrofit = sBuilder.build()
        return retrofit.create(serviceClass)
    }
}
