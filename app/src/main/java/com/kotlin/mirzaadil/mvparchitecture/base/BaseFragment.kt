package com.kotlin.mirzaadil.mvparchitecture.base

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.annotation.NonNull
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.kotlin.mirzaadil.mvparchitecture.MyApplication
import com.kotlin.mirzaadil.mvparchitecture.veiwcustom.MultipleStatusView
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions


/**
 * @author Mirza Adil
 * Base Fragment
 */

abstract class BaseFragment : Fragment(), EasyPermissions.PermissionCallbacks {

    /**
     * Whether the view is loaded
     */

    private var isViewPrepare = false


    /**
     *Whether the data has been loaded
     */

    private var hasLoadData = false

    /**
     * Multiple View
     */

    protected var mLayoutStatusView: MultipleStatusView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutId(), null)
    }


    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            lazyLoadDataIfPrepared()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isViewPrepare = true
        initView()
        lazyLoadDataIfPrepared()

        mLayoutStatusView?.setOnClickListener(mRetryClickListener)
    }

    private fun lazyLoadDataIfPrepared() {
        if (userVisibleHint && isViewPrepare && !hasLoadData) {
            lazyLoad()
            hasLoadData = true
        }
    }

    open val mRetryClickListener: View.OnClickListener = View.OnClickListener {
        lazyLoad()
    }


    /**
     * Get Layout ID
     */
    @LayoutRes
    abstract fun getLayoutId(): Int

    /**
     * Initialization View
     */
    abstract fun initView()

    /**
     * Lazy loading
     */
    abstract fun lazyLoad()

    override fun onDestroy() {
        super.onDestroy()

        activity?.let { MyApplication.getRefWatcher(it)?.watch(activity) }
    }


    /**
     * Activity, Fragment, onRequestPermissionsResult()，
     * EasyPermissions.onRequestPermissionsResult()
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>, @NonNull grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    /**
     *
     *
     * @param requestCode
     * @param perms
     */
    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        Log.i("EasyPermissions", "Get successful permisions$perms")
    }

    /**
     *
     *
     * @param requestCode
     * @param perms
     */
    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        //
        val sb = StringBuffer()
        for (str in perms) {
            sb.append(str)
            sb.append("\n")
        }
        sb.replace(sb.length - 2, sb.length, "")
        //
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            Toast.makeText(activity, "Denied permission" + sb + "And no longer ask", Toast.LENGTH_SHORT).show()
            AppSettingsDialog.Builder(this)
                .setRationale("This feature requires" + sb + "Permission，Otherwise it will not work properly，Whether to open the settings")
                .setPositiveButton("it is good")
                .setNegativeButton("No")
                .build()
                .show()
        }
    }
}

