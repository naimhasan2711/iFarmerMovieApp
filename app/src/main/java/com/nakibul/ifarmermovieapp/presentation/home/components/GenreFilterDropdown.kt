package com.nakibul.ifarmermovieapp.presentation.home.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp

@Composable
fun GenreFilterDropdown(
    genres: List<String>,
    selectedGenre: String?,
    onGenreSelected: (String?) -> Unit,
    modifier: Modifier = Modifier
) {
    var isExpanded by remember { mutableStateOf(false) }
    var buttonWidth by remember { mutableStateOf(0) }
    var buttonOffsetY by remember { mutableStateOf(0f) }
    val density = LocalDensity.current

    Box(modifier = modifier) {
        OutlinedButton(
            onClick = { isExpanded = true },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .onGloballyPositioned { coordinates ->
                    buttonWidth = coordinates.size.width
                    buttonOffsetY = coordinates.positionInWindow().y + coordinates.size.height
                },
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(
                text = selectedGenre ?: "All Genres",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Dropdown Arrow"
            )
        }

        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false },
            modifier = Modifier
                .width(with(density) { buttonWidth.toDp() })
                .align(Alignment.TopStart)
                .padding(bottom = 16.dp),
            offset = DpOffset(0.dp, 0.dp)
        ) {
            DropdownMenuItem(
                text = { Text("All Genres") },
                onClick = {
                    onGenreSelected(null)
                    isExpanded = false
                }
            )

            genres.forEach { genre ->
                DropdownMenuItem(
                    text = { Text(genre) },
                    onClick = {
                        onGenreSelected(genre)
                        isExpanded = false
                    }
                )
            }
        }
    }
}