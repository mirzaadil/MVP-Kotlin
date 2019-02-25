package com.kotlin.mirzaadil.mvparchitecture.mvp.model


import com.kotlin.mirzaadil.mvparchitecture.mvp.model.bean.HomeBean
import com.kotlin.mirzaadil.mvparchitecture.net.RetrofitManager
import com.kotlin.mirzaadil.mvparchitecture.rx.scheduler.SchedulerUtils
import io.reactivex.Observable

/**
 * @author Mirza adil
 * desc: Rank Model
 */
class RankModel {

    /**
     * Get the leaderboard
     */
    fun requestRankList(apiUrl:String): Observable<HomeBean.Issue> {

        return RetrofitManager.service.getIssueData(apiUrl)
                .compose(SchedulerUtils.ioToMain())
    }

}
