package com.nakibul.ifarmermovieapp.presentation.home.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WishlistBadge(
    count: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scale by animateFloatAsState(
        targetValue = if (count > 0) 1.1f else 1f,
        animationSpec = spring(),
        label = "wishlist_badge_scale"
    )

    IconButton(
        onClick = onClick,
        modifier = modifier.scale(scale)
    ) {
        BadgedBox(
            badge = {
                if (count > 0) {
                    Badge(
                        modifier = Modifier
                            .offset(x = (-4).dp, y = 4.dp)
                            .size(20.dp)
                            .clip(CircleShape)
                            .background(Color.Red),
                        containerColor = Color.Red,
                        contentColor = Color.White
                    ) {
                        Text(
                            text = if (count > 99) "99+" else count.toString(),
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }
        ) {
            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .background(MaterialTheme.colorScheme.primary, CircleShape),
                imageVector = Icons.Default.FavoriteBorder,
                contentDescription = "Wishlist",
                tint = if (count > 0) Color.Red else MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}