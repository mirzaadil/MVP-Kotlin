package com.kotlin.mirzaadil.mvparchitecture.mvp.contract

import com.kotlin.mirzaadil.mvparchitecture.base.IBaseView
import com.kotlin.mirzaadil.mvparchitecture.base.IPresenter
import com.kotlin.mirzaadil.mvparchitecture.mvp.model.bean.CategoryBean

/**
 * @author Mirza Adil
 * desc: Classification contract
 */
interface CategoryContract {

    interface View : IBaseView {
        /**
         * Display classified information
         */
        fun showCategory(categoryList: ArrayList<CategoryBean>)

        /**
         * Display error message
         */
        fun showError(errorMsg:String,errorCode:Int)
    }

    interface Presenter: IPresenter<View> {
        /**
         * Get classified information
         */
        fun getCategoryData()
    }
}