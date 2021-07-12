package com.victor.testapp.ui.articles

import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.victor.testapp.other.Category
import com.victor.testapp.ui.style.NewsTheme


@Composable
fun BottomBar(
    categoryList: List<Category>,
    activeCategory: Category,
    onMenuClicked: (Category) -> Unit
) {
    BottomAppBar(
        backgroundColor = NewsTheme.colors.bottomNavBackground,
        elevation = 10.dp
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().preferredHeight(50.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            categoryList.forEach { category ->
                BottomNavigationItem(
                    label = { Text(stringResource(category.titleRes)) },
                    icon = {
                        Icon(
                            asset = vectorResource(id = category.icon),
                            modifier = Modifier.size(25.dp)
                        )
                    },
                    selected = activeCategory == category,
                    onClick = {
                        onMenuClicked(category)
                    },
                    selectedContentColor = NewsTheme.colors.bottomNavActiveIconColor,
                    unselectedContentColor = NewsTheme.colors.bottomNavInActiveIconColor
                )
            }
        }
    }
}
