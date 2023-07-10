package dev.kichan.multiwallpaper.ui

import android.content.Context
import android.content.pm.Attribution
import android.content.res.TypedArray
import android.graphics.Bitmap
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import dev.kichan.multiwallpaper.R
import dev.kichan.multiwallpaper.databinding.ViewOneShotBinding

class OneShotView : LinearLayout {
    lateinit var view : ViewOneShotBinding

    constructor(context: Context) : super(context) {
        initView(context)
    }

    constructor(context: Context, attr : AttributeSet) : super(context, attr){
        initView(context)
        getAttrs(attr)
    }

    constructor(context: Context, attr : AttributeSet, defStyle : Int) : super(context, attr, defStyle){
        initView(context)
        getAttrs(attr, defStyle)
    }

    private fun initView(context: Context) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        view = ViewOneShotBinding.inflate(inflater)

        val lp = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        view.root.layoutParams = lp

        addView(view.root)
    }

    private fun getAttrs(attrs: AttributeSet): Unit {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.OneShotView)
        setTypeArray(typedArray)
    }

    private fun getAttrs(attrs: AttributeSet, defStyle : Int): Unit {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.OneShotView, defStyle, 0)
        setTypeArray(typedArray)
    }

    private fun setTypeArray(typedArray: TypedArray) {
        val oneShotNumber = typedArray.getInt(R.styleable.OneShotView_ontShowNumber, -1)

        view.run {
            imgOneShotPreView.clipToOutline = true
            txtOneShotTitle.text = "원샷 $oneShotNumber"
        }
    }

    fun setWallpaper(wallpaper: Bitmap) {
        Log.d("setWallpaper", "실행")
        view.imgOneShotPreView.setImageBitmap(wallpaper)
    }
}