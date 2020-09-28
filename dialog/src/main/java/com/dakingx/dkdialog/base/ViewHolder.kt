package com.dakingx.dkdialog.base

import android.util.SparseArray
import android.view.View

class ViewHolder(
    val convertView: View
) {
    private val views = SparseArray<View>()

    @Suppress("UNCHECKED_CAST")
    fun <T : View> getView(viewId: Int): T {
        var view = views.get(viewId)
        if (view == null) {
            view = convertView.findViewById(viewId)
            views.put(viewId, view)
        }
        return view as T
    }
}
