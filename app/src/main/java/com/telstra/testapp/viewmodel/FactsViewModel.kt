package com.telstra.testapp.viewmodel

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.telstra.testapp.AppSchedulers
import com.telstra.testapp.data.Resource
import com.telstra.testapp.repository.FactsRepository
import com.telstra.testapp.service.FactsResponse
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class FactsViewModel @Inject constructor(
    private var repository: FactsRepository, private var schedulers: AppSchedulers
) : ViewModel() {
    private val _factsData = MutableLiveData<Resource<FactsResponse>>()
    private val compositeDisposable = CompositeDisposable()
    private var factsResponse: FactsResponse? = null

    val isLoading = ObservableBoolean(false)

    val factsData: LiveData<Resource<FactsResponse>>
        get() = _factsData

    fun loadFactsData() {
        isLoading.set(true)
        _factsData.value = Resource.loading(factsResponse)
        compositeDisposable.add(
            repository.getFactsData()
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.mainThread())
                .subscribe(
                    { factsResponseResource ->
                        isLoading.set(false)
                        factsResponseResource?.data?.let { factsResponse = it }
                        _factsData.value = factsResponseResource
                    },
                    {
                        isLoading.set(false)
                        Resource.error(factsResponse)
                    })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}