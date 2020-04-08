package com.telstra.testapp.service

import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET

/**
 * Retrofit service interface used to fetch data from the REST service
 */
interface FactsService {
    @GET("/s/2iodh4vg0eortkl/facts.json")
    fun getFacts(): Observable<Response<FactsResponse>>

}