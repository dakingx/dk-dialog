package com.dakingx.dkdialog.custom

import android.view.Gravity
import com.dakingx.dkdialog.R
import com.dakingx.dkdialog.base.BaseDialogFragment
import com.dakingx.dkdialog.base.ViewHolder

class LoadingDialogFragment : BaseDialogFragment() {

    init {
        fmName = "loading_dialog_fragment_${System.currentTimeMillis()}"
        layoutId = R.layout.dialog_loading
        gravity = Gravity.CENTER
        cancelOutside = true
    }

    override fun convertView(holder: ViewHolder, fragment: BaseDialogFragment) {
    }
}
