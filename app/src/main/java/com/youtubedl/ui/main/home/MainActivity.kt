package com.youtubedl.ui.main.home

import android.Manifest
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.pm.PackageManager
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import com.roughike.bottombar.OnTabSelectListener
import com.youtubedl.OpenForTesting
import com.youtubedl.R
import com.youtubedl.databinding.ActivityMainBinding
import com.youtubedl.ui.component.adapter.MainAdapter
import com.youtubedl.ui.main.base.BaseActivity
import com.youtubedl.util.fragment.FragmentFactory
import dagger.android.AndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

@OpenForTesting
class MainActivity : BaseActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var fragmentFactory: FragmentFactory

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var mainViewModel: MainViewModel

    private lateinit var dataBinding: ActivityMainBinding

    private lateinit var mainAdapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        mainViewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
        mainAdapter = MainAdapter(supportFragmentManager, fragmentFactory)

        dataBinding.viewPager.adapter = mainAdapter
        dataBinding.viewPagerListener = onPageChangeListener
        dataBinding.bottomBarListener = onTabSelectListener
        dataBinding.viewModel = mainViewModel

        grantPermissions()
    }

    override fun onBackPressed() {
        if (mainViewModel.currentItem.get() != 0) {
            mainViewModel.currentItem.set(0)
        } else {
            mainViewModel.pressBackBtnEvent.call()
        }
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return AndroidInjector { }
    }

    private fun grantPermissions() {
        // Grant permissions
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 0)
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
            R.id.tab_video -> mainViewModel.currentItem.set(2)
            else -> mainViewModel.currentItem.set(3)
        }
    }
}
