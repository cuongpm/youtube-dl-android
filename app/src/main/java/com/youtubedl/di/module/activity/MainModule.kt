package com.youtubedl.di.module.activity

import android.app.Activity
import com.youtubedl.di.ActivityScoped
import com.youtubedl.di.FragmentScoped
import com.youtubedl.ui.main.home.BrowserFragment
import com.youtubedl.ui.main.home.MainActivity
import com.youtubedl.ui.main.progress.ProgressFragment
import com.youtubedl.ui.main.settings.SettingsFragment
import com.youtubedl.ui.main.video.VideoFragment
import com.youtubedl.util.fragment.FragmentFactory
import com.youtubedl.util.fragment.FragmentFactoryImpl
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by cuongpm on 12/9/18.
 */

@Module
abstract class MainModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun bindBrowserFragment(): BrowserFragment

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun bindProgressFragment(): ProgressFragment

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun bindVideoFragment(): VideoFragment

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun bindSettingsFragment(): SettingsFragment

    @ActivityScoped
    @Binds
    abstract fun bindMainActivity(mainActivity: MainActivity): Activity

    @ActivityScoped
    @Binds
    abstract fun bindFragmentFactory(fragmentFactory: FragmentFactoryImpl): FragmentFactory
}