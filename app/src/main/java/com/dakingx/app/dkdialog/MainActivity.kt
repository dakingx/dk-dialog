package com.dakingx.app.dkdialog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.dakingx.dkdialog.custom.LoadingDialogFragment
import com.dakingx.dkdialog.custom.PhotoDialogAction
import com.dakingx.dkdialog.custom.PhotoDialogFragment
import com.dakingx.dkdialog.custom.PhotoDialogListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), PhotoDialogListener {

    private val loadingDialog by lazy {
        LoadingDialogFragment()
    }

    private val photoDialog by lazy {
        PhotoDialogFragment()
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
    }

    override fun onPhotoAction(action: PhotoDialogAction) {
        when (action) {
            PhotoDialogAction.Capture -> toast(R.string.main_tip_capture)
            PhotoDialogAction.Gallery -> toast(R.string.main_tip_gallery)
            PhotoDialogAction.Cancel -> toast(R.string.main_tip_cancel)
        }
    }

    private fun toast(stringResId: Int) {
        Toast.makeText(this, getString(stringResId), Toast.LENGTH_SHORT).show()
    }
}
