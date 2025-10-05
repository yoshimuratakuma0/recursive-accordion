package com.legstart.recursiveaccordion.sample

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color


@Composable
fun SampleAction(
    isExpanded: Boolean,
    onClick: () -> Unit,
) {
    val rotation by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f,
        label = "expandIconRotation",
    )

    IconButton(
        onClick = onClick,
    ) {
        Icon(
            imageVector = Icons.Default.ArrowDropDown,
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier.rotate(rotation),
        )
    }
}