package com.kotlin.mirzaadil.mvparchitecture.mvp.contract

import com.kotlin.mirzaadil.mvparchitecture.base.IBaseView
import com.kotlin.mirzaadil.mvparchitecture.base.IPresenter
import com.kotlin.mirzaadil.mvparchitecture.mvp.model.bean.HomeBean


/**
 * @author Mirza Adil
 * desc: Search contract class

 */
interface SearchContract {

    interface View : IBaseView {
        /**
         * Set popular keyword data
         */
        fun setHotWordData(string: ArrayList<String>)

        /**
         * Set the results returned by the search keyword
         */
        fun setSearchResult(issue: HomeBean.Issue)

        /**
         * Close the software disk
         */
        fun closeSoftKeyboard()

        /**
         * Set empty view
         */
        fun setEmptyView()


        fun showError(errorMsg: String, errorCode: Int)
    }


    interface Presenter : IPresenter<View> {
        /**
         * Get data for popular keywords
         */
        fun requestHotWordData()

        /**
         * Query search
         */
        fun querySearchData(words: String)

        /**
         * load more
         */
        fun loadMoreData()
    }
}