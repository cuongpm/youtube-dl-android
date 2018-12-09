package com.youtubedl.ui.main.home

import android.databinding.DataBindingUtil
import android.os.Bundle
import com.youtubedl.R
import com.youtubedl.databinding.ActivityMainBinding
import com.youtubedl.ui.component.adapter.MainAdapter
import com.youtubedl.ui.main.base.BaseActivity

class MainActivity : BaseActivity() {

    private lateinit var dataBinding: ActivityMainBinding

    private lateinit var mainAdapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        mainAdapter = MainAdapter(supportFragmentManager)
        dataBinding.viewPager.adapter = mainAdapter
    }
}
