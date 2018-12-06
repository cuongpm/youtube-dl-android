package com.youtubedl.di.module

import com.youtubedl.di.ActivityScoped
import com.youtubedl.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by cuongpm on 12/6/18.
 */

@Module
internal abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector
    internal abstract fun bindAddMainActivity(): MainActivity
}