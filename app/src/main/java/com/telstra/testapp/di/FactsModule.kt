package com.telstra.testapp.di

import com.telstra.testapp.AppSchedulers
import com.telstra.testapp.repository.FactsRepository
import com.telstra.testapp.ui.FactsListFragment
import com.telstra.testapp.viewmodel.FactsViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class FactsModule {

    @Module
    companion object {

        @JvmStatic
        @Provides
        internal fun providesFactsViewModelFactory(
            repository: FactsRepository,
            schedulers: AppSchedulers
        ): FactsViewModelFactory {
            return FactsViewModelFactory(repository, schedulers)
        }
    }

    @ContributesAndroidInjector()
    internal abstract fun factsListFragment(): FactsListFragment

}