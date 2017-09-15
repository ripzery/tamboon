package com.ripzery.tamboon.Network

import com.ripzery.tamboon.data.Tamboon
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Created by ripzery on 9/15/17.
 */
interface TamboonApiService {
    @GET("./")
    fun getCharities(): Observable<List<Tamboon.Charity>>

    @POST("donate")
    fun donate(@Body mRequest: Tamboon.DonateRequest): Observable<Tamboon.DonateResponse>
}