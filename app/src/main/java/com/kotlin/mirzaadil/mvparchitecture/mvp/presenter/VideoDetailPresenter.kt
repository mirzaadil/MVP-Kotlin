package com.kotlin.mirzaadil.mvparchitecture.mvp.presenter

import android.app.Activity
import com.kotlin.mirzaadil.mvparchitecture.MyApplication
import com.kotlin.mirzaadil.mvparchitecture.base.BasePresenter
import com.kotlin.mirzaadil.mvparchitecture.dataFormat
import com.kotlin.mirzaadil.mvparchitecture.mvp.contract.VideoDetailContract
import com.kotlin.mirzaadil.mvparchitecture.mvp.model.VideoDetailModel
import com.kotlin.mirzaadil.mvparchitecture.mvp.model.bean.HomeBean
import com.kotlin.mirzaadil.mvparchitecture.net.exception.ExceptionHandle
import com.kotlin.mirzaadil.mvparchitecture.showToast
import com.kotlin.mirzaadil.mvparchitecture.utils.DisplayManager
import com.kotlin.mirzaadil.mvparchitecture.utils.NetworkUtil

/**
 * @author Mirza Adil
 * desc:
 */
class VideoDetailPresenter : BasePresenter<VideoDetailContract.View>(), VideoDetailContract.Presenter {

    private val videoDetailModel: VideoDetailModel by lazy {

        VideoDetailModel()
    }

    /**
     * Load video related data
     */
    override fun loadVideoInfo(itemInfo: HomeBean.Issue.Item) {

        val playInfo = itemInfo.data?.playInfo

        val netType = NetworkUtil.isWifi(MyApplication.context)
        // Check if binding View
        checkViewAttached()
        if (playInfo!!.size > 1) {
            // The current network is a high-definition video in the Wifi environment.
            if (netType) {
                for (i in playInfo) {
                    if (i.type == "high") {
                        val playUrl = i.url
                        mRootView?.setVideo(playUrl)
                        break
                    }
                }
            } else {
                //Otherwise, choose the standard video.
                for (i in playInfo) {
                    if (i.type == "normal") {
                        val playUrl = i.url
                        mRootView?.setVideo(playUrl)
                        //Todo To be perfected
                        (mRootView as Activity).showToast(
                            "This consumption${(mRootView as Activity)
                                .dataFormat(i.urlList[0].size)}flow"
                        )
                        break
                    }
                }
            }
        } else {
            mRootView?.setVideo(itemInfo.data.playUrl)
        }

        //Set background
        val backgroundUrl =
            itemInfo.data.cover.blurred + "/thumbnail/${DisplayManager.getScreenHeight()!! - DisplayManager.dip2px(250f)!!}x${DisplayManager.getScreenWidth()}"
        backgroundUrl.let { mRootView?.setBackground(it) }

        mRootView?.setVideoInfo(itemInfo)


    }


    /**
     * Request related video data
     */
    override fun requestRelatedVideo(id: Long) {
        mRootView?.showLoading()
        val disposable = videoDetailModel.requestRelatedData(id)
            .subscribe({ issue ->
                mRootView?.apply {
                    dismissLoading()
                    setRecentRelatedVideo(issue.itemList)
                }
            }, { t ->
                mRootView?.apply {
                    dismissLoading()
                    setErrorMsg(ExceptionHandle.handleException(t))
                }
            })

        addSubscription(disposable)

    }


}