package com.youtubedl.ui.main.video

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.youtubedl.util.FileUtil
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.File

/**
 * Created by cuongpm on 1/13/19.
 */

class VideoViewModelTest {

    private lateinit var viewModel: VideoViewModel

    private lateinit var fileUtil: FileUtil

    @Before
    fun setup() {
        fileUtil = mock()
        viewModel = VideoViewModel(fileUtil)
    }

    @Test
    fun `test show list downloaded videos`() {
        val files = listOf(File("path1"), File("path2"))
        doReturn(files).`when`(fileUtil).listFiles
        viewModel.start()
        assertEquals(2, viewModel.localVideos.size)
        assertEquals("path1", viewModel.localVideos[0].file.path)
        assertEquals("path2", viewModel.localVideos[1].file.path)
    }
}