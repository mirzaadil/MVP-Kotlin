package com.kotlin.mirzaadil.mvparchitecture.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import com.kotlin.mirzaadil.mvparchitecture.R


/**
 * @author Mirza Adil
 * desc:With delete button EditText
 */

class ClearEditText @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null,
                                              defStyle: Int = android.R.attr.editTextStyle) : EditText(context, attrs, defStyle), View.OnFocusChangeListener, TextWatcher {
    //Delete button to the right of EditText
    private var mClearDrawable: Drawable? = null
    private var hasFocus: Boolean = false

    init {
        init()
    }

    @Suppress("DEPRECATION")
    private fun init() {
        // Get the DrawableRight of the EditText. If it is not set, we use the default image. The order of the images is top left and bottom right (0, 1, 2, 3,).
        mClearDrawable = compoundDrawables[2]
        if (mClearDrawable == null) {
            mClearDrawable = resources.getDrawable(
                    R.mipmap.ic_action_clear)
        }

        mClearDrawable!!.setBounds(0, 0, mClearDrawable!!.intrinsicWidth,
                mClearDrawable!!.intrinsicHeight)
        //Default setting hide icon
        setClearIconVisible(false)
        // Set the monitor for focus change
        onFocusChangeListener = this
        // Set the listener whose content changes in the input box
        addTextChangedListener(this)
    }

    /**
     * When the focus of ClearEditText changes,
     * Enter a length of zero, hide the delete icon, otherwise, display the delete icon
     */
    override fun onFocusChange(v: View, hasFocus: Boolean) {
        this.hasFocus = hasFocus
        if (hasFocus) {
            setClearIconVisible(text.isNotEmpty())
        } else {
            setClearIconVisible(false)
        }
    }

    /* @说明：isInnerWidth, isInnerHeight为true，触摸点在删除图标之内，则视为点击了删除图标
     * event.getX() 获取相对应自身左上角的X坐标
     * event.getY() 获取相对应自身左上角的Y坐标
     * getWidth() 获取控件的宽度
     * getHeight() 获取控件的高度
     * getTotalPaddingRight() 获取删除图标左边缘到控件右边缘的距离
     * getPaddingRight() 获取删除图标右边缘到控件右边缘的距离
     * isInnerWidth:
     *  getWidth() - getTotalPaddingRight() 计算删除图标左边缘到控件左边缘的距离
     *  getWidth() - getPaddingRight() 计算删除图标右边缘到控件左边缘的距离
     * isInnerHeight:
     *  distance 删除图标顶部边缘到控件顶部边缘的距离
     *  distance + height 删除图标底部边缘到控件顶部边缘的距离
     */
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_UP) {
            if (compoundDrawables[2] != null) {
                val x = event.x.toInt()
                val y = event.y.toInt()
                val rect = compoundDrawables[2].bounds
                val height = rect.height()
                val distance = (getHeight() - height) / 2
                val isInnerWidth = x > width - totalPaddingRight && x < width - paddingRight
                val isInnerHeight = y > distance && y < distance + height
                if (isInnerWidth && isInnerHeight) {
                    this.setText("")
                }
            }
        }
        return super.onTouchEvent(event)
    }

    private fun setClearIconVisible(visible: Boolean) {
        val right = if (visible) mClearDrawable else null
        setCompoundDrawables(compoundDrawables[0],
                compoundDrawables[1], right, compoundDrawables[3])
    }

    override fun onTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        if (hasFocus) {
            setClearIconVisible(s.isNotEmpty())
        }
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int,
                                   after: Int) {

    }

    override fun afterTextChanged(s: Editable) {

    }


}