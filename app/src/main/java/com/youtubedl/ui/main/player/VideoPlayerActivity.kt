package com.youtubedl.ui.main.player

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import com.youtubedl.R
import com.youtubedl.ui.main.base.BaseActivity
import com.youtubedl.util.ext.addFragment

/**
 * Created by cuongpm on 1/6/19.
 */

class VideoPlayerActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_player)

        addFragment(R.id.content_frame, intent.extras, ::VideoPlayerFragment)
    }

}