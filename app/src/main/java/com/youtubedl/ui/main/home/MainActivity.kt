package com.youtubedl.ui.main.home

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.view.ViewPager
import com.roughike.bottombar.OnTabSelectListener
import com.youtubedl.R
import com.youtubedl.databinding.ActivityMainBinding
import com.youtubedl.ui.component.adapter.MainAdapter
import com.youtubedl.ui.main.base.BaseActivity
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var browserViewModel: BrowserViewModel

    private lateinit var dataBinding: ActivityMainBinding

    private lateinit var mainAdapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        mainAdapter = MainAdapter(supportFragmentManager)
        dataBinding.adapter = mainAdapter
        dataBinding.viewPagerListener = onPageChangeListener
        dataBinding.bottomBarListener = onTabSelectListener
        dataBinding.viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)

        browserViewModel = ViewModelProviders.of(this, viewModelFactory).get(BrowserViewModel::class.java)
    }

    private val onPageChangeListener = object : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(p0: Int) {
        }

        override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
        }

        override fun onPageSelected(postion: Int) {
            dataBinding.viewModel?.currentItem?.set(postion)
        }
    }

    private val onTabSelectListener = OnTabSelectListener { tabId ->
        when (tabId) {
            R.id.tab_browser -> dataBinding.viewModel?.currentItem?.set(0)
            R.id.tab_progress -> dataBinding.viewModel?.currentItem?.set(1)
            R.id.tab_video -> {
                dataBinding.viewModel?.currentItem?.set(2)
                //                getPresenter().setTabVideoBadge(0)
                //                mBinding.bottomBar.getTabWithId(tabId).removeBadge()
            }
            R.id.tab_online -> {
                dataBinding.viewModel?.currentItem?.set(3)
                //                getPresenter().setTabOnlineBadge(0)
                //                mBinding.bottomBar.getTabWithId(tabId).removeBadge()
            }
            else -> dataBinding.viewModel?.currentItem?.set(4)
        }
    }
}
