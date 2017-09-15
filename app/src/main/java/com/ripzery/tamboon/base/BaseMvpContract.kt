package com.ripzery.tamboon.base

import io.reactivex.disposables.Disposable

/**
 * Created by ripzery on 9/16/17.
 */
interface BaseMvpContract {
    interface BaseView {
        fun showLoading()
        fun hideLoading()
    }

    interface BasePresenter<in V : BaseView> {
        fun attachView(view: V)
        fun detachView()
        fun unSubscription()
        fun addSubscription(d: Disposable)
    }
}