package com.kotlin.mirzaadil.mvparchitecture.mvp.model


import com.kotlin.mirzaadil.mvparchitecture.mvp.model.bean.TabInfoBean
import com.kotlin.mirzaadil.mvparchitecture.net.RetrofitManager
import com.kotlin.mirzaadil.mvparchitecture.rx.scheduler.SchedulerUtils
import io.reactivex.Observable

/**
 * @author Mirza Adil
 * desc: Tab Model
 */
class HotTabModel {

    /**
     * TabInfo
     */
    fun getTabInfo(): Observable<TabInfoBean> {

        return RetrofitManager.service.getRankList()
                .compose(SchedulerUtils.ioToMain())
    }

}
