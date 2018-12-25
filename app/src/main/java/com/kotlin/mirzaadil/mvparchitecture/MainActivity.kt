package com.kotlin.mirzaadil.mvparchitecture

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import android.view.KeyEvent
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.kotlin.mirzaadil.mvparchitecture.base.BaseActivity
import com.kotlin.mirzaadil.mvparchitecture.mvp.model.bean.TabEntity
import com.kotlin.mirzaadil.mvparchitecture.ui.fragment.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

/**
 * @author Mirza Adil
 * created: 2018/12/12
 * desc:BaseActivity
 */

class MainActivity : BaseActivity() {

    private val mTitles = arrayOf("Daily selection", "Find", "Popular", "mine")

    // Un Selected Icon
    private val mIconUnSelectIds = intArrayOf(
        R.mipmap.ic_home_normal,
        R.mipmap.ic_discovery_normal,
        R.mipmap.ic_hot_normal,
        R.mipmap.ic_mine_normal
    )
    // Selected Icon
    private val mIconSelectIds = intArrayOf(
        R.mipmap.ic_home_selected,
        R.mipmap.ic_discovery_selected,
        R.mipmap.ic_hot_selected,
        R.mipmap.ic_mine_selected
    )

    private val mTabEntities = ArrayList<CustomTabEntity>()
    private var mHomeFragment: HomeFragment? = null


    private var mIndex = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            mIndex = savedInstanceState.getInt("currTabIndex")
        }
        super.onCreate(savedInstanceState)
        initTab()
        tab_layout.currentTab = mIndex
        switchFragment(mIndex)
    }

    override fun layoutId(): Int {
        return R.layout.activity_main
    }

    override fun initData() {

    }

    override fun initView() {

    }

    override fun start() {

    }

    //Initialize the bottom menu
    private fun initTab() {
        (0 until mTitles.size)
            .mapTo(mTabEntities) { TabEntity(mTitles[it], mIconSelectIds[it], mIconUnSelectIds[it]) }
        // Assign a value to Tab
        tab_layout.setTabData(mTabEntities)
        tab_layout.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {
                //Switch Fragment
                switchFragment(position)
            }

            override fun onTabReselect(position: Int) {

            }
        })
    }


    /**
     * Switch Fragment
     * @param position Subscript
     */
    private fun switchFragment(position: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        hideFragments(transaction)
        when (position) {
            0 // Home
            -> mHomeFragment?.let {
                transaction.show(it)
            } ?: HomeFragment.getInstance(mTitles[position]).let {
                mHomeFragment = it
                transaction.add(R.id.fl_container, it, "home")
            }
//            1  //Find
//            -> mDiscoveryFragment?.let {
//                transaction.show(it)
//            } ?: DiscoveryFragment.getInstance(mTitles[position]).let {
//                mDiscoveryFragment = it
//                transaction.add(R.id.fl_container, it, "discovery")
//            }
//            2  //Popular
//            -> mHotFragment?.let {
//                transaction.show(it)
//            } ?: HotFragment.getInstance(mTitles[position]).let {
//                mHotFragment = it
//                transaction.add(R.id.fl_container, it, "hot")
//            }
//            3 //mine
//            -> mMineFragment?.let {
//                transaction.show(it)
//            } ?: MineFragment.getInstance(mTitles[position]).let {
//                mMineFragment = it
//                transaction.add(R.id.fl_container, it, "mine")
//            }
//
//            else -> {
//
//            }
        }

        mIndex = position
        tab_layout.currentTab = mIndex
        transaction.commitAllowingStateLoss()
    }

    /**
     * Hide all Fragment
     * @param transaction transaction
     */
    private fun hideFragments(transaction: FragmentTransaction) {
//        mHomeFragment?.let { transaction.hide(it) }
//        mDiscoveryFragment?.let { transaction.hide(it) }
//        mHotFragment?.let { transaction.hide(it) }
//        mMineFragment?.let { transaction.hide(it) }
    }


    @SuppressLint("MissingSuperCall")
    override fun onSaveInstanceState(outState: Bundle) {
//        showToast("onSaveInstanceState->"+mIndex)
//        super.onSaveInstanceState(outState)
        // Record the location of the fragment to prevent the crash. When the activity is recycled by the system, the fragment is disordered.
        if (tab_layout != null) {
            outState.putInt("currTabIndex", mIndex)
        }
    }

    private var mExitTime: Long = 0

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis().minus(mExitTime) <= 2000) {
                finish()
            } else {
                mExitTime = System.currentTimeMillis()
                showToast("Application Closed")
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}
