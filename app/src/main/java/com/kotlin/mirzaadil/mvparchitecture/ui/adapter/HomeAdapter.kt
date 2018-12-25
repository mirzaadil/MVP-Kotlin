package com.kotlin.mirzaadil.mvparchitecture.ui.adapter

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import cn.bingoogolapple.bgabanner.BGABanner
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.kotlin.mirzaadil.mvparchitecture.R
import com.kotlin.mirzaadil.mvparchitecture.durationFormat
import com.kotlin.mirzaadil.mvparchitecture.glide.GlideApp
import com.kotlin.mirzaadil.mvparchitecture.mvp.model.bean.HomeBean
import com.kotlin.mirzaadil.mvparchitecture.view.recyclerview.ViewHolder
import com.kotlin.mirzaadil.mvparchitecture.view.recyclerview.adapter.CommonAdapter
import io.reactivex.Observable


/**
 * Created by Mirza Adil on 2018/12/18.
 * desc: Home Adapter
 */

class HomeAdapter(context: Context, data: ArrayList<HomeBean.Issue.Item>) :
    CommonAdapter<HomeBean.Issue.Item>(context, data, -1) {


    // Banner as the first item in RecyclerView
    var bannerItemSize = 0

    companion object {

        private const val ITEM_TYPE_BANNER = 1    //Banner type
        private const val ITEM_TYPE_TEXT_HEADER = 2   //textHeader
        private const val ITEM_TYPE_CONTENT = 3    //item
    }

    /**
     * Set the banner size
     */
    fun setBannerSize(count: Int) {
        bannerItemSize = count
    }

    /**
     * Add more data
     */
    fun addItemData(itemList: ArrayList<HomeBean.Issue.Item>) {
        this.mData.addAll(itemList)
        notifyDataSetChanged()
    }


    /**
     * Get the type of Item
     */
    override fun getItemViewType(position: Int): Int {
        return when {
            position == 0 ->
                ITEM_TYPE_BANNER
            mData[position + bannerItemSize - 1].type == "textHeader" ->
                ITEM_TYPE_TEXT_HEADER
            else ->
                ITEM_TYPE_CONTENT
        }
    }


    /**
     *      Get the number of RecyclerView Items (Banner as an item)
     */
    override fun getItemCount(): Int {
        return when {
            mData.size > bannerItemSize -> mData.size - bannerItemSize + 1
            mData.isEmpty() -> 0
            else -> 1
        }
    }


    /**
     * Binding layout
     */
    override fun bindData(holder: ViewHolder, data: HomeBean.Issue.Item, position: Int) {
        when (getItemViewType(position)) {
            //Banner
            ITEM_TYPE_BANNER -> {
                val bannerItemData: ArrayList<HomeBean.Issue.Item> =
                    mData.take(bannerItemSize).toCollection(ArrayList())
                val bannerFeedList = ArrayList<String>()
                val bannerTitleList = ArrayList<String>()
                //Remove the img and title displayed by the banner
                Observable.fromIterable(bannerItemData)
                    .subscribe { list ->
                        bannerFeedList.add(list.data?.cover?.feed ?: "")
                        bannerTitleList.add(list.data?.title ?: "")
                    }

                //Set banner
                with(holder) {
                    getView<BGABanner>(R.id.banner).run {
                        setAutoPlayAble(bannerFeedList.size > 1)
                        setData(bannerFeedList, bannerTitleList)
                        setAdapter { banner, _, feedImageUrl, position ->
                            GlideApp.with(mContext)
                                .load(feedImageUrl)
                                .transition(com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions().crossFade())
                                .placeholder(R.drawable.placeholder_banner)
                                .into(banner.getItemImageView(position))


                        }

                    }
                }
                //Unused parameters are replaced with "_" in kotlin
                holder.getView<BGABanner>(R.id.banner).setDelegate { _, imageView, _, i ->

                    goToVideoPlayer(mContext as Activity, imageView, bannerItemData[i])

                }
            }
            //TextHeader
            ITEM_TYPE_TEXT_HEADER -> {
                holder.setText(R.id.tvHeader, mData[position + bannerItemSize - 1].data?.text ?: "")
            }

            //content
            ITEM_TYPE_CONTENT -> {
                setVideoItem(holder, mData[position + bannerItemSize - 1])
            }


        }

    }

    /**
     *  Create layout
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            ITEM_TYPE_BANNER ->
                ViewHolder(inflaterView(R.layout.item_home_banner, parent))
            ITEM_TYPE_TEXT_HEADER ->
                ViewHolder(inflaterView(R.layout.item_home_header, parent))
            else ->
                ViewHolder(inflaterView(R.layout.item_home_content, parent))
        }
    }


    /**
     * Load layout
     */
    private fun inflaterView(mLayoutId: Int, parent: ViewGroup): View {
        //Create view
        val view = mInflater?.inflate(mLayoutId, parent, false)
        return view ?: View(parent.context)
    }


    /**
     * Load content item
     */
    private fun setVideoItem(holder: ViewHolder, item: HomeBean.Issue.Item) {
        val itemData = item.data

        val defAvatar = R.mipmap.default_avatar
        val cover = itemData?.cover?.feed
        var avatar = itemData?.author?.icon
        var tagText: String? = "#"

        // When the author’s source is empty, the information of the provider is obtained.
        if (avatar.isNullOrEmpty()) {
            avatar = itemData?.provider?.icon
        }
        // Load cover page
        GlideApp.with(mContext)
            .load(cover)
            .placeholder(R.drawable.placeholder_banner)
            .transition(DrawableTransitionOptions().crossFade())
            .into(holder.getView(R.id.iv_cover_feed))

        // If the provider information is empty, the default is displayed
        if (avatar.isNullOrEmpty()) {
            GlideApp.with(mContext)
                .load(defAvatar)
                .placeholder(R.mipmap.default_avatar).circleCrop()
                .transition(DrawableTransitionOptions().crossFade())
                .into(holder.getView(R.id.iv_avatar))

        } else {
            GlideApp.with(mContext)
                .load(avatar)
                .placeholder(R.mipmap.default_avatar).circleCrop()
                .transition(DrawableTransitionOptions().crossFade())
                .into(holder.getView(R.id.iv_avatar))
        }
        holder.setText(R.id.tv_title, itemData?.title ?: "")

        //Traverse label
        itemData?.tags?.take(4)?.forEach {
            tagText += (it.name + "/")
        }
        //Formatting time
        val timeFormat = durationFormat(itemData?.duration)

        tagText += timeFormat

        holder.setText(R.id.tv_tag, tagText!!)

        holder.setText(R.id.tv_category, "#" + itemData?.category)

        holder.setOnItemClickListener(listener = View.OnClickListener {
            goToVideoPlayer(mContext as Activity, holder.getView(R.id.iv_cover_feed), item)
        })


    }

    /**
     * Jump to the video details page to play
     *
     * @param activity
     * @param view
     */
    private fun goToVideoPlayer(activity: Activity, view: View, itemData: HomeBean.Issue.Item) {
//        val intent = Intent(activity, VideoDetailActivity::class.java)
//        intent.putExtra(Constants.BUNDLE_VIDEO_DATA, itemData)
//        intent.putExtra(VideoDetailActivity.TRANSITION, true)
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//            val pair = Pair(view, VideoDetailActivity.IMG_TRANSITION)
//            val activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
//                activity, pair)
//            ActivityCompat.startActivity(activity, intent, activityOptions.toBundle())
//        } else {
//            activity.startActivity(intent)
//            activity.overridePendingTransition(R.anim.anim_in, R.anim.anim_out)
//        }
    }


}
