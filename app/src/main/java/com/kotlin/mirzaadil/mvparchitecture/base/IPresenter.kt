package com.kotlin.mirzaadil.mvparchitecture.base



/**
 * @author Mirza Adil
 * created: 2018/12/12
 * desc: Presenter CallBack
 */


interface IPresenter<in V: IBaseView> {

    fun attachView(mRootView: V)

    fun detachView()

}
