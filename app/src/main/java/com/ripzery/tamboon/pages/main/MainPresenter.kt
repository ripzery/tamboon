package com.ripzery.tamboon.pages.main

import com.ripzery.tamboon.base.BaseMvpPresenter
import com.ripzery.tamboon.data.Tamboon
import com.ripzery.tamboon.network.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.net.ConnectException

/**
 * Created by ripzery on 9/16/17.
 */
class MainPresenter : BaseMvpPresenter<MainContract.View>(), MainContract.Presenter {
    override fun loadCharitiesList() {
        ApiService.mTamboonApiClient.getCharities().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
            mView?.showCharitiesList(it.toMutableList())
        }, {
            if (it is ConnectException) {
                mView?.showError(it.message!!)
            }
        })
    }

    override fun handleCharityClicked(charity: Tamboon.Charity) {
        mView?.showDonateScreen(charity)
    }
}