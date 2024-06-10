package com.example.opsc7311.ui.screens.list.appbar

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBarDateItem(text: String, expanded: Boolean, onClick: () -> Unit)
{
    val angle: Float by animateFloatAsState(
        targetValue = if(expanded) 180f else 0f
    )

    Card(
        onClick = onClick
    ) {
        Row (
            modifier = Modifier.padding(all = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ){
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge,
                maxLines = 1
            )
            Icon(
                modifier = Modifier
                    .rotate(degrees = angle),
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = null
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBarItem(text: String, onClick: () -> Unit) {

    Card(
        onClick = onClick
    ) {
        Text(
            modifier = Modifier
                .padding(all = 8.dp)
                .height(Icons.Filled.ArrowDropDown.defaultHeight)
                .wrapContentHeight(align = Alignment.CenterVertically),
            text = text,
            style = MaterialTheme.typography.labelLarge,
            maxLines = 1
        )
    }
}

@Preview
@Composable
fun PreviewFilterBarDateItem()
{
    FilterBarDateItem(text = "12 January 2021", expanded = false, onClick = {})
}

@Preview
@Composable
fun PreviewFilterBarItem()
{
    FilterBarItem(text = "Reset", onClick = {})
}