package com.victor.testapp.data.response

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root


@Root(name = "rss", strict = false)
data class NewsResponse  @JvmOverloads constructor(
    @field:Element(name = "channel")
    @param:Element(name = "channel")
    val channel: Channel,
) {

    override fun toString(): String {
        return "RssFeed [channel=$channel]"
    }
}