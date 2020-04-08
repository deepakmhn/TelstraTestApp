package com.telstra.testapp.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockito_kotlin.verify
import com.telstra.testapp.data.FactDetail
import com.telstra.testapp.data.Resource
import com.telstra.testapp.service.FactsResponse
import com.telstra.testapp.util.NetManager
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class FactsRepositoryTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val netManager = Mockito.mock(NetManager::class.java)
    private val dataSource = Mockito.mock(FactsRemoteDataSource::class.java)
    private var factsRepository = FactsRepository(netManager, dataSource)
    private lateinit var factsResponse: FactsResponse

    @Test
    fun getTopPlayerStatDetail_SuccessfulWhenNetworkConnected() {
        Mockito.`when`(netManager.isConnectedToInternet).thenReturn(true)
        Mockito.`when`(dataSource.getFactsData()).thenReturn(getFactsResponseWithData())
        val testObserver = TestObserver<Resource<FactsResponse>>()
        factsRepository.getFactsData().subscribe(testObserver)

        verify(dataSource).getFactsData()
        testObserver.assertResult(Resource.success(factsResponse))
    }

    @Test
    fun getTopPlayerStatDetail_ErrorWhenObserved() {
        Mockito.`when`(netManager.isConnectedToInternet).thenReturn(false)
        Mockito.`when`(dataSource.getFactsData()).thenReturn(getFactsResponseWithData())
        val testObserver = TestObserver<Resource<FactsResponse>>()
        factsRepository.getFactsData().subscribe(testObserver)

        verify(dataSource, Mockito.never()).getFactsData()
        testObserver.assertResult(Resource.error(null))
    }

    private fun getFactsResponseWithData(): Observable<Resource<FactsResponse>> {
        val factDetail1 = FactDetail("Title1", "Description1", "testUrl1")
        val factDetail2 = FactDetail("Title2", "Description2", "testUrl2")
        val factDetail3 = FactDetail("Title3", "Description3", "testUrl3")
        factsResponse = FactsResponse("Page title", listOf(factDetail1, factDetail2, factDetail3))
        return Observable.just(Resource.success(factsResponse))
    }
}