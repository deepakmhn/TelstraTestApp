package com.telstra.testapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.telstra.testapp.AppSchedulers
import com.telstra.testapp.repository.FactsRepository

/**
 * Factory for providing the FactsViewModel with parameters
 */
class FactsViewModelFactory(
    private val repository: FactsRepository,
    private var schedulers: AppSchedulers
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FactsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FactsViewModel(repository, schedulers) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}