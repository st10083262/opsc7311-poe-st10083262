package com.example.opsc7311.ui.screens.categories

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.opsc7311.ui.screens.list.ListScreenContent
import com.example.opsc7311.viewmodels.SharedViewModel
import com.example.opsc7311.viewmodels.SharedViewModelUiState

@Composable
fun CategoryScreenContent(
    sharedViewModelUiState: SharedViewModelUiState,
    modifier: Modifier = Modifier
) {

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    )
    {
        items(sharedViewModelUiState.filteredCategories.entries.toList()) { entry->
            Card {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = entry.key)
                    Text(text = entry.value.toString() + " Hours")
                }
            }
        }
    }

}

@Preview
@Composable
fun ListScreenContentPreview()
{
    CategoryScreenContent(
        sharedViewModelUiState = SharedViewModelUiState()
    )
}