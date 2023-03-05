package com.meridiane.customviewclock.presentation.ui

import android.animation.Animator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import com.meridiane.customviewclock.R
import com.meridiane.customviewclock.databinding.AnalogClockBinding
import kotlinx.coroutines.*
import java.util.*

class AnalogClock(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {

    private var binding: AnalogClockBinding =
        AnalogClockBinding.inflate(LayoutInflater.from(context))

    init {
        addView(binding.root)
    }

    private val calendar = Calendar.getInstance()

    private var hour =
        ((calendar.get(Calendar.HOUR_OF_DAY)) % 12) * 30 // изначальный градус поворота для часа
    private var minute =
        (calendar.get(Calendar.MINUTE)) * 6 // изначальный градус поворота для минуты
    private var second =
        (calendar.get(Calendar.SECOND)) * 6 // изначальный градус поворота для секунды

    fun startAnimate() {
        binding.imageViewSec.animate()
            .rotationBy(second.toFloat())

        binding.imageViewMin.animate()
            .rotationBy(minute.toFloat())

        binding.imageViewHour.animate()
            .rotationBy(hour.toFloat())


        binding.imageViewSec.animate()
            .setListener(object : Animator.AnimatorListener {

                    override fun onAnimationStart(animation: Animator) {}

                    override fun onAnimationEnd(animation: Animator) {

                            binding.imageViewSec.animate()
                                .rotationBy(6f)
                                .setDuration(ANIMATION_DURATION.toLong())
                                .start()

                            second += 6

                            if (second == 360) {

                                binding.imageViewMin.animate().rotationBy(6f)
                                minute += 6
                                second = 0
                            }

                            if (minute == 360) {

                                binding.imageViewHour.animate().rotationBy(30f)
                                minute = 0

                            }

                    }

                    override fun onAnimationCancel(animation: Animator) {}

                    override fun onAnimationRepeat(animation: Animator) {}

            }).start()
    }

companion object{
    private const val ANIMATION_DURATION = 950
}

}