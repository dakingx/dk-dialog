package com.dakingx.dkdialog.base

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import androidx.annotation.LayoutRes
import androidx.annotation.StyleRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.dakingx.dkdialog.R
import com.dakingx.dkdialog.ext.dp2Px

sealed class BaseDialogAction {
    object Cancel : BaseDialogAction()
}

interface BaseDialogListener {
    fun onBaseAction(action: BaseDialogAction)
}

abstract class BaseDialogFragment : DialogFragment() {

    companion object {
        const val ARG_FM_NAME = "arg_fm_name"
        const val ARG_LAYOUT_ID = "arg_layout_id"
        const val ARG_FM_THEME = "arg_fm_theme"
        const val ARG_WIDTH = "arg_width"
        const val ARG_HEIGHT = "arg_height"
        const val ARG_HORIZONTAL_MARGIN = "arg_horizontal_margin"
        const val ARG_VERTICAL_MARGIN = "arg_vertical_margin"
        const val ARG_GRAVITY = "arg_gravity"
        const val ARG_DIM_AMOUNT = "arg_dim_amount"
        const val ARG_TRANSITION_ANIM = "arg_transition_anim"
        const val ARG_CANCEL_OUTSIDE = "arg_cancel_outside"
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

    private var listener: BaseDialogListener? = null

    abstract fun convertView(holder: ViewHolder)

    fun show(fm: FragmentManager) {
        val transaction = fm.beginTransaction()
        if (this.isAdded) {
            transaction.remove(this)
        }
        transaction.add(this, fmName).commitNow()
    }

    protected open fun restoreState(bundle: Bundle?) {
        bundle?.apply {
            if (containsKey(ARG_FM_NAME)) {
                fmName = getString(ARG_FM_NAME, genDefaultFmName())
            }
            if (containsKey(ARG_LAYOUT_ID)) {
                layoutId = getInt(ARG_LAYOUT_ID, getDefaultLayoutId())
            }
            if (containsKey(ARG_FM_THEME)) {
                fmTheme = getInt(ARG_FM_THEME)
            }
            if (containsKey(ARG_WIDTH)) {
                width = getInt(ARG_WIDTH, WindowManager.LayoutParams.WRAP_CONTENT)
            }
            if (containsKey(ARG_HEIGHT)) {
                height = getInt(ARG_HEIGHT, WindowManager.LayoutParams.WRAP_CONTENT)
            }
            if (containsKey(ARG_HORIZONTAL_MARGIN)) {
                horizontalMargin = getInt(ARG_HORIZONTAL_MARGIN)
            }
            if (containsKey(ARG_VERTICAL_MARGIN)) {
                verticalMargin = getInt(ARG_VERTICAL_MARGIN)
            }
            if (containsKey(ARG_GRAVITY)) {
                gravity = getInt(ARG_GRAVITY, Gravity.CENTER)
            }
            if (containsKey(ARG_DIM_AMOUNT)) {
                dimAmount = getFloat(ARG_DIM_AMOUNT, 0.5F)
            }
            if (containsKey(ARG_TRANSITION_ANIM)) {
                transitionAnim = getInt(ARG_TRANSITION_ANIM)
            }
            if (containsKey(ARG_CANCEL_OUTSIDE)) {
                cancelOutside = getBoolean(ARG_CANCEL_OUTSIDE)
            }
        }
    }

    protected open fun storeState(bundle: Bundle) {
        bundle.let {
            it.putString(ARG_FM_NAME, fmName)
            it.putInt(ARG_LAYOUT_ID, layoutId)
            it.putInt(ARG_FM_THEME, fmTheme)
            it.putInt(ARG_WIDTH, width)
            it.putInt(ARG_HEIGHT, height)
            it.putInt(ARG_HORIZONTAL_MARGIN, horizontalMargin)
            it.putInt(ARG_VERTICAL_MARGIN, verticalMargin)
            it.putInt(ARG_GRAVITY, gravity)
            it.putFloat(ARG_DIM_AMOUNT, dimAmount)
            it.putInt(ARG_TRANSITION_ANIM, transitionAnim)
            it.putBoolean(ARG_CANCEL_OUTSIDE, cancelOutside)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        restoreState(arguments)
        restoreState(savedInstanceState)

        if (fmTheme != 0) {
            setStyle(STYLE_NO_TITLE, fmTheme)
        }

        listener = context as? BaseDialogListener
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        storeState(outState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(layoutId, container, false)
        convertView(ViewHolder(view))
        return view
    }

    override fun onStart() {
        super.onStart()

        initParams()
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)

        listener?.onBaseAction(BaseDialogAction.Cancel)
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
