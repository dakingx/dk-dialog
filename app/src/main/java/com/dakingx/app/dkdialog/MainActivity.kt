package com.dakingx.app.dkdialog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dakingx.dkdialog.custom.LoadingDialogFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val loadingDialog by lazy {
        LoadingDialogFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadingBtn.setOnClickListener {
            loadingDialog.show(supportFragmentManager)
        }
    }
}
