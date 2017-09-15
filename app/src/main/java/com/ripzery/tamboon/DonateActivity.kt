package com.ripzery.tamboon

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import co.omise.android.ui.CreditCardActivity
import com.ripzery.tamboon.Network.ApiService
import com.ripzery.tamboon.data.Tamboon
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_donate.*
import retrofit2.HttpException

class DonateActivity : AppCompatActivity() {

    private val REQUEST_CC = 100
    private var mToken: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donate)

        val intent = Intent(this, CreditCardActivity::class.java)
        intent.putExtra(CreditCardActivity.EXTRA_PKEY, "pkey_test_58i9pow3dgadkocuwlm")
        startActivityForResult(intent, REQUEST_CC)

        btnDonate.setOnClickListener {
            ApiService.mTamboonApiClient
                    .donate(Tamboon.DonateRequest("Phuchit", mToken!!, etAmount.text.toString().toInt()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        Log.d("Test", it.success.toString())
                        startActivity(Intent(this@DonateActivity, SuccessActivity::class.java))
                    }, {
                        Toast.makeText(this@DonateActivity, "Error", Toast.LENGTH_SHORT).show()
                        val response = (it as HttpException).response().errorBody()
                        Log.w("Error", response.toString())
                    })
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == CreditCardActivity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CC -> {
                    mToken = data?.getStringExtra(CreditCardActivity.EXTRA_TOKEN)
                    mToken?.let { layoutAddCreditCard.visibility = View.GONE }
                    btnDonate.isEnabled = true
                    Log.d("Token", mToken ?: "null")
                }
                else -> super.onActivityResult(requestCode, resultCode, data)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}
