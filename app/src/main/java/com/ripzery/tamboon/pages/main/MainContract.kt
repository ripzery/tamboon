package com.ripzery.tamboon.pages.main

import com.ripzery.tamboon.base.BaseMvpContract
import com.ripzery.tamboon.data.Tamboon

/**
 * Created by ripzery on 9/16/17.
 */
interface MainContract {
    interface View : BaseMvpContract.BaseView {
        fun showCharitiesList(list: MutableList<Tamboon.Charity>)
        fun showDonateScreen(charity: Tamboon.Charity)
        fun showError(errorMsg: String)
    }

    interface Presenter : BaseMvpContract.BasePresenter<View> {
        fun loadCharitiesList()
        fun handleCharityClicked(charity: Tamboon.Charity)
    }
}