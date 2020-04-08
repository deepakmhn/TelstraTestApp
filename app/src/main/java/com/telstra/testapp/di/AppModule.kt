package com.telstra.testapp.di

import android.content.Context
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.telstra.testapp.AppSchedulers
import com.telstra.testapp.TelstraTestApp
import com.telstra.testapp.repository.FactsRemoteDataSource
import com.telstra.testapp.repository.FactsRepository
import com.telstra.testapp.service.FactsService
import com.telstra.testapp.util.FACTS_SERVICE_ENDPOINT
import com.telstra.testapp.util.NetManager
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [FactsModule::class])
class AppModule {

    @Provides
    fun providesContext(application: TelstraTestApp): Context = application.applicationContext

    @Singleton
    @Provides
    fun provideAppScheduler() = AppSchedulers()

    @Singleton
    @Provides
    fun provideFactsRepository(netManager: NetManager, factsDataSource: FactsRemoteDataSource) =
        FactsRepository(netManager, factsDataSource)


    @Singleton
    @Provides
    fun provideFactsService(): FactsService =
        Retrofit.Builder()
            .baseUrl(FACTS_SERVICE_ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(FactsService::class.java)

}