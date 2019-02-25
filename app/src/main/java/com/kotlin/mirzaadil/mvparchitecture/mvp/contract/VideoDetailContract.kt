package com.kotlin.mirzaadil.mvparchitecture.mvp.contract

import com.kotlin.mirzaadil.mvparchitecture.base.IBaseView
import com.kotlin.mirzaadil.mvparchitecture.base.IPresenter
import com.kotlin.mirzaadil.mvparchitecture.mvp.model.bean.HomeBean


/**
 * @author Mirza Adil
 * desc: Video details contract class
 */
interface VideoDetailContract {

    interface View : IBaseView {

        /**
         * Set the video source
         */
        fun setVideo(url: String)

        /**
         * Set up video information
         */
        fun setVideoInfo(itemInfo: HomeBean.Issue.Item)

        /**
         * Set background
         */
        fun setBackground(url: String)

        /**
         * Set up the latest related videos
         */
        fun setRecentRelatedVideo(itemList: ArrayList<HomeBean.Issue.Item>)

        /**
         * Set error message
         */
        fun setErrorMsg(errorMsg: String)


    }

    interface Presenter : IPresenter<View> {

        /**
         * Load video information
         */
        fun loadVideoInfo(itemInfo: HomeBean.Issue.Item)

        /**
         * Request related video data
         */
        fun requestRelatedVideo(id: Long)

    }


}