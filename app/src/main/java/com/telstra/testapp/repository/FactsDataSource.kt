package com.telstra.testapp.repository

import com.telstra.testapp.data.Resource
import com.telstra.testapp.service.FactsResponse
import io.reactivex.Observable

interface FactsDataSource {
    fun getFactsData(): Observable<Resource<FactsResponse>>
}