package com.ripzery.tamboon.pages

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import co.omise.android.CardNumber
import co.omise.android.TokenRequest
import co.omise.android.ui.CreditCardActivity
import co.omise.android.ui.ExpiryMonthSpinnerAdapter
import co.omise.android.ui.ExpiryYearSpinnerAdapter
import com.ripzery.tamboon.R
import com.ripzery.tamboon.SuccessActivity
import com.ripzery.tamboon.base.BaseMvpActivity
import kotlinx.android.synthetic.main.activity_donate.*


class DonateActivity : BaseMvpActivity<DonateContract.View, DonateContract.Presenter>(), DonateContract.View {
    override val mPresenter: DonateContract.Presenter by lazy { DonatePresenter() }
    private var mToken: String? = null
    private val mExpiryMonthAdapter by lazy { object : ExpiryMonthSpinnerAdapter() {} }
    private val mExpiryYearAdapter by lazy { object : ExpiryYearSpinnerAdapter() {} }

    companion object {
        val EXTRA_ID = "DonateActivity.id"
        val EXTRA_NAME = "DonateActivity.name"
        val EXTRA_LOGO_URL = "DonateActivity.logoUrl"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donate)

        initInstance()
        btnDonate.setOnClickListener {
            val charityName = intent.getStringExtra(EXTRA_NAME)
            val tokenRequest = TokenRequest().apply {
                number = etCreditCard.text.toString()
                name = etName.text.toString()
                expirationMonth = spinExpiryMonth.selectedItem as Int
                expirationYear = spinExpiryYear.selectedItem as Int
                securityCode = etCvv.text.toString()
            }
            mPresenter.donate(tokenRequest, charityName, etAmount.text.toString().toInt())
        }
    }

    private fun initInstance() {
        spinExpiryMonth.adapter = mExpiryMonthAdapter
        spinExpiryYear.adapter = mExpiryYearAdapter
        etCreditCard.addTextChangedListener(ActivityTextWatcher())
    }
    
    // Override MVP method zone
    override fun showDonateSuccess() {
        startActivity(Intent(this@DonateActivity, SuccessActivity::class.java))
    }

    override fun showDonateFailed(failedMsg: String) {
        Toast.makeText(this@DonateActivity, failedMsg, Toast.LENGTH_SHORT).show()
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    // Inner class zone

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
}
