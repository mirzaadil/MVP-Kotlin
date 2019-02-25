package com.kotlin.mirzaadil.mvparchitecture.mvp.model


import com.kotlin.mirzaadil.mvparchitecture.mvp.model.bean.HomeBean
import com.kotlin.mirzaadil.mvparchitecture.net.RetrofitManager
import com.kotlin.mirzaadil.mvparchitecture.rx.scheduler.SchedulerUtils
import io.reactivex.Observable

/**
 * @author Mirza Adil
 * desc: Category Detail Model
 */
class CategoryDetailModel {

    /**
     * Get List data under category
     */
    fun getCategoryDetailList(id: Long): Observable<HomeBean.Issue> {
        return RetrofitManager.service.getCategoryDetailList(id)
            .compose(SchedulerUtils.ioToMain())
    }

    /**
     * Load more data
     */
    fun loadMoreData(url: String): Observable<HomeBean.Issue> {
        return RetrofitManager.service.getIssueData(url)
            .compose(SchedulerUtils.ioToMain())
    }
}