

package com.ibrahim.kotlindemo.util

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ibrahim.kotlindemo.R
const val PERMISSION_SEND_SMS=234
fun getProgressDrawable(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        centerRadius = 1f
        centerRadius = 50f
        start()
    }

}
fun ImageView.loadImage(url: String?, progressDrawable: CircularProgressDrawable) {
    val options = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.mipmap.ic_launcher)

    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(url)
        .into(this)
}
@BindingAdapter("android:imageUrl")
fun  loadImage(view: ImageView ,url: String?){
    view.loadImage(url , getProgressDrawable(view.context))
}