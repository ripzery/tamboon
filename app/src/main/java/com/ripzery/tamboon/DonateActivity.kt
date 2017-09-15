package com.ripzery.tamboon

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import co.omise.android.CardNumber
import co.omise.android.ui.CreditCardActivity
import co.omise.android.ui.ExpiryMonthSpinnerAdapter
import co.omise.android.ui.ExpiryYearSpinnerAdapter
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import com.ripzery.tamboon.network.ApiService
import com.ripzery.tamboon.data.Tamboon
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_donate.*


class DonateActivity : AppCompatActivity() {

    private val REQUEST_CC = 100
    private var mToken: String? = null
    private val mExpiryMonthAdapter by lazy {
        object : ExpiryMonthSpinnerAdapter() {

        }
    }
    private val mExpiryYearAdapter by lazy {
        object : ExpiryYearSpinnerAdapter() {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donate)

        initInstance()
//        val intent = Intent(this, CreditCardActivity::class.java)
//        intent.putExtra(CreditCardActivity.EXTRA_PKEY, "pkey_test_58i9pow3dgadkocuwlm")
//        startActivityForResult(intent, REQUEST_CC)

        btnDonate.setOnClickListener {

            Log.d("Card month", spinExpiryMonth.selectedItem.toString())
            Log.d("Card year", spinExpiryYear.selectedItem.toString())

            ApiService.mTamboonApiClient
                    .donate(Tamboon.DonateRequest("Phuchit", mToken!!, etAmount.text.toString().toInt()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        Log.d("Test", it.success.toString())
                        startActivity(Intent(this@DonateActivity, SuccessActivity::class.java))
                    }, {
                        if (it is HttpException) {
                            // Drop empty new line
                            var errorText = String(it.response().errorBody()!!.bytes().dropLast(1).toByteArray())

                            // Remove unnecessary error text
                            errorText = errorText.split(" ").drop(1).joinToString(" ")

                            // Show error cause to user
                            Toast.makeText(this@DonateActivity, errorText, Toast.LENGTH_SHORT).show()
                        }
                    })
        }

    }

    private inner class ActivityTextWatcher : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable) {
            val pan = s.toString()
            if (pan.length > 6) {
                val brand = CardNumber.brand(pan)
                if (brand != null && brand.logoResourceId > -1) {
                    ivCardBrand.setImageResource(brand.logoResourceId)
                    return
                }
            }

            ivCardBrand.setImageDrawable(null)
        }
    }

    private fun initInstance() {
        spinExpiryMonth.adapter = mExpiryMonthAdapter
        spinExpiryYear.adapter = mExpiryYearAdapter
        etCreditCard.addTextChangedListener(ActivityTextWatcher())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == CreditCardActivity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CC -> {
                    mToken = data?.getStringExtra(CreditCardActivity.EXTRA_TOKEN)
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
