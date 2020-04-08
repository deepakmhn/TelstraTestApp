package com.telstra.testapp.di

import com.telstra.testapp.TelstraTestApp
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, AppModule::class, FactsModule::class])
interface AppComponent : AndroidInjector<TelstraTestApp> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<TelstraTestApp>()
}