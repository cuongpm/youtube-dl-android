package com.youtubedl.di.module

import android.content.Context
import com.youtubedl.DLApplication
import com.youtubedl.di.qualifier.ApplicationContext
import dagger.Binds
import dagger.Module

/**
 * Created by cuongpm on 12/6/18.
 */

@Module
abstract class AppModule {

    @Binds
    @ApplicationContext
    abstract fun bindApplicationContext(application: DLApplication): Context
}