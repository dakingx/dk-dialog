package com.dakingx.dkdialog.base

import android.util.SparseArray
import android.view.View

class ViewHolder(
    val convertView: View
) {
    private val views = SparseArray<View>()

    fun <T : View> getView(viewId: Int): View {
        var view = views.get(viewId)
        if (view == null) {
            view = convertView.findViewById(viewId)
            views.put(viewId, view)
        }
        return view
    }
}
