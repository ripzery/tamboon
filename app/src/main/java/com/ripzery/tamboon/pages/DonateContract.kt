package com.ripzery.tamboon.pages

import co.omise.android.TokenRequest
import com.ripzery.tamboon.base.BaseMvpContract

/**
 * Created by ripzery on 9/16/17.
 */
interface DonateContract {
    interface View : BaseMvpContract.BaseView {
        fun showDonateSuccess()
        fun showDonateFailed(failedMsg: String)
    }

    interface Presenter : BaseMvpContract.BasePresenter<View> {
        fun donate(tokenRequest: TokenRequest, name: String, amount: Int)
    }
}