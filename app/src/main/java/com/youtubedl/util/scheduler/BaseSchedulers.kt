package com.youtubedl.util.scheduler

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by cuongpm on 1/16/19.
 */

interface BaseSchedulers {
    val computation: Scheduler
    val io: Scheduler
    val newThread: Scheduler
    val single: Scheduler
    val mainThread: Scheduler
}

class BaseSchedulersImpl @Inject constructor() : BaseSchedulers {
    override val computation: Scheduler = Schedulers.computation()
    override val io: Scheduler = Schedulers.io()
    override val newThread: Scheduler = Schedulers.newThread()
    override val single: Scheduler = Schedulers.single()
    override val mainThread: Scheduler = AndroidSchedulers.mainThread()
}