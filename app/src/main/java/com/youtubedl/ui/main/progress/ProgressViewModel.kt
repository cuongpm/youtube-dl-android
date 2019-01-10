package com.youtubedl.ui.main.progress

import android.app.DownloadManager
import android.app.DownloadManager.*
import android.app.DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED
import android.content.Context
import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import android.net.Uri
import com.youtubedl.DLApplication
import com.youtubedl.data.local.model.ProgressInfo
import com.youtubedl.data.local.room.entity.VideoInfo
import com.youtubedl.ui.main.base.BaseViewModel
import com.youtubedl.util.FileUtil.FOLDER_NAME
import com.youtubedl.util.FileUtil.folderDir
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
    private val application: DLApplication
) : BaseViewModel() {

    private lateinit var compositeDisposable: CompositeDisposable

    val progressInfos: ObservableList<ProgressInfo> = ObservableArrayList()

    override fun start() {
        compositeDisposable = CompositeDisposable()
    }

    override fun stop() {
        compositeDisposable.clear()
    }

    fun downloadVideo(videoInfo: VideoInfo?) {
        videoInfo?.let {
            if (!folderDir.exists() && !folderDir.mkdirs()) return

            val downloadManager = application.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            val request = DownloadManager.Request(Uri.parse(videoInfo.downloadUrl)).apply {
                setDestinationInExternalPublicDir(FOLDER_NAME, videoInfo.name)
                setNotificationVisibility(VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                allowScanningByMediaScanner()
            }

            val downloadId = downloadManager.enqueue(request)
            val progressInfo = ProgressInfo(downloadId = downloadId, videoInfo = videoInfo)
//        getProgressInfos().add(progressInfo)
//        mPreferencesManager.setProgress(getProgressInfos())
//        sendToView { view -> view.updateProgress() }

            downloadProgress(progressInfo, downloadManager)
        }
    }

    private fun downloadProgress(progressInfo: ProgressInfo, downloadManager: DownloadManager) {
        progressObservable(progressInfo, downloadManager)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {
                progressInfos.remove(progressInfo)
            }
            .doOnComplete {
                progressInfos.remove(progressInfo)
            }
            .subscribe { progressInfo ->
                progressInfos.find { it.downloadId == progressInfo.downloadId }?.let {
                    it.bytesDownloaded = progressInfo.bytesDownloaded
                    it.bytesTotal = progressInfo.bytesTotal
                }
            }.let { compositeDisposable.add(it) }
    }

    private fun progressObservable(
        progressInfo: ProgressInfo, downloadManager: DownloadManager
    ): Observable<ProgressInfo> {
        return Observable.create { emitter ->
            try {
                Observable.interval(1, TimeUnit.SECONDS)
                    .subscribe {
                        val query = DownloadManager.Query().apply { setFilterById(progressInfo.downloadId) }
                        val cursor = downloadManager.query(query).apply { moveToFirst() }
                        val status = cursor.getInt(cursor.getColumnIndex(COLUMN_STATUS))

                        when (status) {
                            STATUS_SUCCESSFUL -> emitter.onComplete()
                            STATUS_FAILED -> emitter.onError(Exception("Download failed"))
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
                emitter.onError(e)
            }
        }
    }
}