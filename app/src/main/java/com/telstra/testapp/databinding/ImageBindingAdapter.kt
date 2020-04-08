package com.telstra.testapp.databinding

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

/**
 * Data Binding adapters specific to the app
 */
object ImageBindingAdapter {

    @JvmStatic
    @BindingAdapter("app:imageUrl")
    fun setImageUrl(view: ImageView, url: String?) {
        url?.let {
            view.visibility = View.VISIBLE
            Glide.with(view.context).load(url).into(view)
        }
    }
}