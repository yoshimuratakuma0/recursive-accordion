package com.legstart.recursiveaccordion.sample

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.legstart.recursive_item.RecursiveAccordion

@Composable
fun SampleScreen() {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding,
        ) {
            items(sampleItems) { item ->
                RecursiveAccordion(
                    item = item,
                    levelLeadingPadding = 8.dp,
                    animationDurationMillis = 500,
                    action = { current ->
                        SampleAction(
                            isExpanded = current.expanded,
                            onClick = {
                                current.expanded = !current.expanded
                            },
                        )
                    },
                    content = { current ->
                        SampleContent(
                            item = current,
                            onTapItem = { tappedItem ->
                                tappedItem.expanded = !tappedItem.expanded
                            },
                        )
                    },
                )
            }
        }
    }
}