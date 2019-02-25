package com.kotlin.mirzaadil.mvparchitecture.mvp.presenter

import com.kotlin.mirzaadil.mvparchitecture.base.BasePresenter
import com.kotlin.mirzaadil.mvparchitecture.mvp.contract.SearchContract
import com.kotlin.mirzaadil.mvparchitecture.mvp.model.SearchModel
import com.kotlin.mirzaadil.mvparchitecture.net.exception.ExceptionHandle


/**
 * @author Mirza Adil
 * desc: Search Presenter
 */
class SearchPresenter : BasePresenter<SearchContract.View>(), SearchContract.Presenter {

    private var nextPageUrl: String? = null

    private val searchModel by lazy { SearchModel() }


    /**
     * Get popular keywords
     */
    override fun requestHotWordData() {
        checkViewAttached()
        checkViewAttached()
        mRootView?.apply {
            closeSoftKeyboard()
            showLoading()
        }
        addSubscription(disposable = searchModel.requestHotWordData()
                .subscribe({ string ->
                    mRootView?.apply {
                        setHotWordData(string)
                    }
                }, { throwable ->
                    mRootView?.apply {
                        //Exception handling
                        showError(ExceptionHandle.handleException(throwable),ExceptionHandle.errorCode)
                    }
                }))
    }

    /**
     * Query keyword
     */
    override fun querySearchData(words: String) {
        checkViewAttached()
        mRootView?.apply {
            closeSoftKeyboard()
            showLoading()
        }
        addSubscription(disposable = searchModel.getSearchResult(words)
                .subscribe({ issue ->
                    mRootView?.apply {
                        dismissLoading()
                        if (issue.count > 0 && issue.itemList.size > 0) {
                            nextPageUrl = issue.nextPageUrl
                            setSearchResult(issue)
                        } else
                            setEmptyView()
                    }
                }, { throwable ->
                    mRootView?.apply {
                        dismissLoading()
                        //Exception handling
                        showError(ExceptionHandle.handleException(throwable),ExceptionHandle.errorCode)
                    }
                })
        )

    }

    /**
     * Load more data
     */
    override fun loadMoreData() {
        checkViewAttached()
        nextPageUrl?.let {
            addSubscription(disposable = searchModel.loadMoreData(it)
                    .subscribe({ issue ->
                        mRootView?.apply {
                            nextPageUrl = issue.nextPageUrl
                            setSearchResult(issue)
                        }
                    }, { throwable ->
                        mRootView?.apply {
                            //Exception handling
                            showError(ExceptionHandle.handleException(throwable),ExceptionHandle.errorCode)
                        }
                    }))
        }

    }


}