package com.legstart.recursive_item

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
@Stable
class RecursiveItem<T>(
    val value: T,
    val children: List<RecursiveItem<T>> = emptyList(),
    initialExpanded: Boolean = false,
) {
    var expanded by mutableStateOf(initialExpanded)
    val isExpandable = children.isNotEmpty()
}