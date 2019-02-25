package com.kotlin.mirzaadil.mvparchitecture.mvp.contract

import com.kotlin.mirzaadil.mvparchitecture.base.IBaseView
import com.kotlin.mirzaadil.mvparchitecture.base.IPresenter
import com.kotlin.mirzaadil.mvparchitecture.mvp.model.bean.HomeBean


/**
 * @author Mirza Adil
 * Contract class
 */

interface HomeContract {

    interface View : IBaseView {

        /**
         * Set the data for the first request
         */
        fun setHomeData(homeBean: HomeBean)

        /**
         * Set to load more data
         */
        fun setMoreData(itemList:ArrayList<HomeBean.Issue.Item>)

        /**
         * Display error message
         */
        fun showError(msg: String,errorCode:Int)


    }

    interface Presenter : IPresenter<View> {

        /**
         * Get the home page selection data.
         */
        fun requestHomeData(num: Int)

        /**
         * Load more data

         */
        fun loadMoreData()


    }


}