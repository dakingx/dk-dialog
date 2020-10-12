package com.dakingx.dkdialog.base

import android.os.Bundle

class DKDialog : BaseDialogFragment() {

    private var listener: DKDialogListener? = null

    override fun convertView(holder: ViewHolder) {
        listener?.convertView(holder, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        listener = context as? DKDialogListener
    }

    override fun onDestroy() {
        listener = null

        super.onDestroy()
    }
}

interface DKDialogListener {
    fun convertView(holder: ViewHolder, dialog: DKDialog)
}
