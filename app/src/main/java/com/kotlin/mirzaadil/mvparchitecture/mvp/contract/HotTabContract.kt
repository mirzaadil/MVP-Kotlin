package com.kotlin.mirzaadil.mvparchitecture.mvp.contract

import com.kotlin.mirzaadil.mvparchitecture.base.IBaseView
import com.kotlin.mirzaadil.mvparchitecture.base.IPresenter
import com.kotlin.mirzaadil.mvparchitecture.mvp.model.bean.TabInfoBean


/**
 * @author Mirza Adil
 * desc: Contract class
 */
interface HotTabContract {

    interface View : IBaseView {
        /**
         * Set TabInfo
         */
        fun setTabInfo(tabInfoBean: TabInfoBean)

        fun showError(errorMsg: String, errorCode: Int)
    }


    interface Presenter : IPresenter<View> {
        /**
         * get TabInfo
         */
        fun getTabInfo()
    }
}