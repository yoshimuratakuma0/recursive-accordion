package com.legstart.recursiveaccordion.sample

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.legstart.recursive_item.RecursiveItem


@Composable
fun SampleContent(
    item: RecursiveItem<Fruit>,
    modifier: Modifier = Modifier,
    onTapItem: (RecursiveItem<Fruit>) -> Unit = {},
) {
    Row(
        modifier = modifier
            .height(48.dp)
            .fillMaxWidth()
            .clickable { onTapItem(item) },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = item.value.name,
            modifier = Modifier.padding(start = 8.dp),
            style = TextStyle(
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
            ),
        )
    }
}