package com.dakingx.app.dkdialog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.WindowManager
import android.widget.Toast
import com.dakingx.dkdialog.base.DKDialog
import com.dakingx.dkdialog.base.DKDialogListener
import com.dakingx.dkdialog.base.ViewHolder
import com.dakingx.dkdialog.custom.LoadingDialogFragment
import com.dakingx.dkdialog.custom.PhotoDialogAction
import com.dakingx.dkdialog.custom.PhotoDialogFragment
import com.dakingx.dkdialog.custom.PhotoDialogListener
import kotlinx.android.synthetic.main.activity_main.*

private const val TAG = "dk-dialog"

class MainActivity : AppCompatActivity(), PhotoDialogListener, DKDialogListener {

    private val loadingDialog by lazy {
        LoadingDialogFragment()
    }

    private val photoDialog by lazy {
        PhotoDialogFragment()
    }

    private val errorTipDialog by lazy {
        DKDialog().apply {
            fmName = "error_tip_dialog" // fragment名称
            layoutId = R.layout.dialog_message // dialog布局
            fmTheme = R.style.DKDialogStyle // dialog主题
            width = WindowManager.LayoutParams.MATCH_PARENT // dialog宽度
            height = WindowManager.LayoutParams.WRAP_CONTENT // dialog高度
            horizontalMargin = 20 // dialog的水平间距，仅当width为MATCH_PARENT时有效
            verticalMargin = 0 // dialog的垂直间距，仅当height为MATCH_PARENT时有效
            gravity = Gravity.CENTER // dialog的位置，默认为居中
            dimAmount = 0.5F // 灰色背景透明度，默认为0.5F，范围为0~1.0
            transitionAnim = R.style.FadeTransitionAnim // dialog的入场和出场动画
            cancelOutside = true // 点击dialog布局外是否可取消，默认为false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadingBtn.setOnClickListener {
            loadingDialog.show(supportFragmentManager)
        }

        photoBtn.setOnClickListener {
            photoDialog.show(supportFragmentManager)
        }

        errorTipBtn.setOnClickListener {
            errorTipDialog.show(supportFragmentManager)
        }
    }

    override fun onPhotoAction(action: PhotoDialogAction, dialog: PhotoDialogFragment) {
        when (action) {
            PhotoDialogAction.Capture -> toast(R.string.main_tip_capture)
            PhotoDialogAction.Gallery -> toast(R.string.main_tip_gallery)
            PhotoDialogAction.Cancel -> toast(R.string.main_tip_cancel)
        }
    }

    private fun toast(stringResId: Int) {
        Toast.makeText(this, getString(stringResId), Toast.LENGTH_SHORT).show()
    }

    override fun convertView(holder: ViewHolder, dialog: DKDialog) {
        if (dialog === errorTipDialog) {
            Log.d(TAG, "errorTipDialog#convertView()")
        }
    }
}
