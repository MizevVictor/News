package com.victor.testapp.data.response

import org.simpleframework.xml.Element
import org.simpleframework.xml.Namespace
import org.simpleframework.xml.Root

/**
 * Created by Viktor Mizev on 11.07.2021.
 */

@Root(name = "item", strict = false)
class Article @JvmOverloads constructor(
    @field:Element(name = "title")
    @param:Element(name = "title")
    val title: String? = null,

    @field:Element(name = "link")
    @param:Element(name = "link")
    val link: String? = null,

    @field:Element(name = "pubDate")
    @param:Element(name = "pubDate")
    val pubDate: String? = null,

    @field:Element(name = "content")
    @param:Element(name = "content")
    @field:Namespace(reference = "http://search.yahoo.com/mrss/", prefix = "media")
    @param:Namespace(reference = "http://search.yahoo.com/mrss/", prefix = "media")
    val media: Media? = null,

    ) {

    override fun toString(): String {
        return ("RssItem [title=" + title + ", link=" + link + ", pubDate=" + pubDate
                + "]")
    }
}