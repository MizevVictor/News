package com.victor.testapp.other

import androidx.annotation.StringRes
import com.victor.testapp.R


interface Category {
    val category: String
    val icon: Int
    val titleRes: Int
}

data class Bitcoin(
    override val category: String = "bitcoin",
    override val icon: Int = R.drawable.ic_cointelegraph_logo,
    override val titleRes: Int = R.string.category_bitcoin
) : Category

data class Altcoin(
    override val category: String = "altcoin",
    override val icon: Int = R.drawable.ic_cointelegraph_logo,
    override val titleRes: Int = R.string.category_altcoin

) : Category

data class Blockchain(
    override val category: String = "blockchain",
    override val icon: Int = R.drawable.ic_cointelegraph_logo,
    override val titleRes: Int = R.string.category_blockchain
) : Category

fun getTitleResource(activeCategory: Category): Int = when (activeCategory) {
    is Bitcoin -> R.string.category_bitcoin
    is Altcoin -> R.string.category_altcoin
    is Blockchain -> R.string.category_blockchain
    else -> throw IllegalAccessException("Page number is invalid")
}
