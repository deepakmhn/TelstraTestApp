package com.telstra.testapp.repository

import com.telstra.testapp.data.Resource
import com.telstra.testapp.service.FactsResponse
import com.telstra.testapp.util.NetManager
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository which provides data stream to the ViewModel
 */

@Singleton
class FactsRepository @Inject constructor(private var netManager: NetManager, private var dataSource: FactsDataSource) {

    fun getFactsData(): Observable<Resource<FactsResponse>> {
        return if (netManager.isConnectedToInternet == true) {
            dataSource.getFactsData()
        } else {
            Observable.just(Resource.error(null))
        }
    }
}