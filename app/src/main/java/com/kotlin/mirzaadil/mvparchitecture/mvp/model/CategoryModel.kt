package com.kotlin.mirzaadil.mvparchitecture.mvp.model


import com.kotlin.mirzaadil.mvparchitecture.mvp.model.bean.CategoryBean
import com.kotlin.mirzaadil.mvparchitecture.net.RetrofitManager
import com.kotlin.mirzaadil.mvparchitecture.rx.scheduler.SchedulerUtils
import io.reactivex.Observable

/**
 * @author Mirza Adil
 * desc: Categorical data model
 */
class CategoryModel {


    /**
     * Get classified information
     */
    fun getCategoryData(): Observable<ArrayList<CategoryBean>> {
        return RetrofitManager.service.getCategory()
                .compose(SchedulerUtils.ioToMain())
    }
}