package com.youarelaunched.challenge.ui.screen.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.youarelaunched.challenge.ui.theme.VendorAppTheme

@Composable
fun NoResult() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Sorry! No results found...",
            style = VendorAppTheme.typography.h2,
            color = VendorAppTheme.colors.textDarkGreen
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Please try a different search request\n" +
                    "or browse businesses from the list",
            style = VendorAppTheme.typography.subtitle2,
            textAlign = TextAlign.Center,
            color = VendorAppTheme.colors.textDark
        )
    }
}
