package com.legstart.recursiveaccordion.sample

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateSetOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.legstart.recursive_item.RecursiveAccordion
import com.legstart.recursive_item.RecursiveItem
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import org.jetbrains.compose.ui.tooling.preview.PreviewParameterProvider

typealias RecursiveItemWithLabel = Pair<String, RecursiveItem<Fruit>>

private val itemsWithLabel = listOf(
    Pair(
        "Single Item",
        RecursiveItem(id = "1", value = Fruit("Apple"))
    ),
    Pair(
        "Item with children",
        RecursiveItem(
            id = "2",
            value = Fruit("Citrus"),
            children = listOf(
                RecursiveItem(id = "2-1", value = Fruit("Orange")),
                RecursiveItem(id = "2-2", value = Fruit("Lemon")),
                RecursiveItem(id = "2-3", value = Fruit("Lime")),
            ),
        )
    ),
    Pair(
        "Multi-level Item",
        RecursiveItem(
            id = "3",
            value = Fruit("Berries"),
            children = listOf(
                RecursiveItem(id = "3-1", value = Fruit("Strawberry")),
                RecursiveItem(
                    id = "3-2",
                    value = Fruit("Blueberry"),
                    children = listOf(
                        RecursiveItem(id = "3-2-1", value = Fruit("Wild Blueberry")),
                        RecursiveItem(id = "3-2-2", value = Fruit("Cultivated Blueberry")),
                    ),
                ),
                RecursiveItem(id = "3-3", value = Fruit("Raspberry")),
            ),
        ),
    ),
    Pair(
        "Collapsed Item with children",
        RecursiveItem(
            id = "4",
            value = Fruit("Tropical Fruits"),
            children = listOf(
                RecursiveItem(id = "4-1", value = Fruit("Mango")),
                RecursiveItem(id = "4-2", value = Fruit("Pineapple")),
                RecursiveItem(id = "4-3", value = Fruit("Papaya")),
            ),
        )
    ),
)

val sampleItems = itemsWithLabel.map { it.second }

// デフォルトで展開するアイテムの ID
val defaultExpandedIds = setOf("2", "3", "3-2")

class RecursivePreviewParameterProvider : PreviewParameterProvider<RecursiveItemWithLabel> {
    override val values: Sequence<RecursiveItemWithLabel> =
        sequenceOf(*itemsWithLabel.toTypedArray())
}

@Preview
@Composable
fun RecursiveItemPreview(
    @PreviewParameter(RecursivePreviewParameterProvider::class) itemWithLabel: RecursiveItemWithLabel
) {
    Surface {
        val (label, item) = itemWithLabel
        val expandedIds = remember { mutableStateSetOf<String>() }

        Column {
            BasicText(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally),
                text = label,
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                ),
            )

            HorizontalDivider()

            RecursiveAccordion(
                item = item,
                levelLeadingPadding = 16.dp,
                expandedIds = expandedIds,
                onToggle = { toggledItem ->
                    if (toggledItem.id in expandedIds) {
                        expandedIds.remove(toggledItem.id)
                    } else {
                        expandedIds.add(toggledItem.id)
                    }
                },
                action = { current, isExpanded, onToggle ->
                    SampleAction(
                        isExpanded = isExpanded,
                        onClick = onToggle,
                    )
                }
            ) { current ->
                SampleContent(
                    item = current,
                    modifier = Modifier,
                )
            }
        }
    }
}