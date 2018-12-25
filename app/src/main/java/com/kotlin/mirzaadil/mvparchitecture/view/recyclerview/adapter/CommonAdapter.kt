package com.kotlin.mirzaadil.mvparchitecture.view.recyclerview.adapter


import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.kotlin.mirzaadil.mvparchitecture.view.recyclerview.MultipleType
import com.kotlin.mirzaadil.mvparchitecture.view.recyclerview.ViewHolder

/**
 * Created by Mirza Adil on 2018/12/18.
 * desc: Generic Common Adapter
 */

abstract class CommonAdapter<T>(
    var mContext: Context, var mData: ArrayList<T>, //Item layout
    private var mLayoutId: Int
) : RecyclerView.Adapter<ViewHolder>() {
    protected var mInflater: LayoutInflater? = null
    private var mTypeSupport: MultipleType<T>? = null

    //Use interface callback click event
    private var mItemClickListener: OnItemClickListener? = null

    //Use interface callback click event
    private var mItemLongClickListener: OnItemLongClickListener? = null

    init {
        mInflater = LayoutInflater.from(mContext)
    }

    //Need multiple layouts
    constructor(context: Context, data: ArrayList<T>, typeSupport: MultipleType<T>) : this(context, data, -1) {
        this.mTypeSupport = typeSupport
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (mTypeSupport != null) {
            //Need multiple layouts
            mLayoutId = viewType
        }
        //Create view
        val view = mInflater?.inflate(mLayoutId, parent, false)
        return ViewHolder(view!!)
    }

    override fun getItemViewType(position: Int): Int {
        //Multiple layout issues
        return mTypeSupport?.getLayoutId(mData[position], position) ?: super.getItemViewType(position)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Binding data
        bindData(holder, mData[position], position)

//        if (mItemClickListener != null) {
//            holder.itemView.setOnClickListener { mItemClickListener!!.onItemClick(mData[position], position) }
//        }
//        //长按点击事件
//        if (mItemLongClickListener != null) {
//            holder.itemView.setOnLongClickListener { mItemLongClickListener!!.onItemLongClick(mData[position], position) }
//        }
        //Entry click event
        mItemClickListener?.let {
            holder.itemView.setOnClickListener { mItemClickListener!!.onItemClick(mData[position], position) }
        }

        //Long press on click event
        mItemLongClickListener?.let {
            holder.itemView.setOnLongClickListener {
                mItemLongClickListener!!.onItemLongClick(
                    mData[position],
                    position
                )
            }
        }
    }

    /**
     * Pass the necessary parameters
     *
     * @param holder
     * @param data
     * @param position
     */
    protected abstract fun bindData(holder: ViewHolder, data: T, position: Int)

    override fun getItemCount(): Int {
        return mData.size
    }

    fun setOnItemClickListener(itemClickListener: OnItemClickListener) {
        this.mItemClickListener = itemClickListener
    }

    fun setOnItemLongClickListener(itemLongClickListener: OnItemLongClickListener) {
        this.mItemLongClickListener = itemLongClickListener
    }
}
