package com.youtubedl.di.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.youtubedl.di.ViewModelKey
import com.youtubedl.ui.main.home.BrowserViewModel
import com.youtubedl.ui.main.home.MainViewModel
import com.youtubedl.ui.main.player.VideoPlayerViewModel
import com.youtubedl.ui.main.splash.SplashViewModel
import com.youtubedl.util.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

/**
 * Created by cuongpm on 12/6/18.
 */

@Module(includes = [AppModule::class])
abstract class ViewModelModule {

    @Singleton
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    abstract fun bindSplashViewModel(viewModel: SplashViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BrowserViewModel::class)
    abstract fun bindBrowserViewModel(viewModel: BrowserViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(VideoPlayerViewModel::class)
    abstract fun bindVideoPlayerViewModel(viewModel: VideoPlayerViewModel): ViewModel
}
