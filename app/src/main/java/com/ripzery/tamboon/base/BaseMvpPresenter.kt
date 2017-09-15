package com.ripzery.tamboon.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by ripzery on 9/16/17.
 */
abstract class BaseMvpPresenter<V : BaseMvpContract.BaseView> : BaseMvpContract.BasePresenter<V> {
    protected var mView: V? = null
    private var mCompositeSubscription: CompositeDisposable? = null
    override fun attachView(view: V) {
        mCompositeSubscription = CompositeDisposable()
        mView = view
    }

    override fun detachView() {
        mView = null
        mCompositeSubscription?.dispose()
        mCompositeSubscription = null
    }

    override fun addSubscription(d: Disposable) {
        mCompositeSubscription?.add(d)
    }

    override fun unSubscription() {
        mCompositeSubscription?.clear()
    }

}