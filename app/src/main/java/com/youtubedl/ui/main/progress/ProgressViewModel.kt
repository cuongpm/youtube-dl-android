package com.youtubedl.ui.main.progress

import android.app.DownloadManager
import android.app.DownloadManager.*
import android.app.DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED
import android.content.Context
import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import android.net.Uri
import com.youtubedl.DLApplication
import com.youtubedl.data.local.room.entity.ProgressInfo
import com.youtubedl.data.local.room.entity.VideoInfo
import com.youtubedl.data.repository.ProgressRepository
import com.youtubedl.ui.main.base.BaseViewModel
import com.youtubedl.util.FileUtil
import com.youtubedl.util.FileUtil.Companion.FOLDER_NAME
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by cuongpm on 12/7/18.
 */

class ProgressViewModel @Inject constructor(
    private val application: DLApplication,
    private val fileUtil: FileUtil,
    private val progressRepository: ProgressRepository
) : BaseViewModel() {

    private lateinit var downloadManager: DownloadManager

    private lateinit var compositeDisposable: CompositeDisposable

    val progressInfos: ObservableList<ProgressInfo> = ObservableArrayList()

    override fun start() {
        compositeDisposable = CompositeDisposable()
        downloadManager = application.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        showListDownloadingVideos()
    }

    override fun stop() {
        compositeDisposable.clear()
    }

    private fun showListDownloadingVideos() {
        progressRepository.getProgressInfos()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ progressInfos ->
                progressInfos.map { downloadProgress(it) }
            }, { error ->
                error.printStackTrace()
            }).let { compositeDisposable.add(it) }

    }

    fun downloadVideo(videoInfo: VideoInfo?) {
        videoInfo?.let {
            if (!fileUtil.folderDir.exists() && !fileUtil.folderDir.mkdirs()) return

            val request = DownloadManager.Request(Uri.parse(videoInfo.downloadUrl)).apply {
                setDestinationInExternalPublicDir(FOLDER_NAME, videoInfo.name)
                setNotificationVisibility(VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                allowScanningByMediaScanner()
            }

            val downloadId = downloadManager.enqueue(request)
            val progressInfo =
                ProgressInfo(downloadId = downloadId, videoInfo = videoInfo)

            progressRepository.saveProgressInfo(progressInfo)
            downloadProgress(progressInfo)
        }
    }

    private fun downloadProgress(progressInfo: ProgressInfo) {
        progressObservable(progressInfo)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete {
                progressInfos.find { it.downloadId == progressInfo.downloadId }?.let {
                    progressInfos.remove(progressInfo)
                    progressRepository.deleteProgressInfo(progressInfo)
                }
            }
            .subscribe({ progressInfo ->
                progressInfos.find { it.downloadId == progressInfo.downloadId }?.let {
                    progressInfos[progressInfos.indexOf(it)] = progressInfo
                } ?: run {
                    progressInfos.add(progressInfo)
                }
            }, { error ->
                error.printStackTrace()
            }).let { compositeDisposable.add(it) }
    }

    private fun progressObservable(progressInfo: ProgressInfo): Observable<ProgressInfo> {
        return Observable.create { emitter ->
            try {
                Observable.interval(1, TimeUnit.SECONDS)
                    .subscribe {
                        val query = DownloadManager.Query().apply { setFilterById(progressInfo.downloadId) }
                        val cursor = downloadManager.query(query).apply { moveToFirst() }
                        val status = cursor.getInt(cursor.getColumnIndex(COLUMN_STATUS))

                        when (status) {
                            STATUS_SUCCESSFUL -> emitter.onComplete()
                            STATUS_FAILED -> emitter.onComplete()
                            STATUS_RUNNING -> {
                                val currentBytes = cursor.getInt(cursor.getColumnIndex(COLUMN_BYTES_DOWNLOADED_SO_FAR))
                                val totalBytes = cursor.getInt(cursor.getColumnIndex(COLUMN_TOTAL_SIZE_BYTES))
                                progressInfo.bytesDownloaded = currentBytes
                                progressInfo.bytesTotal = totalBytes
                                emitter.onNext(progressInfo)
                            }
                        }
                        cursor.close()
                    }
            } catch (e: Exception) {
                emitter.onComplete()
            }
        }
    }
}