
import androidx.compose.foundation.Box
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.VectorAsset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.victor.testapp.ui.style.NewsTheme
import com.victor.testapp.ui.style.titleColorDark
import dev.chrisbanes.accompanist.coil.CoilImageWithCrossfade


@Composable
fun HeightSpacer(value: Dp) {
    Spacer(modifier = Modifier.preferredHeight(value))
}

@Composable
fun WidthSpacer(value: Dp) {
    Spacer(modifier = Modifier.preferredWidth(value))
}

@Composable
fun RemoteImage(
        url: String?,
        modifier: Modifier,
        errorImage: VectorAsset = vectorResource(id = R.drawable.abc_ic_ab_back_material),
        contentScale: ContentScale = ContentScale.Crop,
        shape: Shape = RoundedCornerShape(5.dp)
) {
    Box(
            modifier = modifier
    ) {
        if (url.isNullOrEmpty()) {
            Image(
                    modifier = Modifier.fillMaxSize(),
                    asset = errorImage,
                    colorFilter = ColorFilter(
                            color = if (NewsTheme.colors.isDark) titleColorDark else Color.Black,
                            blendMode = BlendMode.SrcAtop
                    )
            )
        } else {
            Surface(
                    color = Color.Transparent,
                    shape = shape
            ) {
                CoilImageWithCrossfade(
                        data = url,
                        modifier = modifier,
                        contentScale = contentScale,
                        loading = {
                            Stack(Modifier.fillMaxSize()) {
                                CircularProgressIndicator(
                                        color = NewsTheme.colors.circularLoaderColor,
                                        modifier = Modifier.gravity(Alignment.Center)
                                )
                            }
                        }
                )
            }
        }
    }
}
