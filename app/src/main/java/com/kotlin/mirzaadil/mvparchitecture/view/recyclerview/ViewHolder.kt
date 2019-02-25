package com.kotlin.mirzaadil.mvparchitecture.view.recyclerview

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

@Suppress("UNCHECKED_CAST")
/**
 * @author Mirza Adil
 * desc:
 */

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    //Used to cache the interface that has been found
    private var mView: SparseArray<View>?=null

    init {
        mView = SparseArray()
    }

    fun <T : View> getView(viewId: Int): T {
        //Caching existing views
        var view: View? = mView?.get(viewId)
        //Use the cache method to reduce the number of findViewById
        if (view == null) {
            view = itemView.findViewById(viewId)
            mView?.put(viewId, view)
        }
        return view as T
    }


    fun <T : ViewGroup> getViewGroup(viewId: Int): T {
        //Caching existing views
        var view: View? = mView?.get(viewId)
        //Use the cache method to reduce the number of findViewById
        if (view == null) {
            view = itemView.findViewById(viewId)
            mView?.put(viewId, view)
        }
        return view as T
    }

    @SuppressLint("SetTextI18n")
    //Universal functions for packaging Set text Set item click event Set picture
    fun setText(viewId: Int, text: CharSequence): ViewHolder {
        val view = getView<TextView>(viewId)
        view.text = "" + text
        //Hope can be chained
        return this
    }

    fun setHintText(viewId: Int, text: CharSequence): ViewHolder {
        val view = getView<TextView>(viewId)
        view.hint = "" + text
        return this
    }

    /**
     * Set local image
     *
     * @param viewId
     * @param resId
     * @return
     */
    fun setImageResource(viewId: Int, resId: Int): ViewHolder {
        val iv = getView<ImageView>(viewId)
        iv.setImageResource(resId)
        return this
    }

    /**
     * Load image resource path

     *
     * @param viewId
     * @param imageLoader
     * @return
     */
    fun setImagePath(viewId: Int, imageLoader: HolderImageLoader): ViewHolder {
        val iv = getView<ImageView>(viewId)
        imageLoader.loadImage(iv, imageLoader.path)
        return this
    }

    abstract class HolderImageLoader(val path: String) {

        /**
         * Need to rewrite this method to load the image
         *
         * @param iv
         * @param path
         */
        abstract fun loadImage(iv: ImageView, path: String)
    }

    /**
     * Set View's Visibility
     */
    fun setViewVisibility(viewId: Int, visibility: Int): ViewHolder {
        getView<View>(viewId).visibility = visibility
        return this
    }

    /**
     * Set item click event
     */
    fun setOnItemClickListener(listener: View.OnClickListener) {
        itemView.setOnClickListener(listener)
    }

    /**
     * Set item long press event
     */
    fun setOnItemLongClickListener(listener: View.OnLongClickListener) {
        itemView.setOnLongClickListener(listener)
    }

}