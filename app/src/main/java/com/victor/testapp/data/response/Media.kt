package com.victor.testapp.data.response

import org.simpleframework.xml.Attribute

/**
 * Created by Viktor Mizev on 12.07.2021.
 */
const val MEDIUMTYPE_IMAGE = "image"

class Media @JvmOverloads constructor(
    @field:Attribute(name = "url")
    @param:Attribute(name = "url")
    val url: String? = null,

    @field:Attribute(name = "medium")
    @param:Attribute(name = "medium")
    val medium: String? = null,


    ){

}