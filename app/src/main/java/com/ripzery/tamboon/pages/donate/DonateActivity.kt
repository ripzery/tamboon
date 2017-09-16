package com.ripzery.tamboon.pages.donate

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import co.omise.android.CardNumber
import co.omise.android.TokenRequest
import co.omise.android.ui.ExpiryMonthSpinnerAdapter
import co.omise.android.ui.ExpiryYearSpinnerAdapter
import com.ripzery.tamboon.R
import com.ripzery.tamboon.base.BaseMvpActivity
import com.ripzery.tamboon.extensions.toast
import com.ripzery.tamboon.pages.success.SuccessActivity
import kotlinx.android.synthetic.main.activity_donate.*


class DonateActivity : BaseMvpActivity<DonateContract.View, DonateContract.Presenter>(), DonateContract.View {
    override val mPresenter: DonateContract.Presenter by lazy { DonatePresenter() }
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
    }

    private fun initInstance() {
        setSupportActionBar(toolbarDonate)
        supportActionBar?.title = "Donate"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        spinExpiryMonth.adapter = mExpiryMonthAdapter
        spinExpiryYear.adapter = mExpiryYearAdapter
        etCreditCard.addTextChangedListener(ActivityTextWatcher())

        btnDonate.setOnClickListener {
            val charityName = intent.getStringExtra(EXTRA_NAME)
            val tokenRequest = TokenRequest().apply {
                number = etCreditCard.text.toString()
                name = etName.text.toString()
                expirationMonth = spinExpiryMonth.selectedItem as Int
                expirationYear = spinExpiryYear.selectedItem as Int
                securityCode = etCvv.text.toString()
            }
            val amount = etAmount.text.toString()
            mPresenter.donate(tokenRequest, charityName, amount)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return false
    }

    // Override MVP method zone
    override fun showDonateSuccess() {
        startActivity(Intent(this@DonateActivity, SuccessActivity::class.java))
    }

    override fun showDonateFailed(failedMsg: String) {
        toast(failedMsg)
    }

    override fun showLoading() {
        layoutLoading.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        layoutLoading.visibility = View.GONE
    }

    override fun enableUI(enable: Boolean) {
        btnDonate.isEnabled = enable
        etCreditCard.isEnabled = enable
        etName.isEnabled = enable
        spinExpiryMonth.isEnabled = enable
        spinExpiryYear.isEnabled = enable
        etCvv.isEnabled = enable
        etAmount.isEnabled = enable
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
