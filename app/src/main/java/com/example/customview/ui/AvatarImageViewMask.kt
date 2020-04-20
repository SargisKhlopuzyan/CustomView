package com.example.customview.ui

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatImageView
import com.example.customview.extentions.dpToOx

class AvatarImageViewMask @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    companion object {
        private const val DEFAULT_SIZE = 40
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        Log.e("LOG_TAG", "onAttachedToWindow")
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        Log.e(
            "LOG_TAG", """
            onMeasure
            width: ${MeasureSpec.toString(widthMeasureSpec)}
            height: ${MeasureSpec.toString(heightMeasureSpec)}
        """.trimMargin()
        )

        val initSize = resolveDefaultSize(widthMeasureSpec)
        setMeasuredDimension(initSize, initSize)

        Log.e("LOG_TAG", "onMeasure after set size: $measuredWidth $measuredHeight")
    }

    private fun resolveDefaultSize(spec: Int): Int {
        return when (MeasureSpec.getMode(DEFAULT_SIZE)) {
            MeasureSpec.UNSPECIFIED -> {
                Log.e("LOG_TAG", "resolveDefaultSize -> MeasureSpec.UNSPECIFIED")
                context.dpToOx(DEFAULT_SIZE).toInt()
            }
            MeasureSpec.AT_MOST -> MeasureSpec.getSize(spec)
            MeasureSpec.EXACTLY -> MeasureSpec.getSize(spec)
            else -> MeasureSpec.getSize(spec)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        Log.e("LOG_TAG", "onSizeChanged")
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        Log.e("LOG_TAG", "onLayout")
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        Log.e("LOG_TAG", "onDraw")
    }


}