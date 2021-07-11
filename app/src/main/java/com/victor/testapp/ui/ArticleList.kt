
import android.content.Intent
import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.victor.testapp.WebViewActivity
import com.victor.testapp.data.response.Article
import com.victor.testapp.ui.style.NewsTheme


@Composable
fun ArticleRow(article: Article, onClick: () -> Unit) {
    Column(modifier = Modifier.clickable(onClick = { onClick() })) {
        Row(
                modifier = Modifier.padding(all = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
//            RemoteImage(
//                    url = article.urlToImage,
//                    modifier = Modifier.preferredSize(100.dp)
//            )
            WidthSpacer(value = 10.dp)
            Column {

                article.title?.let {
                    Text(
                        text = it,
                        style = articleTitleStyle.copy(color = NewsTheme.colors.titleColor),
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                HeightSpacer(value = 4.dp)
                article.pubDate?.let {
                    Text(
                        text = it,
                        style = dateTextStyle.copy(color = NewsTheme.colors.sourceColor)
                    )
                }
            }
        }
        HeightSpacer(value = 10.dp)
        Divider(
                color = NewsTheme.colors.dividerColor
        )
    }
}

@Composable
fun ArticleList(articles: List<Article>) {
    val context = ContextAmbient.current
    val isDark = NewsTheme.colors.isDark
    LazyColumnFor(
            items = articles,
            itemContent = { article: Article ->
                ArticleRow(
                        article = article,
                        onClick = {
                            val intent = Intent(context, WebViewActivity::class.java)
                            intent.putExtra(WebViewActivity.EXTRA_URL, article.link.toString())
                            context.startActivity(intent)
//                            CustomTabUtil.launch(context, article.link.toString(), isDark)
                        }
                )
            }
    )
}
