package com.youtubedl.di.module.activity

import com.youtubedl.di.FragmentScoped
import com.youtubedl.ui.main.player.VideoPlayerFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by cuongpm on 1/6/19.
 */

@Module
abstract class VideoPlayerModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun bindVideoPlayerFragment(): VideoPlayerFragment
}