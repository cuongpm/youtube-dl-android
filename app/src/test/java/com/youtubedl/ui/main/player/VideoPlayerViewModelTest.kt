package com.youtubedl.ui.main.player

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Created by cuongpm on 1/29/19.
 */
class VideoPlayerViewModelTest {

    private lateinit var videoPlayerViewModel: VideoPlayerViewModel

    @Before
    fun setup() {
        videoPlayerViewModel = VideoPlayerViewModel()
    }

    @Test
    fun `test video volume on`() {
        assertEquals(1.0f, videoPlayerViewModel.getVolume())
    }

    @Test
    fun `test video volume off`() {
        videoPlayerViewModel.isVolumeOn = false
        assertEquals(0.0f, videoPlayerViewModel.getVolume())
    }
}