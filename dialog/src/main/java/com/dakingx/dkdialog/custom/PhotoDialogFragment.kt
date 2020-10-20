package com.dakingx.dkdialog.custom

import android.content.DialogInterface
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import androidx.appcompat.widget.AppCompatButton
import com.dakingx.dkdialog.R
import com.dakingx.dkdialog.base.BaseDialogFragment
import com.dakingx.dkdialog.base.ViewHolder

sealed class PhotoDialogAction {
    object Capture : PhotoDialogAction()
    object Gallery : PhotoDialogAction()
    object Cancel : PhotoDialogAction()
}

interface PhotoDialogListener {
    fun onPhotoAction(action: PhotoDialogAction, dialog: PhotoDialogFragment)
}

class PhotoDialogFragment : BaseDialogFragment() {

    init {
        fmName = "photo_dialog_fragment_${System.currentTimeMillis()}"
        layoutId = R.layout.dialog_photo
        width = WindowManager.LayoutParams.MATCH_PARENT
        height = WindowManager.LayoutParams.WRAP_CONTENT
        gravity = Gravity.BOTTOM
        cancelOutside = true
    }

    var photoDialogListener: PhotoDialogListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (photoDialogListener == null) {
            photoDialogListener = context as? PhotoDialogListener
        }
    }

    override fun onDestroy() {
        photoDialogListener = null

        super.onDestroy()
    }

    override fun convertView(holder: ViewHolder) {
        // 拍照
        val captureBtn = holder.getView<AppCompatButton>(R.id.btn_capture)
        captureBtn.setOnClickListener {
            photoDialogListener?.onPhotoAction(PhotoDialogAction.Capture, this)
            dismiss()
        }
        // 相册
        val galleryBtn = holder.getView<AppCompatButton>(R.id.btn_gallery)
        galleryBtn.setOnClickListener {
            photoDialogListener?.onPhotoAction(PhotoDialogAction.Gallery, this)
            dismiss()
        }
        // 取消
        val cancelBtn = holder.getView<AppCompatButton>(R.id.btn_cancel)
        cancelBtn.setOnClickListener {
            photoDialogListener?.onPhotoAction(PhotoDialogAction.Cancel, this)
            dismiss()
        }
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)

        photoDialogListener?.onPhotoAction(PhotoDialogAction.Cancel, this)
    }
}
