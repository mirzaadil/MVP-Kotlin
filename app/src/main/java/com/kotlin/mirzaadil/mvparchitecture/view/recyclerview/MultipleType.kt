package com.kotlin.mirzaadil.mvparchitecture.view.recyclerview

/**
 * @author Mirza ADil
 * desc: Multi-layout entry type
 */

interface MultipleType<in T> {
    fun getLayoutId(item: T, position: Int): Int
}
