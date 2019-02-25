package com.kotlin.mirzaadil.mvparchitecture.mvp.contract

import com.kotlin.mirzaadil.mvparchitecture.base.IBaseView
import com.kotlin.mirzaadil.mvparchitecture.base.IPresenter
import com.kotlin.mirzaadil.mvparchitecture.mvp.model.bean.HomeBean

/**
 * @author Mirza Adil
 * desc: Contract class
 */
interface FollowContract {

    interface View : IBaseView {
        /**
         * Set attention information data
         */
        fun setFollowInfo(issue: HomeBean.Issue)

        fun showError(errorMsg: String, errorCode: Int)
    }


    interface Presenter : IPresenter<View> {
        /**
         * Get List
         */
        fun requestFollowList()

        /**
         * load more
         */
        fun loadMoreData()
    }
}