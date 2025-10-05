package com.legstart.recursive_item

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Dp
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
    expandedIds: Set<String> = emptySet(),
    onToggle: (RecursiveItem<T>) -> Unit = {},
    action: @Composable (RecursiveItem<T>, () -> Unit) -> Unit = { _, _ -> },
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
        expandedIds = expandedIds,
        onToggle = onToggle,
        action = action,
        content = content,
    )
}

@Composable
internal fun <T> InnerRecursiveAccordion(
    item: RecursiveItem<T>,
    level: Int,
    levelLeadingPadding: Dp,
    modifier: Modifier = Modifier,
    animationDurationMillis: Int,
    showLevelGuide: Boolean,
    levelGuideWidth: Dp,
    levelGuideColor: Color,
    expandedIds: Set<String>,
    onToggle: (RecursiveItem<T>) -> Unit,
    action: @Composable (RecursiveItem<T>, () -> Unit) -> Unit,
    content: @Composable (RecursiveItem<T>) -> Unit,
) {
    val isExpanded = item.id in expandedIds

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
                action(item) { onToggle(item) }
            }
        }

        if (item.isExpandable) {
            val expandProgress by animateFloatAsState(
                targetValue = if (isExpanded) 1f else 0f,
                animationSpec = tween(durationMillis = animationDurationMillis),
                label = "expandProgress"
            )

            Column(
                modifier = Modifier
                    .graphicsLayer {
                        alpha = expandProgress
                        scaleY = expandProgress
                        transformOrigin = androidx.compose.ui.graphics.TransformOrigin(
                            pivotFractionX = 0.5f,
                            pivotFractionY = 0f,
                        )
                    }
                    .layout { measurable, constraints ->
                        val placeable = measurable.measure(constraints)
                        val height = (placeable.height * expandProgress).toInt()
                        layout(placeable.width, height) {
                            placeable.placeRelative(0, 0)
                        }
                    }
            ) {
                if (isExpanded || expandProgress > 0f) {
                    item.children.forEach { child ->
                        InnerRecursiveAccordion(
                            item = child,
                            level = level + 1,
                            levelLeadingPadding = levelLeadingPadding,
                            modifier = modifier,
                            animationDurationMillis = animationDurationMillis,
                            showLevelGuide = showLevelGuide,
                            levelGuideWidth = levelGuideWidth,
                            levelGuideColor = levelGuideColor,
                            expandedIds = expandedIds,
                            onToggle = onToggle,
                            action = action,
                            content = content,
                        )
                    }
                }
            }
        }
    }
}