package com.kotlin.mirzaadil.mvparchitecture.mvp.model


import com.kotlin.mirzaadil.mvparchitecture.mvp.model.bean.HomeBean
import com.kotlin.mirzaadil.mvparchitecture.net.RetrofitManager
import com.kotlin.mirzaadil.mvparchitecture.rx.scheduler.SchedulerUtils
import io.reactivex.Observable

/**
 * 2@author Mirza Adil
 * desc: Home model
 */

class HomeModel{

    /**
     * Get the homepage banner data.
     */
    fun requestHomeData(num:Int):Observable<HomeBean>{
        return RetrofitManager.service.getFirstHomeData(num)
                .compose(SchedulerUtils.ioToMain())
    }

    /**
     * load more
     */
    fun loadMoreData(url:String):Observable<HomeBean>{

        return RetrofitManager.service.getMoreHomeData(url)
                .compose(SchedulerUtils.ioToMain())
    }



}
