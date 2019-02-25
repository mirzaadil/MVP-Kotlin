package com.kotlin.mirzaadil.mvparchitecture.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.orhanobut.logger.Logger

import java.lang.reflect.Field

/**
 * @author Mirza Adil
 * desc:
 */

object CleanLeakUtils {

    fun fixInputMethodManagerLeak(destContext: Context?) {
        if (destContext == null) {
            return
        }
        val inputMethodManager = destContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        val viewArray = arrayOf("mCurRootView", "mServedView", "mNextServedView")
        var filed: Field
        var filedObject: Any?

        for (view in viewArray) {
            try {
                filed = inputMethodManager.javaClass.getDeclaredField(view)
                if (!filed.isAccessible) {
                    filed.isAccessible = true
                }
                filedObject = filed.get(inputMethodManager)
                if (filedObject != null && filedObject is View) {
                    val fileView = filedObject as View?
                    if (fileView!!.context === destContext) { // The context referenced by the InputMethodManager is intended to be destroyed by the target.
                        filed.set(inputMethodManager, null) //Empty, destroy path to gc node

                    } else {
                        break// If you don't want the target to be destroyed, you have to enter another layer of interface. Don't deal with it, avoid affecting the original logic, and you don't have to continue for loop.
                    }
                }
            } catch (t: Throwable) {
                t.printStackTrace()
            }

        }
    }
}
