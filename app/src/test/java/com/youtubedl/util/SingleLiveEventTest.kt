package com.youtubedl.util

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.Observer
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

/**
 * Created by cuongpm on 1/13/19.
 */

class SingleLiveEventTest {

    // Execute tasks synchronously
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    // The class that has the lifecycle
    @Mock
    private lateinit var owner: LifecycleOwner
    // The observer of the event under test
    @Mock
    private lateinit var eventObserver: Observer<Int>
    // Defines the Android Lifecycle of an object, used to trigger different events
    private lateinit var lifecycle: LifecycleRegistry
    // Event object under test
    private val singleLiveEvent = SingleLiveEvent<Int>()

    @Before
    fun setUpLifecycles() {
        MockitoAnnotations.initMocks(this)


        // Link custom lifecycle owner with the lifecyle register.
        lifecycle = LifecycleRegistry(owner)
        Mockito.`when`(owner.lifecycle).thenReturn(lifecycle)

        // Start observing
        singleLiveEvent.observe(owner, eventObserver)

        // Start in a non-active state
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
    }

    @Test
    fun valueNotSet_onFirstOnResume() {
        // On resume
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)

        // no update should be emitted because no value has been set
        Mockito.verify<Observer<Int>>(eventObserver, Mockito.never()).onChanged(ArgumentMatchers.anyInt())
    }

    @Test
    fun singleUpdate_onSecondOnResume_updatesOnce() {
        // After a value is set
        singleLiveEvent.value = 42

        with(lifecycle) {
            // observers are called once on resume
            handleLifecycleEvent(android.arch.lifecycle.Lifecycle.Event.ON_RESUME)

            // on second resume, no update should be emitted.
            handleLifecycleEvent(android.arch.lifecycle.Lifecycle.Event.ON_STOP)
            handleLifecycleEvent(android.arch.lifecycle.Lifecycle.Event.ON_RESUME)
        }

        // Check that the observer is called once
        Mockito.verify<Observer<Int>>(eventObserver, Mockito.times(1)).onChanged(ArgumentMatchers.anyInt())
    }

    @Test
    fun twoUpdates_updatesTwice() {
        // After a value is set
        singleLiveEvent.value = 42

        // observers are called once on resume
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)

        // when the value is set again, observers are called again.
        singleLiveEvent.value = 23

        // Check that the observer has been called twice
        Mockito.verify<Observer<Int>>(eventObserver, Mockito.times(2)).onChanged(ArgumentMatchers.anyInt())
    }

    @Test
    fun twoUpdates_noUpdateUntilActive() {
        // Set a value
        singleLiveEvent.value = 42

        // which doesn't emit a change
        Mockito.verify<Observer<Int>>(eventObserver, Mockito.never()).onChanged(42)

        // and set it again
        singleLiveEvent.value = 42

        // observers are called once on resume.
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)

        // Check that the observer is called only once
        Mockito.verify<Observer<Int>>(eventObserver, Mockito.times(1)).onChanged(ArgumentMatchers.anyInt())
    }

}