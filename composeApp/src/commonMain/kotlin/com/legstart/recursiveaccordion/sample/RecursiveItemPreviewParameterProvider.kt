package com.legstart.recursiveaccordion.sample

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
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

@Preview
@Composable
fun RecursiveItemPreview(
    @PreviewParameter(RecursivePreviewParameterProvider::class) itemWithLabel: RecursiveItemWithLabel
) {
    Surface {
        val (label, item) = itemWithLabel
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
                action = { current ->
                    SampleAction(
                        isExpanded = current.expanded,
                        onClick = { current.expanded = !current.expanded },
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