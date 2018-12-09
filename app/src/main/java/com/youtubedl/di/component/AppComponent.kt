package com.youtubedl.di.component

import com.youtubedl.DLApplication
import com.youtubedl.di.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Created by cuongpm on 12/6/18.
 */

@Singleton
@Component(
    modules = [AndroidSupportInjectionModule::class, AppModule::class, ActivityBindingModule::class,
        NetworkModule::class, RepositoryModule::class, ViewModelModule::class]
)
interface AppComponent : AndroidInjector<DLApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(appApplication: DLApplication): Builder

        fun build(): AppComponent
    }
}