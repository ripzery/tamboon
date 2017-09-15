package com.ripzery.tamboon

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ripzery.tamboon.Network.ApiService
import kotlinx.android.synthetic.main.activity_donate.*

class DonateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donate)

        btnDonate.setOnClickListener {
            ApiService.mTamboonApiClient
                    .donate("Phuchit", "pkey_test_58i9pow3dgadkocuwlm", 10000)

        }
    }
}
