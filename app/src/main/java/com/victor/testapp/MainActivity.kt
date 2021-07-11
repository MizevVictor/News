package com.victor.testapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.victor.testapp.WebViewActivity.EXTRA_URL
import kotlinx.coroutines.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter
import org.sufficientlysecure.htmltextview.HtmlTextView
import java.io.IOException
import java.io.InputStream
import kotlin.math.log


class MainActivity : AppCompatActivity() {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val textView = findViewById<HtmlTextView>(R.id.html_text)
        textView.setOnClickATagListener { _, _, href ->
            val intent = Intent(textView.context, WebViewActivity::class.java)
            intent.putExtra(EXTRA_URL, href)
            startActivity(intent)
            true
        }
        coroutineScope.launch {
            val document =
                withContext(Dispatchers.IO) {
                    delay(10000)
                    Jsoup.connect("https://cointelegraph.com/news/trust-is-still-a-must-in-the-trustless-world-of-cryptocurrency")
                        .get()
                }

            val cleanedArticle = withContext(Dispatchers.Default) {
                val article = document.getElementsByClass("post post-page__article").first()

                cleanUpHtml(
                    article,
                    "post-meta post__block post__block_post-meta",
                    "post-actions post__block post__block_post-actions",
                    "article-subscription-widget",
                    "tags-list post__block post__block_tags",
                    "related-list"
                )
                article
            }


            textView.setHtml(cleanedArticle.toString(), HtmlHttpImageGetter(textView))
        }
    }


    private fun cleanUpHtml(element: Element?, vararg elementNames: String) =
        elementNames.forEach { element?.getElementsByClass(it)?.first()?.remove() }



}