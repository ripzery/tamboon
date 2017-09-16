package com.ripzery.tamboon.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * Created by ripzery on 9/16/17.
 */
abstract class BaseMvpActivity<in V : BaseMvpContract.BaseView, out P : BaseMvpContract.BasePresenter<V>> : AppCompatActivity(), BaseMvpContract.BaseView {
    protected abstract val mPresenter: P

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            mPresenter.attachView(this as V)
        } catch (ex: ClassCastException) {
            throw ClassCastException("Activity should be implemented BaseView interface.")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

    override fun showLoading() {}
    override fun hideLoading() {}
}