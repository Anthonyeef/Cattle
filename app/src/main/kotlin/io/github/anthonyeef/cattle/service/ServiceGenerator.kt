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
    fun <S> createService(serviceClass: Class<S>, token: String = "", secret: String = ""): S {
        val signingList = httpClient.networkInterceptors().filterIsInstance(Oauth1SigningInterceptor::class.java)
        when (signingList.size) {
            0 -> {
                addNewSigningInterceptor(token, secret)
            }
            else -> {
                val interceptor = signingList.last()
                if (interceptor.needUpdate(token, secret)) {
                    clearSigningInterceptor()
                    addNewSigningInterceptor(token, secret)
                }
            }
        }
        return createService(serviceClass)
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

    // TODO: create NewBuilder() method for it
    private fun addNewSigningInterceptor(token: String, secret: String) {
        val signingInterceptor = Oauth1SigningInterceptor.Builder()
                .consumerKey(BuildConfig.API_KEY)
                .consumerSecret(BuildConfig.API_SECRET)
                .accessToken(token)
                .accessSecret(secret)
                .build()
        httpClient.addNetworkInterceptor(signingInterceptor)
    }

    private fun clearSigningInterceptor() {
        httpClient.networkInterceptors()
                .filterIsInstance<Oauth1SigningInterceptor>()
                .forEach { httpClient.networkInterceptors().remove(it) }
    }
}
