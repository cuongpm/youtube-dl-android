package com.youtubedl.ui.main.progress

import android.app.DownloadManager
import android.app.DownloadManager.*
import android.app.DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED
import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import android.net.Uri
import android.support.annotation.VisibleForTesting
import com.youtubedl.OpenForTesting
import com.youtubedl.data.local.room.entity.ProgressInfo
import com.youtubedl.data.local.room.entity.VideoInfo
import com.youtubedl.data.repository.ProgressRepository
import com.youtubedl.ui.main.base.BaseViewModel
import com.youtubedl.util.FileUtil
import com.youtubedl.util.FileUtil.Companion.FOLDER_NAME
import com.youtubedl.util.scheduler.BaseSchedulers
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by cuongpm on 12/7/18.
 */

@OpenForTesting
class ProgressViewModel @Inject constructor(
    private val downloadManager: DownloadManager,
    private val fileUtil: FileUtil,
    private val baseSchedulers: BaseSchedulers,
    private val progressRepository: ProgressRepository
) : BaseViewModel() {

    @VisibleForTesting
    internal lateinit var compositeDisposable: CompositeDisposable

    val progressInfos: ObservableList<ProgressInfo> = ObservableArrayList()

    override fun start() {
        compositeDisposable = CompositeDisposable()
        showListDownloadingVideos()
    }

    override fun stop() {
        compositeDisposable.clear()
    }

    private fun showListDownloadingVideos() {
        progressRepository.getProgressInfos()
            .subscribeOn(baseSchedulers.io)
            .observeOn(baseSchedulers.mainThread)
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
            val progressInfo = ProgressInfo(downloadId = downloadId, videoInfo = videoInfo)

            saveProgressInfo(progressInfo)
            downloadProgress(progressInfo)
        }
    }

    private fun saveProgressInfo(progressInfo: ProgressInfo) {
        Completable.create { emitter ->
            try {
                progressRepository.saveProgressInfo(progressInfo)
                emitter.onComplete()
            } catch (e: Exception) {
                emitter.onError(e)
            }
        }.subscribeOn(baseSchedulers.io)
            .observeOn(baseSchedulers.mainThread)
            .subscribe({
            }, { error ->
                error.printStackTrace()
            })
    }

    private fun deleteProgressInfo(progressInfo: ProgressInfo) {
        Completable.create { emitter ->
            try {
                progressRepository.deleteProgressInfo(progressInfo)
                emitter.onComplete()
            } catch (e: Exception) {
                emitter.onError(e)
            }
        }.subscribeOn(baseSchedulers.io)
            .observeOn(baseSchedulers.mainThread)
            .subscribe({
            }, { error ->
                error.printStackTrace()
            })
    }

    @VisibleForTesting
    internal fun downloadProgress(progressInfo: ProgressInfo) {
        progressObservable(progressInfo)
            .subscribeOn(baseSchedulers.io)
            .observeOn(baseSchedulers.mainThread)
            .doOnComplete {
                progressInfos.find { it.downloadId == progressInfo.downloadId }?.let {
                    progressInfos.remove(progressInfo)
                    deleteProgressInfo(progressInfo)
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
            Observable.interval(1, TimeUnit.SECONDS)
                .subscribe({
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
                }, {
                    emitter.onComplete()
                })
        }
    }
}