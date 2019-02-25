package com.kotlin.mirzaadil.mvparchitecture.mvp.contract

import com.kotlin.mirzaadil.mvparchitecture.base.IBaseView
import com.kotlin.mirzaadil.mvparchitecture.base.IPresenter
import com.kotlin.mirzaadil.mvparchitecture.mvp.model.bean.HomeBean


/**
 * Created by xuhao on 2017/11/30.
 * desc: Rank Contract Class
 */
interface RankContract {

    interface View: IBaseView {
        /**
         * Set data for leaderboards
         */
        fun setRankList(itemList: ArrayList<HomeBean.Issue.Item>)

        fun showError(errorMsg:String,errorCode:Int)
    }


    interface Presenter: IPresenter<View> {
        /**
         * Get TabInfo
         */
        fun requestRankList(apiUrl:String)
    }
}