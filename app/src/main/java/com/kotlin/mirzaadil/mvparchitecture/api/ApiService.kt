package com.kotlin.mirzaadil.mvparchitecture.api


import com.kotlin.mirzaadil.mvparchitecture.mvp.model.bean.AuthorInfoBean
import com.kotlin.mirzaadil.mvparchitecture.mvp.model.bean.CategoryBean
import com.kotlin.mirzaadil.mvparchitecture.mvp.model.bean.HomeBean
import com.kotlin.mirzaadil.mvparchitecture.mvp.model.bean.TabInfoBean
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

/**
 * Created by xuhao on 2017/11/16.
 * Api interface
 */

interface ApiService {

    /**
     * Home selection
     */
    @GET("v2/feed?")
    fun getFirstHomeData(@Query("num") num: Int): Observable<HomeBean>

    /**
     * Request data next page based on nextPageUrl
     */
    @GET
    fun getMoreHomeData(@Url url: String): Observable<HomeBean>

    /**
     * Get related videos based on item id
     */
    @GET("v4/video/related?")
    fun getRelatedData(@Query("id") id: Long): Observable<HomeBean.Issue>

    /**
     * Get classification
     */
    @GET("v4/categories")
    fun getCategory(): Observable<ArrayList<CategoryBean>>

    /**
     * Get the classification details list
     */
    @GET("v4/categories/videoList?")
    fun getCategoryDetailList(@Query("id") id: Long): Observable<HomeBean.Issue>

    /**
     * Get more Issues
     */
    @GET
    fun getIssueData(@Url url: String): Observable<HomeBean.Issue>

    /**
     *Get Info for all leaderboards (including title and Url)
     */
    @GET("v4/rankList")
    fun getRankList(): Observable<TabInfoBean>

    /**
     * Get search information
     */
    @GET("v1/search?&num=10&start=10")
    fun getSearchData(@Query("query") query: String): Observable<HomeBean.Issue>

    /**
     * Popular search terms
     */
    @GET("v3/queries/hot")
    fun getHotWord(): Observable<ArrayList<String>>

    /**
     * attention
     */
    @GET("v4/tabs/follow")
    fun getFollowInfo(): Observable<HomeBean.Issue>

    /**
     * author information
     */
    @GET("v4/pgcs/detail/tab?")
    fun getAuthorInfo(@Query("id") id: Long): Observable<AuthorInfoBean>


}