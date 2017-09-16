package com.ripzery.tamboon.pages.success

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ripzery.tamboon.pages.main.MainActivity
import com.ripzery.tamboon.R
import kotlinx.android.synthetic.main.activity_success.*

class SuccessActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success)
        btnDismiss.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            })
        }
    }

    override fun onBackPressed() {
        startActivity(Intent(this, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        })
    }
}
