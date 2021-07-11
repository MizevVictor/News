package com.victor.testapp.data.response

import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root


/**
 * Created by Viktor Mizev on 11.07.2021.
 */

@Root(name = "channel", strict = false)
class Channel  @JvmOverloads constructor(

    @field:ElementList(inline = true,entry = "item", required = false)
    @param:ElementList(inline = true,entry = "item", required = false)
    var items: List<Article>? = null


    ){
//    @Element
//    private val title: String? = null
//
//    @Element
//    private val image: RssImage? = null


    override fun toString(): String {
        return "Channel [item=$items]"
    }
}