package com.machine.testkotlin.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.machine.testkotlin.R

fun ImageView.loadProfile(url: String?) {
    Glide.with(this)
        .load(url)
        .placeholder(R.drawable.placeholder_user)
        .apply(RequestOptions.circleCropTransform())
        .circleCrop()
        .into(this)
}