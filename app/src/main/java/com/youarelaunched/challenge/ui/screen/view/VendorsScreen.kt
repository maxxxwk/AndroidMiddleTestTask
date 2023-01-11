package com.youarelaunched.challenge.ui.screen.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.youarelaunched.challenge.middle.R
import com.youarelaunched.challenge.ui.screen.state.VendorsScreenUiState
import com.youarelaunched.challenge.ui.screen.view.components.ChatsumerSnackbar
import com.youarelaunched.challenge.ui.screen.view.components.NoResult
import com.youarelaunched.challenge.ui.screen.view.components.SearchField
import com.youarelaunched.challenge.ui.screen.view.components.VendorItem
import com.youarelaunched.challenge.ui.theme.VendorAppTheme

@Composable
fun VendorsRoute(
    viewModel: VendorsVM
) {
    val uiState by viewModel.uiState.collectAsState()

    VendorsScreen(
        uiState = uiState,
        onSearchFieldValueChange = viewModel::inputSearchQuery,
        search = { viewModel.getVendors(uiState.searchFieldValue) }
    )
}

@Composable
fun VendorsScreen(
    uiState: VendorsScreenUiState,
    onSearchFieldValueChange: (String) -> Unit,
    search: () -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 24.dp, horizontal = 16.dp),
        backgroundColor = VendorAppTheme.colors.background,
        snackbarHost = { ChatsumerSnackbar(it) }
    ) { paddings ->
        Column(
            modifier = Modifier.padding(paddings),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SearchField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                value = uiState.searchFieldValue,
                onValueChange = onSearchFieldValueChange,
                placeholder = stringResource(R.string.search_field_placeholder),
                onSearchClicked = search
            )
            if (!uiState.vendors.isNullOrEmpty()) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(vertical = 24.dp)
                ) {
                    items(uiState.vendors) { vendor ->
                        VendorItem(
                            vendor = vendor,
                            modifier = Modifier.semantics {
                                contentDescription = "Vendors list item"
                            })
                    }
                }
            } else if (uiState.vendors != null && uiState.vendors.isEmpty()) {
                NoResult(modifier = Modifier.semantics { contentDescription = "No result" })
            }
        }
    }
}