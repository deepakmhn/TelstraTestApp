package com.telstra.testapp.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockito_kotlin.verify
import com.telstra.testapp.data.FactDetail
import com.telstra.testapp.data.Resource
import com.telstra.testapp.service.FactsResponse
import com.telstra.testapp.service.FactsService
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import okhttp3.ResponseBody
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import retrofit2.Response

class FactsRemoteDataSourceTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val service = Mockito.mock(FactsService::class.java)
    private var dataSource = FactsRemoteDataSource(service)
    private lateinit var factsResponse: FactsResponse


    @Test
    fun getFactsResponse_SuccessfulWhenDataAvailable() {
        Mockito.`when`(service.getFacts()).thenReturn(getFactsResponseWithData())
        val testObserver = TestObserver<Resource<FactsResponse>>()
        dataSource.getFactsData().subscribe(testObserver)

        verify(service).getFacts()
        testObserver.assertNoErrors().assertResult(Resource.success(factsResponse))
    }

    @Test
    fun getTopPlayerStatDetail_ErrorWhenDataNotAvailable() {
        Mockito.`when`(service.getFacts())
            .thenReturn(Observable.just(Response.error(404, Mockito.mock(ResponseBody::class.java))))
        val testObserver = TestObserver<Resource<FactsResponse>>()
        dataSource.getFactsData().subscribe(testObserver)

        verify(service).getFacts()
        testObserver.assertNoErrors().assertResult(Resource.error(null))
    }

    private fun getFactsResponseWithData(): Observable<Response<FactsResponse>> {
        val factDetail1 = FactDetail("Title1", "Description1", "testUrl1")
        val factDetail2 = FactDetail("Title2", "Description2", "testUrl2")
        val factDetail3 = FactDetail("Title3", "Description3", "testUrl3")
        factsResponse = FactsResponse("Page title", listOf(factDetail1, factDetail2, factDetail3))
        return Observable.just(Response.success(factsResponse))
    }

}