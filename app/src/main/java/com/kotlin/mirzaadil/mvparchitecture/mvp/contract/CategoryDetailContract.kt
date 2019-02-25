package com.kotlin.mirzaadil.mvparchitecture.mvp.contract

import com.kotlin.mirzaadil.mvparchitecture.base.IBaseView
import com.kotlin.mirzaadil.mvparchitecture.base.IPresenter
import com.kotlin.mirzaadil.mvparchitecture.mvp.model.bean.HomeBean


/**
 * @author Mirza Adil
 * desc: Classification details contract class
 */
interface CategoryDetailContract {

    interface View: IBaseView {
        /**
         *  Set list data
         */
        fun setCateDetailList(itemList:ArrayList<HomeBean.Issue.Item>)

        fun showError(errorMsg:String)




    }

    interface Presenter: IPresenter<View> {

        fun getCategoryDetailList(id:Long)

        fun loadMoreData()
    }
}