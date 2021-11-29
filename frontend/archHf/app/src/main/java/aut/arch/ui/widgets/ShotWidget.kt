package aut.arch.ui.widgets

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.constraintlayout.widget.ConstraintLayout
import aut.arch.databinding.WidgetShotBinding
import aut.arch.local_data.models.enums.TargetType
import kotlin.math.pow
import kotlin.math.sqrt


@SuppressLint("ClickableViewAccessibility")
class ShotWidget @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleRes: Int = 0
) : ConstraintLayout(context, attrs, defStyleRes) {

    private val binding =
        WidgetShotBinding.inflate(LayoutInflater.from(context), this, true)

    companion object {
        const val TARGET_USEFUL_RATIO = 194.0 / 235
    }

    var deltaX = 0F
    var deltaY = 0F
    var startingDist = 0F
    var positionX = 0F
    var positionY = 0F
    var x1 = 0f
    var y1 = 0f
    var zoomed = false
    var zoomedIn = false
    val target = binding.shotTarget

    init {
        binding.shotTarget.setOnTouchListener(fun(v: View, m: MotionEvent): Boolean {
            val x = m.rawX
            val y = m.rawY

            when (m.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    deltaX = x - target.x
                    deltaY = y - target.y
                }
                MotionEvent.ACTION_MOVE -> {

                    if (m.pointerCount == 2) {
                        x1 = m.getRawX(1)
                        y1 = m.getRawY(1)

                        val dist = sqrt(
                            (x.toDouble() - x1).pow(2.0)
                                    + (y.toDouble() - y1).pow(2.0)
                        )

                        if (dist > startingDist + 4 && !zoomed) {
                            target.animate()
                                .scaleX(4F)
                                .scaleY(4F)
                                .setInterpolator(AccelerateDecelerateInterpolator())
                                .setDuration(100)
                                .start()

                            zoomedIn = true

                            zoomed = true
                        }

                        if (dist < startingDist - 4 && !zoomed) {
                            target.animate()
                                .scaleX(1F)
                                .scaleY(1F)
                                .setInterpolator(AccelerateDecelerateInterpolator())
                                .setDuration(100)
                                .start()

                            zoomedIn = false

                            zoomed = true
                        }

                    } else {

                        target.animate()
                            .x(0F + x - deltaX)
                            .y(0F + y - deltaY)
                            .setDuration(0)
                            .start()

                        positionX = target.x
                        positionY = target.y
                    }
                }
                MotionEvent.ACTION_POINTER_DOWN -> {
                    x1 = m.getRawX(1)
                    y1 = m.getRawY(1)

                    startingDist = sqrt(
                        (x.toDouble() - x1).pow(2.0)
                                + (y.toDouble() - y1).pow(2.0)
                    ).toFloat()
                }
                MotionEvent.ACTION_POINTER_UP -> {
                    zoomed = false
                }

                else -> {
                }
            }
            invalidate()

            return true
        })
    }

    fun calculatePoints(targetType: TargetType): Int {
        val posValue = target.x.pow(2) + target.y.pow(2)
        val r = ((TARGET_USEFUL_RATIO * width) / 20) * if (zoomedIn) 4 else 1

        Log.d("MAKING", "r: ")

        // Could write with 10-posValue/r but in that case,
        // it can't adapt if the score system chan  ges
        if (targetType == TargetType.FITA)
            return when {
                posValue < r.pow(2) -> 10
                posValue < (2 * r).pow(2) -> 9
                posValue < (3 * r).pow(2) -> 8
                posValue < (4 * r).pow(2) -> 7
                posValue < (5 * r).pow(2) -> 6
                posValue < (6 * r).pow(2) -> 5
                posValue < (7 * r).pow(2) -> 4
                posValue < (8 * r).pow(2) -> 3
                posValue < (9 * r).pow(2) -> 2
                posValue < (10 * r).pow(2) -> 1
                else -> 0
            }
        else
            return 0
    }

    fun resetTarget(){
        target.animate()
            .x(0F)
            .y(0F).scaleX(1F)
            .scaleY(1F)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .setDuration(255)
            .start()

        zoomedIn = false
    }

    fun getXPos(): Int {
        return if(zoomedIn)
            (positionX/4).toInt()
        else
            positionX.toInt()
    }

    fun getYPos(): Int {
        return if(zoomedIn)
            (positionY/4).toInt()
        else
            positionY.toInt()
    }

}