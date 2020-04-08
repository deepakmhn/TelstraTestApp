package com.telstra.testapp.repository

import com.telstra.testapp.data.Resource
import com.telstra.testapp.service.FactsResponse
import com.telstra.testapp.service.FactsService
import io.reactivex.Observable
import javax.inject.Inject

class FactsRemoteDataSource @Inject constructor(private val service: FactsService) : FactsDataSource {

    override fun getFactsData(): Observable<Resource<FactsResponse>> {
        return service.getFacts().map { response ->
            if (response.isSuccessful) {
                response.body()?.let { Resource.success(it) } ?: Resource.error(null)
            } else {
                Resource.error(null)
            }
        }
    }

}