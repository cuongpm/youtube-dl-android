package com.youtubedl.ui.main.home

import android.Manifest
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.pm.PackageManager
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import com.roughike.bottombar.OnTabSelectListener
import com.youtubedl.OpenForTesting
import com.youtubedl.R
import com.youtubedl.databinding.ActivityMainBinding
import com.youtubedl.ui.component.adapter.MainAdapter
import com.youtubedl.ui.main.base.BaseActivity

import com.youtubedl.ui.main.progress.ProgressViewModel
import javax.inject.Inject

@OpenForTesting
class MainActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var browserViewModel: BrowserViewModel

    lateinit var progressViewModel: ProgressViewModel

    private lateinit var mainViewModel: MainViewModel

    private lateinit var dataBinding: ActivityMainBinding

    private lateinit var mainAdapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        browserViewModel = ViewModelProviders.of(this, viewModelFactory).get(BrowserViewModel::class.java)
        progressViewModel = ViewModelProviders.of(this, viewModelFactory).get(ProgressViewModel::class.java)
        mainViewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
        mainAdapter = MainAdapter(supportFragmentManager)

        dataBinding.adapter = mainAdapter
        dataBinding.viewPagerListener = onPageChangeListener
        dataBinding.bottomBarListener = onTabSelectListener
        dataBinding.viewModel = mainViewModel

        handleUIEvents()
    }

    override fun onBackPressed() {
        if (mainViewModel.currentItem.get() != 0) {
            mainViewModel.currentItem.set(0)
        } else {
            browserViewModel.pressBackBtnEvent.call()
        }
    }

    private fun handleUIEvents() {
        // Grant permission
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 0)
        }

        browserViewModel.apply {
            downloadVideoEvent.observe(this@MainActivity, Observer { videoInfo ->
                progressViewModel.downloadVideo(videoInfo)
            })
        }
    }

    private val onPageChangeListener = object : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(p0: Int) {
        }

        override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
        }

        override fun onPageSelected(postion: Int) {
            mainViewModel.currentItem.set(postion)
        }
    }

    private val onTabSelectListener = OnTabSelectListener { tabId ->
        when (tabId) {
            R.id.tab_browser -> mainViewModel.currentItem.set(0)
            R.id.tab_progress -> mainViewModel.currentItem.set(1)
            R.id.tab_video -> {
                mainViewModel.currentItem.set(2)
                //                getPresenter().setTabVideoBadge(0)
                //                mBinding.bottomBar.getTabWithId(tabId).removeBadge()
            }
            else -> mainViewModel.currentItem.set(3)
        }
    }
}
