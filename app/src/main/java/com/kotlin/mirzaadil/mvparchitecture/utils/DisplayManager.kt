package com.kotlin.mirzaadil.mvparchitecture.utils

import android.content.Context
import android.util.DisplayMetrics

/**
 * @author Mirza Adil
 * desc:
 */

object DisplayManager {
    init {

    }

    private var displayMetrics: DisplayMetrics? = null

    private var screenWidth: Int? = null

    private var screenHeight: Int? = null

    private var screenDpi: Int? = null

    fun init(context: Context) {
        displayMetrics = context.resources.displayMetrics
        screenWidth = displayMetrics?.widthPixels
        screenHeight = displayMetrics?.heightPixels
        screenDpi = displayMetrics?.densityDpi
    }


    //UI map size
    private const val STANDARD_WIDTH = 1080
    private const val STANDARD_HEIGHT = 1920


    fun getScreenWidth(): Int? {
        return screenWidth
    }

    fun getScreenHeight(): Int? {
        return screenHeight
    }


    /**
     * The height of the problem passed into the UI map, unit pixels
     * @param size
     * @return
     */
    fun getPaintSize(size: Int): Int? {
        return getRealHeight(size)
    }

    /**
     * Enter the size of the UI map and output the actual px
     *
     * @param px Size in ui diagram
     * @return
     */
    fun getRealWidth(px: Int): Int? {

        return getRealWidth(px, STANDARD_WIDTH.toFloat())
    }

    /**
     * Enter the size of the UI map, output the actual px, the second parameter is the parent layout
     *
     * @param px
     * @param parentWidth
     * @return
     */
    fun getRealWidth(px: Int, parentWidth: Float): Int? {
        return (px / parentWidth * getScreenWidth()!!).toInt()
    }

    /**
     * Enter the size of the UI map and output the actual px
     *
     * @param px
     * @return
     */
    fun getRealHeight(px: Int): Int? {

        return getRealHeight(px, STANDARD_HEIGHT.toFloat())
    }

    /**
     * Enter the size of the UI map, output the actual px, the second parameter is the parent layout
     *
     * @param px
     * @param parentHeight
     * @return
     */
    fun getRealHeight(px: Int, parentHeight: Float): Int? {
        return (px / parentHeight * getScreenHeight()!!).toInt()
    }

    /**
     * dip px
     * @param dipValue
     * @return int
     */
    fun dip2px(dipValue: Float): Int? {
        val scale = displayMetrics?.density
        return (dipValue * scale!! + 0.5f).toInt()
    }
}