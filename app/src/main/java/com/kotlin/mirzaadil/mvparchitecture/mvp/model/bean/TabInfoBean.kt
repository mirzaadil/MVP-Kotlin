package com.kotlin.mirzaadil.mvparchitecture.mvp.model.bean

/**
 * @author Mirza Adil
 * desc:  tabInfo
 */

data class TabInfoBean(val tabInfo: TabInfo) {
    data class TabInfo(val tabList: ArrayList<Tab>)

    data class Tab(val id: Long, val name: String, val apiUrl: String)
}
