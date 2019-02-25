package com.kotlin.mirzaadil.mvparchitecture.mvp.presenter

import com.kotlin.mirzaadil.mvparchitecture.base.BasePresenter
import com.kotlin.mirzaadil.mvparchitecture.mvp.contract.RankContract
import com.kotlin.mirzaadil.mvparchitecture.mvp.model.RankModel
import com.kotlin.mirzaadil.mvparchitecture.net.exception.ExceptionHandle


/**
 *@author Mirza Adil
 * desc:TabInfo Presenter
 */
class RankPresenter : BasePresenter<RankContract.View>(), RankContract.Presenter {

    private val rankModel by lazy { RankModel() }


    /**
     *  Request leaderboard data
     */
    override fun requestRankList(apiUrl: String) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = rankModel.requestRankList(apiUrl)
                .subscribe({ issue ->
                    mRootView?.apply {
                        dismissLoading()
                        setRankList(issue.itemList)
                    }
                }, { throwable ->
                    mRootView?.apply {
                        //Exception handling
                        showError(ExceptionHandle.handleException(throwable),ExceptionHandle.errorCode)
                    }
                })
        addSubscription(disposable)
    }
}