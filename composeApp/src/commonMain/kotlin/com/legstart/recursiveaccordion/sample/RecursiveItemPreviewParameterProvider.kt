package com.legstart.recursiveaccordion.sample

import com.legstart.recursive_item.RecursiveItem
import org.jetbrains.compose.ui.tooling.preview.PreviewParameterProvider

typealias RecursiveItemWithLabel = Pair<String, RecursiveItem<Fruit>>

private val itemsWithLabel = listOf(
    Pair(
        "Single Item",
        RecursiveItem(value = Fruit("Apple"))
    ),
    Pair(
        "Item with children",
        RecursiveItem(
            value = Fruit("Citrus"),
            children = listOf(
                RecursiveItem(value = Fruit("Orange")),
                RecursiveItem(value = Fruit("Lemon")),
                RecursiveItem(value = Fruit("Lime")),
            ),
            initialExpanded = true,
        )
    ),
    Pair(
        "Multi-level Item",
        RecursiveItem(
            value = Fruit("Berries"),
            children = listOf(
                RecursiveItem(value = Fruit("Strawberry")),
                RecursiveItem(
                    value = Fruit("Blueberry"),
                    children = listOf(
                        RecursiveItem(value = Fruit("Wild Blueberry")),
                        RecursiveItem(value = Fruit("Cultivated Blueberry")),
                    ),
                    initialExpanded = true,
                ),
                RecursiveItem(value = Fruit("Raspberry")),
            ),
            initialExpanded = true,
        ),
    ),
    Pair(
        "Collapsed Item with children",
        RecursiveItem(
            value = Fruit("Tropical Fruits"),
            children = listOf(
                RecursiveItem(value = Fruit("Mango")),
                RecursiveItem(value = Fruit("Pineapple")),
                RecursiveItem(value = Fruit("Papaya")),
            ),
            initialExpanded = false,
        )
    ),
)

val sampleItems = itemsWithLabel.map { it.second }

class RecursivePreviewParameterProvider : PreviewParameterProvider<RecursiveItemWithLabel> {
    override val values: Sequence<RecursiveItemWithLabel> =
        sequenceOf(*itemsWithLabel.toTypedArray())
}