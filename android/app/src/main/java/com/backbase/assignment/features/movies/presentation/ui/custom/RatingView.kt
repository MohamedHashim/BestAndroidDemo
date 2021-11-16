package com.backbase.assignment.features.movies.presentation.ui.custom

import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.backbase.assignment.R

class RatingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    companion object {
        const val ARC_FULL_ROTATION_DEGREE = 360
        const val PERCENTAGE_DIVIDER = 100.0
        const val PERCENTAGE_VALUE_HOLDER = "percentage"
    }

    var currentPercentage = 20f

    private val ovalSpace = RectF()
    private val ovalBackgroundSpace = RectF()

    val parentArcPaint = Paint().apply {
        style = Paint.Style.STROKE
        isAntiAlias = true
        strokeWidth = 8f
    }

    val fillArcPaint = Paint().apply {
        style = Paint.Style.STROKE
        isAntiAlias = true
        strokeWidth = 8f
        strokeCap = Paint.Cap.ROUND
    }

    private val backArcPaint = Paint().apply {
        style = Paint.Style.FILL
        strokeCap = Paint.Cap.SQUARE
        color = ContextCompat.getColor(context, R.color.blue_bg_rating)
    }

    override fun onDraw(canvas: Canvas?) {
        setSpace()
        setBackgroundSpace()
        canvas?.let {
            drawBackgroundSpaceArc(it)
            drawBackgroundArc(it)
            drawInnerArc(it)
        }
    }

    private fun setSpace() {
        val horizontalCenter = (width.div(2)).toFloat()
        val verticalCenter = (height.div(2)).toFloat()
        val ovalSize = 45
        ovalSpace.set(
            horizontalCenter - ovalSize,
            verticalCenter - ovalSize,
            horizontalCenter + ovalSize,
            verticalCenter + ovalSize
        )
    }

    private fun setBackgroundSpace() {
        val horizontalCenter = (width.div(2)).toFloat()
        val verticalCenter = (height.div(2)).toFloat()
        val ovalSize = 55
        ovalBackgroundSpace.set(
            horizontalCenter - ovalSize,
            verticalCenter - ovalSize,
            horizontalCenter + ovalSize,
            verticalCenter + ovalSize
        )
    }

    private fun drawBackgroundArc(it: Canvas) {
        it.drawArc(ovalSpace, 0f, 360f, false, parentArcPaint)
    }

    private fun drawBackgroundSpaceArc(it: Canvas) {
        it.drawArc(ovalBackgroundSpace, 0f, 360f, true, backArcPaint)
    }

    private fun drawInnerArc(canvas: Canvas) {
        val percentageToFill = getCurrentPercentageToFill()
        canvas.drawArc(ovalSpace, 270f, percentageToFill, false, fillArcPaint)
    }

    private fun getCurrentPercentageToFill() =
        (ARC_FULL_ROTATION_DEGREE * (currentPercentage / PERCENTAGE_DIVIDER)).toFloat()

    fun animateProgress() {
        val valuesHolder = PropertyValuesHolder.ofFloat("percentage", 0f, currentPercentage)
        val animator = ValueAnimator().apply {
            setValues(valuesHolder)
            duration = 1000
            addUpdateListener {
                val percentage = it.getAnimatedValue(PERCENTAGE_VALUE_HOLDER) as Float
                currentPercentage = percentage
                invalidate()
            }
        }
        animator.start()
    }
}
