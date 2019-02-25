package com.kotlin.mirzaadil.mvparchitecture.mvp.model.bean

import java.io.Serializable

/**
 * @author Mirza Adil
 * desc: Category Bean
 */
data class CategoryBean(val id: Long, val name: String, val description: String, val bgPicture: String, val bgColor: String, val headerImage: String) : Serializable
