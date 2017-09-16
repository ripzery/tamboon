package com.ripzery.tamboon.pages.donate

import android.util.Log
import co.omise.android.Client
import co.omise.android.TokenRequest
import co.omise.android.TokenRequestListener
import co.omise.android.models.Token
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import com.ripzery.tamboon.base.BaseMvpPresenter
import com.ripzery.tamboon.data.Tamboon
import com.ripzery.tamboon.network.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by ripzery on 9/16/17.
 */
class DonatePresenter : BaseMvpPresenter<DonateContract.View>(), DonateContract.Presenter {
    private val mClient by lazy { Client("pkey_test_58i9pow3dgadkocuwlm") }

    override fun donate(tokenRequest: TokenRequest, name: String, amount: Int) {
        mView?.enableUI(false)
        mView?.showLoading()
        mClient.send(tokenRequest, object : TokenRequestListener {
            override fun onTokenRequestSucceed(tokenRequest: TokenRequest, token: Token) {
                val d = ApiService.mTamboonApiClient
                        .donate(Tamboon.DonateRequest(name, token.id, amount))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doFinally {
                            mView?.enableUI(true)
                            mView?.hideLoading()
                        }
                        .subscribe({
                            mView?.showDonateSuccess()
                        }, {
                            if (it is HttpException) {
                                // Drop empty new line
                                var errorText = String(it.response().errorBody()!!.bytes().dropLast(1).toByteArray())

                                // Remove unnecessary error text
                                errorText = errorText.split(" ").drop(1).joinToString(" ")

                                // Show error cause to user
                                mView?.showDonateFailed(errorText.capitalize())
                            } else {
                                mView?.showDonateFailed("Unknown error")
                            }
                        })

                addSubscription(d)
            }

            override fun onTokenRequestFailed(tokenRequest: TokenRequest, throwable: Throwable) {
                Log.d("token request", throwable.message)
                mView?.showDonateFailed(throwable.message!!.capitalize())
                mView?.enableUI(true)
                mView?.hideLoading()
            }
        })
    }
}