package com.example.customview.ui

import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import androidx.annotation.ColorInt
import androidx.annotation.Px
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.toRectF
import com.example.customview.R
import com.example.customview.extentions.dpToOx

class AvatarImageViewMask @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    companion object {
        private const val DEFAULT_BORDER_WIDTH = 2
        private const val DEFAULT_BORDER_COLOR = Color.WHITE
        private const val DEFAULT_SIZE = 40
    }

    @Px
    private var borderWidth: Float = context.dpToOx(DEFAULT_BORDER_WIDTH)
    @ColorInt
    private var borderColor: Int = Color.WHITE
    private var initials: String = "??"
    private val maskPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val viewRect = Rect()
    private lateinit var resultBm: Bitmap
    private lateinit var maskBm: Bitmap
    private lateinit var srcBm: Bitmap

    init {
        if (attrs != null) {
            val ta: TypedArray =
                context.obtainStyledAttributes(attrs, R.styleable.AvatarImageViewMask)
            borderWidth =
                ta.getDimension(
                    R.styleable.AvatarImageViewMask_aiv_borderWidth, context.dpToOx(
                        DEFAULT_BORDER_WIDTH
                    )
                )

            borderColor =
                ta.getColor(R.styleable.AvatarImageViewMask_aiv_borderColor, DEFAULT_BORDER_COLOR)

            initials = ta.getString(R.styleable.AvatarImageViewMask_aiv_initials) ?: "??"
        }

        scaleType = ScaleType.CENTER_CROP
        setup()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        Log.e("LOG_TAG", "onMeasure")

        val initSize = resolveDefaultSize(widthMeasureSpec)
        setMeasuredDimension(initSize, initSize)
    }

    private fun resolveDefaultSize(spec: Int): Int {
        return when (MeasureSpec.getMode(spec)) {
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

        if (w == 0) return

        with(viewRect) {
            left = 0
            top = 0
            right = w
            bottom = h
        }

        prepareBitmaps(w, h)
    }

    override fun onDraw(canvas: Canvas) {
//        super.onDraw(canvas)
        Log.e("LOG_TAG", "onDraw")
        // NOT allocate, only draw
        canvas.drawBitmap(resultBm, viewRect, viewRect, null)
//        canvas.drawBitmap(maskBm, viewRect, viewRect, null)
    }

    private fun setup() {
        with(maskPaint) {
            color = Color.RED
            style = Paint.Style.FILL
        }
    }

    private fun prepareBitmaps(w: Int, h: Int) {
        //prepare buffer this

//        maskBm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        maskBm = Bitmap.createBitmap(w, h, Bitmap.Config.ALPHA_8)
        resultBm = maskBm.copy(Bitmap.Config.ARGB_8888, true)

        val maskCanvas = Canvas(maskBm)
        maskCanvas.drawOval(viewRect.toRectF(), maskPaint)


        maskPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
//        maskPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OVER)
        srcBm = drawable.toBitmap(w, h, Bitmap.Config.ARGB_8888)
//
        val resultCanvas = Canvas(resultBm)
        resultCanvas.drawBitmap(maskBm, viewRect, viewRect, null)
        resultCanvas.drawBitmap(srcBm, viewRect, viewRect, maskPaint)
//        resultCanvas.drawBitmap(srcBm, viewRect, viewRect, null)
    }


}