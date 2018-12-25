package com.kotlin.mirzaadil.mvparchitecture.base

import android.content.Context
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import com.kotlin.mirzaadil.mvparchitecture.MyApplication
import com.kotlin.mirzaadil.mvparchitecture.veiwcustom.MultipleStatusView
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions


/**
 * @author Mirza Adil
 * Base Activity
 */


abstract class BaseActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {


    protected var mLayoutStatusView: MultipleStatusView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId())

        initData()
        initView()
        start()
        initListener()
    }


    /**
     * Multiple Status Listener
     */

    private fun initListener() {
        mLayoutStatusView?.setOnClickListener(mRetryClickListener)
    }

    open val mRetryClickListener: View.OnClickListener = View.OnClickListener {
        start()
    }

    /**
     * Load layout
     */

    abstract fun layoutId(): Int

    /**
     * Initialization Data
     */
    abstract fun initData()


    /**
     *  Initialization View
     */

    abstract fun initView()

    /**
     * Start Activity
     */

    abstract fun start()

    /**
     * Punch soft keyboard
     */

    fun openKeyBoard(mEditText: EditText, mContext: Context) {
        val imm = mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN)
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    /**
     * Close keyboard
     */

    fun closeKeyboard(mEditText: EditText, mContext: Context) {
        val imm = mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(mEditText.windowToken, 0)
    }

    override fun onDestroy() {
        super.onDestroy()
        MyApplication.getRefWatcher(this)?.watch(this)
    }


    /**
     * Activity, Fragment, onRequestPermissionsResult()，
     * EasyPermissions.onRequestPermissionsResult().
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
     * Android Permission Granted.
     *
     * @param requestCode
     * @param perms
     */
    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        Log.i("EasyPermissions", "Get successful permisions$perms")
    }

    /**
     * Callback executed when the permission request fails
     *
     * @param requestCode
     * @param perms
     */
    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {

        val sb = StringBuffer()
        for (str in perms) {
            sb.append(str)
            sb.append("\n")
        }
        sb.replace(sb.length - 2, sb.length, "")
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            Toast.makeText(this, "Denied permission" + sb + "And no longer ask", Toast.LENGTH_SHORT).show()
            AppSettingsDialog.Builder(this)
                .setRationale("This feature requires" + sb + "Permission，Otherwise it will not work properly，Whether to open the settings")
                .setPositiveButton("it is good")
                .setNegativeButton("No")
                .build()
                .show()
        }
    }

}





