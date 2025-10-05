package com.legstart.recursiveaccordion.sample

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateSetOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.legstart.recursive_item.RecursiveAccordion

@Composable
fun SampleScreen() {
    val expandedIds = remember {
        mutableStateSetOf<String>().apply {
            addAll(defaultExpandedIds)
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding,
        ) {
            items(sampleItems) { item ->
                RecursiveAccordion(
                    item = item,
                    levelLeadingPadding = 8.dp,
                    animationDurationMillis = 500,
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
                    },
                    content = { current ->
                        SampleContent(
                            item = current,
                            onTapItem = { tappedItem ->
                                if (tappedItem.id in expandedIds) {
                                    expandedIds.remove(tappedItem.id)
                                } else {
                                    expandedIds.add(tappedItem.id)
                                }
                            },
                        )
                    },
                )
            }
        }
    }
}