package com.kotlin.mirzaadil.mvparchitecture.mvp.presenter

import com.kotlin.mirzaadil.mvparchitecture.base.BasePresenter
import com.kotlin.mirzaadil.mvparchitecture.mvp.contract.HomeContract
import com.kotlin.mirzaadil.mvparchitecture.mvp.model.HomeModel
import com.kotlin.mirzaadil.mvparchitecture.mvp.model.bean.HomeBean
import com.kotlin.mirzaadil.mvparchitecture.net.exception.ExceptionHandle

/**
 * Created by Mirza Adil on 2018/12/18.
 * Home featured Presenter
 * (Data is a combination of Banner data and a page of data, HomeBean, view the interface and then understand it in the analysis)
 */

class HomePresenter : BasePresenter<HomeContract.View>(), HomeContract.Presenter {


    private var bannerHomeBean: HomeBean? = null

    private var nextPageUrl: String? =
        null     //After loading the Banner data of the home page + one page of data, the nextPageUrl is not added.

    private val homeModel: HomeModel by lazy {

        HomeModel()
    }

    /**
     * Get the home page selection data banner plus one page of data
     */
    override fun requestHomeData(num: Int) {
        // Check if the view is bound
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = homeModel.requestHomeData(num)
            .flatMap({ homeBean ->
                //Filter out Banner2 (including ads, etc.), and view the interface analysis.
                val bannerItemList = homeBean.issueList[0].itemList
                bannerItemList.filter { item ->
                    item.type == "banner2" || item.type == "horizontalScrollCard"
                }.forEach { item ->
                    //Remove item
                    bannerItemList.remove(item)
                }
                bannerHomeBean = homeBean //Record the first page as the banner data
                //Request next page data according to nextPageUrl
                homeModel.loadMoreData(homeBean.nextPageUrl)
            })
            .subscribe({ homeBean ->
                mRootView?.apply {
                    dismissLoading()

                    nextPageUrl = homeBean.nextPageUrl
                    //Filter out Banner2 (including ads, etc.), and view the interface analysis.
                    val newBannerItemList = homeBean.issueList[0].itemList

                    newBannerItemList.filter { item ->
                        item.type == "banner2" || item.type == "horizontalScrollCard"
                    }.forEach { item ->
                        //Remove item
                        newBannerItemList.remove(item)
                    }
                    // Reassign Banner length
                    bannerHomeBean!!.issueList[0].count = bannerHomeBean!!.issueList[0].itemList.size

                    //Assign filtered data + banner data
                    bannerHomeBean?.issueList!![0].itemList.addAll(newBannerItemList)

                    setHomeData(bannerHomeBean!!)

                }

            }, { t ->
                mRootView?.apply {
                    dismissLoading()
                    showError(ExceptionHandle.handleException(t), ExceptionHandle.errorCode)
                }
            })

        addSubscription(disposable)

    }

    /**
     * load more
     */

    override fun loadMoreData() {
        val disposable = nextPageUrl?.let {
            homeModel.loadMoreData(it)
                .subscribe({ homeBean ->
                    mRootView?.apply {
                        //Filter out Banner2 (including ads, etc.), and view the interface analysis.
                        val newItemList = homeBean.issueList[0].itemList

                        newItemList.filter { item ->
                            item.type == "banner2" || item.type == "horizontalScrollCard"
                        }.forEach { item ->
                            //Remove item
                            newItemList.remove(item)
                        }

                        nextPageUrl = homeBean.nextPageUrl
                        setMoreData(newItemList)
                    }

                }, { t ->
                    mRootView?.apply {
                        showError(ExceptionHandle.handleException(t), ExceptionHandle.errorCode)
                    }
                })


        }
        if (disposable != null) {
            addSubscription(disposable)
        }
    }
}