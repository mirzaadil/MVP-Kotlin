package com.kotlin.mirzaadil.mvparchitecture.mvp.presenter

import com.kotlin.mirzaadil.mvparchitecture.base.BasePresenter
import com.kotlin.mirzaadil.mvparchitecture.mvp.contract.HotTabContract
import com.kotlin.mirzaadil.mvparchitecture.mvp.model.HotTabModel
import com.kotlin.mirzaadil.mvparchitecture.net.exception.ExceptionHandle


/**
 * @author Mirza Adil
 * desc:TabInfo Presenter
 */
class HotTabPresenter: BasePresenter<HotTabContract.View>(),HotTabContract.Presenter {

    private val hotTabModel by lazy { HotTabModel() }


    override fun getTabInfo() {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = hotTabModel.getTabInfo()
                .subscribe({
                    tabInfo->
                    mRootView?.setTabInfo(tabInfo)
                },{
                    throwable->
                    //Exception handling
                    mRootView?.showError(ExceptionHandle.handleException(throwable), ExceptionHandle.errorCode)
                })
        addSubscription(disposable)
    }
}