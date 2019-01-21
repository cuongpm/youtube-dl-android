package com.youtubedl.ui.main.video

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.content.ContentResolver
import android.content.Context
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.youtubedl.ui.main.video.VideoViewModel.Companion.FILE_EXIST_ERROR_CODE
import com.youtubedl.ui.main.video.VideoViewModel.Companion.FILE_INVALID_ERROR_CODE
import com.youtubedl.util.FileUtil
import com.youtubedl.util.ext.deleteMedia
import com.youtubedl.util.ext.scanMedia
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.File

/**
 * Created by cuongpm on 1/13/19.
 */

class VideoViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: VideoViewModel

    private lateinit var fileUtil: FileUtil

    private lateinit var file: File

    private lateinit var newFile: File

    private lateinit var listFiles: List<File>

    private lateinit var context: Context

    private lateinit var contentResolver: ContentResolver

    @Before
    fun setup() {
        file = mock()
        newFile = mock()
        fileUtil = mock()
        context = mock()
        contentResolver = mock()
        viewModel = VideoViewModel(fileUtil)
        listFiles = listOf(File("path1"), File("path2"))
        doReturn(listFiles).`when`(fileUtil).listFiles
    }

    @Test
    fun `show list downloaded videos`() {
        viewModel.start()
        assertEquals(2, viewModel.localVideos.size)
        assertEquals("path1", viewModel.localVideos[0].file.path)
        assertEquals("path2", viewModel.localVideos[1].file.path)
    }

    @Test
    fun `delete a non-existent file should not change list videos`() {
        doReturn(false).`when`(file).exists()
        viewModel.start()
        viewModel.deleteVideo(file)

        assertEquals(2, viewModel.localVideos.size)
    }

    @Test
    fun `delete an existent file should update list videos`() {
        doReturn(true).`when`(file).exists()
        doReturn(("path1")).`when`(file).path
        viewModel.start()
        viewModel.deleteVideo(file)

        assertEquals(1, viewModel.localVideos.size)
        assertEquals("path2", viewModel.localVideos[0].file.path)
        verify(file).delete()
    }

    @Test
    fun `rename video with an invalid name should throw invalid error code`() {
        viewModel.renameVideo(context, file, "", newFile)

        assertEquals(FILE_INVALID_ERROR_CODE, viewModel.renameErrorEvent.value)
    }

    @Test
    fun `rename video with a existent name should throw exist error code`() {
        doReturn(true).`when`(newFile).exists()
        viewModel.renameVideo(context, file, "newName", newFile)

        verify(newFile).exists()
        assertEquals(FILE_EXIST_ERROR_CODE, viewModel.renameErrorEvent.value)
    }

    @Test
    fun `rename video with a valid name should update list videos`() {
        doReturn(false).`when`(newFile).exists()
        doReturn(true).`when`(file).renameTo(newFile)
        doReturn(("path1")).`when`(file).path
        doReturn(contentResolver).`when`(context).contentResolver

        viewModel.start()
        viewModel.renameVideo(context, file, "newName", newFile)

        verify(file).deleteMedia(context)
        verify(newFile).scanMedia(context)
        assertEquals(newFile, viewModel.localVideos[0].file)
    }
}