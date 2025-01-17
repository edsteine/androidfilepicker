package com.edsteine.androidfilepicker.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * A composable for creating a section that groups related permission buttons.
 *
 * This composable provides a consistent look and feel for sections in the permission
 * screen. It includes a title, an icon, and an expandable arrow.
 *
 * @param title The title text for the permission section.
 * @param icon The icon to display at the beginning of the section.
 * @param modifier Modifier to apply to the surface component.
 * @param content The composable content to display inside the permission section.
 */
@Composable
fun PermissionSection(
    title: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier, // Apply the provided modifier
        shape = MaterialTheme.shapes.medium, // Apply a medium shape for the surface
        tonalElevation = 2.dp // Apply a tonal elevation to create shadow effect
    ) {
        Column( // Start a column to arrange content vertically
            modifier = Modifier.padding(16.dp) // Apply padding for inner content
        ) {
            Row( // Start a row for title and icon
                modifier = Modifier.fillMaxWidth(), // Make row fill its width
                verticalAlignment = Alignment.CenterVertically // Align items vertically in the center
            ) {
                Icon( // Icon composable to display our icon
                    imageVector = icon, // The icon to be displayed
                    contentDescription = null, // No content description needed for decorative icon
                    tint = MaterialTheme.colorScheme.primary // Set the tint color of the icon
                )
                Spacer(modifier = Modifier.width(8.dp)) // Add some horizontal space
                Text(
                    text = title, // Show the section title
                    style = MaterialTheme.typography.titleLarge.copy(fontSize = 18.sp), // Set the text style
                    color = MaterialTheme.colorScheme.onSurface // Set the text color
                )

                Spacer(modifier = Modifier.weight(1f)) // Add a spacer that will consume all the space between the title and the arrow

                Icon( // Icon composable for our expandable arrow
                    imageVector = Icons.Filled.ArrowDropDown, // The icon to be displayed
                    contentDescription = null, // No content description needed for decorative icon
                    tint = MaterialTheme.colorScheme.onSurface // Set the tint color of the icon
                )
            }
            Spacer(modifier = Modifier.height(8.dp)) // Add vertical space between the title and the content
            content() // Composable content for the section
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PermissionSectionPreview() {
    PermissionSection(
        title = "Example Permission Section",
        icon = Icons.AutoMirrored.Filled.ArrowBack,
        content = {
            Text("Content of the section")
        }
    )
}
