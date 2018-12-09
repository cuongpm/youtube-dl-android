package com.youtubedl.ui.main.splash

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.os.Handler
import com.youtubedl.R
import com.youtubedl.databinding.ActivitySplashBinding
import com.youtubedl.ui.main.base.BaseActivity
import com.youtubedl.ui.main.home.MainActivity
import javax.inject.Inject

/**
 * Created by cuongpm on 12/6/18.
 */

class SplashActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var splashViewModel: SplashViewModel

    private lateinit var dataBinding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash)

        splashViewModel = ViewModelProviders.of(this, viewModelFactory).get(SplashViewModel::class.java).apply {
            //            addTaskEvent.observe(this@TaskActivity, Observer {
//                startActivity(Intent(this@TaskActivity, AddTaskActivity::class.java))
//            })
        }

        Handler().postDelayed({
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
        }, 3000)
    }
}