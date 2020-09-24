package com.dakingx.dkdialog.base

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.annotation.LayoutRes
import androidx.annotation.StyleRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.dakingx.dkdialog.R
import com.dakingx.dkdialog.ext.dp2Px

abstract class BaseDialogFragment : DialogFragment() {

    companion object {
        private const val PARAM_FM_NAME = "param_fm_name"
        private const val PARAM_LAYOUT_ID = "param_layout_id"
        private const val PARAM_FM_THEME = "param_fm_theme"
        private const val PARAM_WIDTH = "param_width"
        private const val PARAM_HEIGHT = "param_height"
        private const val PARAM_HORIZONTAL_MARGIN = "param_horizontal_margin"
        private const val PARAM_VERTICAL_MARGIN = "param_vertical_margin"
        private const val PARAM_GRAVITY = "param_gravity"
        private const val PARAM_DIM_AMOUNT = "param_dim_amount"
        private const val PARAM_TRANSITION_ANIM = "param_transition_anim"
        private const val PARAM_CANCEL_OUTSIDE = "param_cancel_outside"
    }

    var fmName = genDefaultFmName()

    @LayoutRes
    var layoutId = getDefaultLayoutId()

    @StyleRes
    var fmTheme = 0

    var width = WindowManager.LayoutParams.WRAP_CONTENT
    var height = WindowManager.LayoutParams.WRAP_CONTENT
    var horizontalMargin = 0
    var verticalMargin = 0

    var gravity = Gravity.CENTER

    var dimAmount = 0.5F

    @StyleRes
    var transitionAnim = 0

    var cancelOutside = false

    abstract fun convertView(holder: ViewHolder, fragment: BaseDialogFragment)

    fun show(fm: FragmentManager) {
        val transaction = fm.beginTransaction()
        if (this.isAdded) {
            transaction.remove(this).commitNow()
        }
        transaction.add(this, fmName).commitNow()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        savedInstanceState?.also {
            fmName = it.getString(PARAM_FM_NAME, genDefaultFmName())
            layoutId = it.getInt(PARAM_LAYOUT_ID, getDefaultLayoutId())
            fmTheme = it.getInt(PARAM_FM_THEME, 0)
            width = it.getInt(PARAM_WIDTH, WindowManager.LayoutParams.WRAP_CONTENT)
            height = it.getInt(PARAM_HEIGHT, WindowManager.LayoutParams.WRAP_CONTENT)
            horizontalMargin = it.getInt(PARAM_HORIZONTAL_MARGIN, 0)
            verticalMargin = it.getInt(PARAM_VERTICAL_MARGIN, 0)
            gravity = it.getInt(PARAM_GRAVITY, 0)
            dimAmount = it.getFloat(PARAM_DIM_AMOUNT, 0.5F)
            transitionAnim = it.getInt(PARAM_TRANSITION_ANIM, 0)
            cancelOutside = it.getBoolean(PARAM_CANCEL_OUTSIDE, false)
        }

        if (fmTheme != 0) {
            setStyle(STYLE_NO_TITLE, fmTheme)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.also {
            it.putString(PARAM_FM_NAME, fmName)
            it.putInt(PARAM_LAYOUT_ID, layoutId)
            it.putInt(PARAM_FM_THEME, fmTheme)
            it.putInt(PARAM_WIDTH, width)
            it.putInt(PARAM_HEIGHT, height)
            it.putInt(PARAM_HORIZONTAL_MARGIN, horizontalMargin)
            it.putInt(PARAM_VERTICAL_MARGIN, verticalMargin)
            it.putInt(PARAM_GRAVITY, gravity)
            it.putFloat(PARAM_DIM_AMOUNT, dimAmount)
            it.putInt(PARAM_TRANSITION_ANIM, transitionAnim)
            it.putBoolean(PARAM_CANCEL_OUTSIDE, cancelOutside)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(layoutId, container, false)
        convertView(ViewHolder(view), this)
        return view
    }

    override fun onStart() {
        super.onStart()

        initParams()
    }

    @SuppressLint("RtlHardcoded")
    private fun initParams() {
        val ctx = context

        ctx?.let {
            dialog?.window?.let {
                val windowAttrs = it.attributes
                // 调节灰色背景的透明度
                windowAttrs.dimAmount = dimAmount
                // 对齐方式
                windowAttrs.gravity = gravity
                // 转场动画
                val windowAnim = if (transitionAnim == 0) {
                    when (gravity) {
                        // 左
                        Gravity.START, Gravity.START or Gravity.BOTTOM, Gravity.START or Gravity.TOP,
                        Gravity.LEFT, Gravity.LEFT or Gravity.BOTTOM, Gravity.LEFT or Gravity.TOP -> R.style.LeftTransitionAnim
                        // 上
                        Gravity.TOP -> R.style.TopTransitionAnim
                        // 右
                        Gravity.END, Gravity.END or Gravity.BOTTOM, Gravity.END or Gravity.TOP,
                        Gravity.RIGHT, Gravity.RIGHT or Gravity.BOTTOM, Gravity.RIGHT or Gravity.TOP -> R.style.RightTransitionAnim
                        // 下
                        Gravity.BOTTOM -> R.style.BottomTransitionAnim
                        // 其他
                        else -> R.style.FadeTransitionAnim
                    }
                } else {
                    transitionAnim
                }
                it.setWindowAnimations(windowAnim)
                // 宽
                if (width > 0) {
                    windowAttrs.width = ctx.dp2Px(width)
                } else if (width == 0 || width == WindowManager.LayoutParams.MATCH_PARENT) {
                    windowAttrs.width = WindowManager.LayoutParams.MATCH_PARENT
                } else {
                    windowAttrs.width = WindowManager.LayoutParams.WRAP_CONTENT
                }
                if (horizontalMargin > 0) {
                    windowAttrs.horizontalMargin = (2 * ctx.dp2Px(horizontalMargin)).toFloat()
                }
                // 高
                if (height > 0) {
                    windowAttrs.height = ctx.dp2Px(height)
                } else if (height == 0 || height == WindowManager.LayoutParams.MATCH_PARENT) {
                    windowAttrs.height = WindowManager.LayoutParams.MATCH_PARENT
                } else {
                    windowAttrs.height = WindowManager.LayoutParams.WRAP_CONTENT
                }
                if (verticalMargin > 0) {
                    windowAttrs.verticalMargin = (2 * ctx.dp2Px(verticalMargin)).toFloat()
                }
                // 设置
                it.attributes = windowAttrs
            }
        }

        isCancelable = cancelOutside
    }

    private fun genDefaultFmName() = "base_dialog_fragment_${System.currentTimeMillis()}"

    private fun getDefaultLayoutId() = R.layout.dialog_loading
}
