package com.youtubedl.di.module

import android.app.Application
import android.content.Context
import com.youtubedl.DLApplication
import com.youtubedl.di.qualifier.ApplicationContext
import com.youtubedl.util.scheduler.BaseSchedulers
import com.youtubedl.util.scheduler.BaseSchedulersImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

/**
 * Created by cuongpm on 12/6/18.
 */

@Module
abstract class AppModule {

    @Binds
    @ApplicationContext
    abstract fun bindApplicationContext(application: DLApplication): Context

    @Binds
    abstract fun bindApplication(application: DLApplication): Application

    @Singleton
    @Binds
    abstract fun bindBaseSchedulers(baseSchedulers: BaseSchedulersImpl): BaseSchedulers
}