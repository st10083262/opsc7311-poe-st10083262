package com.example.opsc7311.ui.screens.edit.appbar

import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.opsc7311.ui.screens.edit.EditScreenUiState
import com.example.opsc7311.ui.screens.edit.EditScreenViewModel

@Composable
fun EditBar(
    editScreenViewModel: EditScreenViewModel,
    onBackPressed: () -> Unit,
    onSavePressed: () -> Unit
)
{
    val editScreenUiState by editScreenViewModel.uiState.collectAsState()

    EditBarContent(
        editScreenUiState = editScreenUiState,
        onValueChange = {
            editScreenViewModel.updateTitle(it)
        },
        onBackPressed = onBackPressed,
        onSavePressed = onSavePressed
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditBarContent(
    editScreenUiState: EditScreenUiState,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    onBackPressed: () -> Unit,
    onSavePressed: () -> Unit
)
{
    TopAppBar(
        title = {
            /*Text(
                "Simple TopAppBar",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )*/
            BasicTextField(
                value = editScreenUiState.title,
                onValueChange = onValueChange,
                singleLine = true,
                textStyle = MaterialTheme.typography.headlineMedium.copy(color = MaterialTheme
                    .colorScheme.onSurface)
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackPressed) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        },
        actions = {
            IconButton(onClick = onSavePressed) {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = "Save"
                )
            }
        }
    )
}

@Preview
@Composable
fun EditBarPreview()
{
    EditBarContent(
        editScreenUiState = EditScreenUiState(),
        onValueChange = {},
        onBackPressed = {},
        onSavePressed = {}
    )
}