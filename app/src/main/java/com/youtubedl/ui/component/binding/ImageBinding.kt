package com.youtubedl.ui.component.binding

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide

/**
 * Created by cuongpm on 12/18/18.
 */

object ImageBinding {

    @BindingAdapter("app:imageUrl")
    @JvmStatic
    fun ImageView.loadImage(url: String) {
        Glide.with(context).load(url).into(this)
    }

    @BindingAdapter("android:src")
    @JvmStatic
    fun ImageView.setImageResource(resource: Int) {
        setImageResource(resource)
    }
}