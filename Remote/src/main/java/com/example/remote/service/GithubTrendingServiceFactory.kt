package com.example.remote.service

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.util.concurrent.TimeUnit

class GithubTrendingServiceFactory{

    private fun makeGithubTrendingService(isDebug: Boolean): GithubTrendingService{
        val okHttpClient = makeOkHttpClient(makeLoggingIntercaptor(isDebug))
        return makeGithubTrendingService(okHttpClient)
    }

    private fun makeGithubTrendingService(okHttpClient: OkHttpClient): GithubTrendingService{
        val retrofit = Retrofit.Builder()
            .baseUrl("")
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        return retrofit.create(GithubTrendingService::class.java)
    }

    private fun makeOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(120, TimeUnit.SECONDS)
            .callTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    private fun makeLoggingIntercaptor(isDebug:Boolean): HttpLoggingInterceptor{
        val logging = HttpLoggingInterceptor()
        logging.level = if(isDebug){
            HttpLoggingInterceptor.Level.BODY
        } else{
            HttpLoggingInterceptor.Level.NONE
        }
        return logging
    }
}