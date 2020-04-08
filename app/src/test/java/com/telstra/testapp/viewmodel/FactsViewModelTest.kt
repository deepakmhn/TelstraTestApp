package com.telstra.testapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.telstra.testapp.AppSchedulers
import com.telstra.testapp.data.FactDetail
import com.telstra.testapp.data.Resource
import com.telstra.testapp.repository.FactsRepository
import com.telstra.testapp.service.FactsResponse
import io.reactivex.Observable
import io.reactivex.schedulers.TestScheduler
import org.assertj.core.api.Assertions
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class FactsViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val repository = Mockito.mock(FactsRepository::class.java)
    private val testScheduler = TestScheduler()
    private val appSchedulers = AppSchedulers(testScheduler, testScheduler, testScheduler)
    private var factsViewModel = FactsViewModel(repository, appSchedulers)

    @Test
    fun loadStatDetailList_SuccessfulWhenObserved() {
        Mockito.`when`(repository.getFactsData()).thenReturn(getFactsResponseWithData())
        val observer = mock<Observer<Resource<FactsResponse>>>()
        factsViewModel.factsData.observeForever(observer)
        factsViewModel.loadFactsData()

        testScheduler.triggerActions()
        Mockito.verify(repository).getFactsData()

        val argumentCaptor = argumentCaptor<Resource<FactsResponse>>()
        argumentCaptor.run {
            verify(observer, times(2)).onChanged(capture())
            val (loadingState, successState) = allValues
            assertEquals(loadingState, Resource.loading(null))
            val factsTitle = successState.data?.title
            Assertions.assertThat(factsTitle).isEqualTo("Page title")
            val factsList = successState.data?.items
            Assertions.assertThat(factsList?.size).isEqualTo(3)
            Assertions.assertThat(factsList?.get(0)?.title).isEqualTo("Title1")
            Assertions.assertThat(factsList?.get(0)?.description).isEqualTo("Description1")
            Assertions.assertThat(factsList?.get(0)?.imageHref).isEqualTo("testUrl1")
            Assertions.assertThat(factsList?.get(1)?.title).isEqualTo("Title2")
            Assertions.assertThat(factsList?.get(1)?.description).isEqualTo("Description2")
            Assertions.assertThat(factsList?.get(1)?.imageHref).isEqualTo("testUrl2")
        }
    }

    @Test
    fun loadStatDetailList_ErrorWhenObserved() {
        Mockito.`when`(repository.getFactsData()).thenReturn(Observable.just(Resource.error(null)))
        val observer = mock<Observer<Resource<FactsResponse>>>()
        factsViewModel.factsData.observeForever(observer)
        factsViewModel.loadFactsData()

        testScheduler.triggerActions()
        Mockito.verify(repository).getFactsData()

        val argumentCaptor = argumentCaptor<Resource<FactsResponse>>()
        argumentCaptor.run {
            verify(observer, times(2)).onChanged(capture())
            val (loadingState, errorState) = allValues
            assertEquals(loadingState, Resource.loading(null))
            assertEquals(errorState, Resource.error(null))
        }
    }

    private fun getFactsResponseWithData(): Observable<Resource<FactsResponse>> {
        val factDetail1 = FactDetail("Title1", "Description1", "testUrl1")
        val factDetail2 = FactDetail("Title2", "Description2", "testUrl2")
        val factDetail3 = FactDetail("Title3", "Description3", "testUrl3")
        val factsResponse = FactsResponse("Page title", listOf(factDetail1, factDetail2, factDetail3))
        return Observable.just(Resource.success(factsResponse))
    }
}