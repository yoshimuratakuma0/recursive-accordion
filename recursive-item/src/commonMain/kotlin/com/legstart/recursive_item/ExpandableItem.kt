package com.legstart.recursive_item

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp

@Composable
fun <T> RecursiveAccordion(
    item: RecursiveItem<T>,
    modifier: Modifier = Modifier,
    levelLeadingPadding: Dp = 4.dp,
    animationDurationMillis: Int = 250,
    showLevelGuide: Boolean = true,
    levelGuideWidth: Dp = 1.dp,
    levelGuideColor: Color = Color(0xFFE0E0E0),
    action: @Composable (RecursiveItem<T>) -> Unit = {},
    content: @Composable (RecursiveItem<T>) -> Unit,
) {
    InnerRecursiveAccordion(
        item = item,
        level = 0,
        levelLeadingPadding = levelLeadingPadding,
        modifier = modifier,
        animationDurationMillis = animationDurationMillis,
        showLevelGuide = showLevelGuide,
        levelGuideWidth = levelGuideWidth,
        levelGuideColor = levelGuideColor,
        action = action,
        content = content,
    )
}

@Composable
internal fun <T> InnerRecursiveAccordion(
    item: RecursiveItem<T>,
    level: Int = 0,
    levelLeadingPadding: Dp,
    modifier: Modifier = Modifier,
    animationDurationMillis: Int = 250,
    expandFrom: Alignment.Vertical = Alignment.Top,
    shrinkTowards: Alignment.Vertical = Alignment.Top,
    showLevelGuide: Boolean = true,
    levelGuideWidth: Dp = 1.dp,
    levelGuideColor: Color = Color(0xFFE0E0E0),
    action: @Composable (RecursiveItem<T>) -> Unit = {},
    content: @Composable (RecursiveItem<T>) -> Unit,
) {
    Column(
        modifier = modifier
            .padding(start = levelLeadingPadding * (level + 1)),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .drawBehind {
                    if (showLevelGuide && level >= 1) {
                        val w = levelGuideWidth.toPx()
                        drawRect(
                            color = levelGuideColor,
                            topLeft = androidx.compose.ui.geometry.Offset(0f, 0f),
                            size = androidx.compose.ui.geometry.Size(w, size.height)
                        )
                    }
                },
        ) {
            Box(
                modifier = Modifier.weight(1f),
            ) {
                content(item)
            }
            if (item.isExpandable) {
                action.invoke(item)
            }
        }

        if (item.isExpandable) {
            val enterSpec: FiniteAnimationSpec<IntSize> = tween(animationDurationMillis)
            val exitSpec: FiniteAnimationSpec<IntSize> = tween(animationDurationMillis)
            AnimatedVisibility(
                visible = item.expanded,
                enter = expandVertically(animationSpec = enterSpec, expandFrom = expandFrom),
                exit = shrinkVertically(animationSpec = exitSpec, shrinkTowards = shrinkTowards),
            ) {
                Column {
                    item.children.forEach { child ->
                        InnerRecursiveAccordion(
                            item = child,
                            level = level + 1,
                            levelLeadingPadding = levelLeadingPadding,
                            modifier = modifier,
                            animationDurationMillis = animationDurationMillis,
                            expandFrom = expandFrom,
                            shrinkTowards = shrinkTowards,
                            showLevelGuide = showLevelGuide,
                            levelGuideWidth = levelGuideWidth,
                            levelGuideColor = levelGuideColor,
                            action = action,
                            content = content,
                        )
                    }
                }
            }
        }
    }
}