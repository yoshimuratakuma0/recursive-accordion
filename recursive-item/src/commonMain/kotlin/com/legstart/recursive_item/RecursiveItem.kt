package com.legstart.recursive_item

import androidx.compose.runtime.Stable

@Stable
data class RecursiveItem<T>(
    val id: String,
    val value: T,
    val children: List<RecursiveItem<T>> = emptyList(),
) {
    val isExpandable = children.isNotEmpty()
}