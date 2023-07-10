package dev.kichan.multiwallpaper.ui

import android.app.Activity
import android.graphics.Bitmap
import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("imageBitmap")
fun imageBitmap(imageView : ImageView, image : Bitmap){
    imageView.setImageBitmap(image)
}

@BindingAdapter("iamgePreView")
fun iamgePreView(imageView: ImageView, isPreView : Boolean){
    if(!isPreView) return

    val size = UiUtil.getPreViewImageSize(imageView.context as Activity, 0)
    imageView.run {
        clipToOutline = true
        layoutParams.width = size.x
        layoutParams.height = size.y
    }
}