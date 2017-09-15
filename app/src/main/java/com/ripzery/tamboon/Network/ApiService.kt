package com.ripzery.tamboon.Network

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.ripzery.tamboon.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by ripzery on 9/15/17.
 */
object ApiService {
    private const val TAMBOON_BASE_URL = BuildConfig.tamboonBaseUrl
    val mTamboonApiClient: TamboonApiService by lazy {
        Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(TAMBOON_BASE_URL)
                .build().create(TamboonApiService::class.java)
    }
}