package com.youtubedl.ui.main.player

import android.os.Bundle
import com.youtubedl.R
import com.youtubedl.ui.main.base.BaseActivity
import com.youtubedl.util.ext.addFragment

/**
 * Created by cuongpm on 1/6/19.
 */

class VideoPlayerActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        addFragment(R.id.content_frame, intent.extras, ::VideoPlayerFragment)
    }

}