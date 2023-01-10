package com.youarelaunched.challenge.ui.screen.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.youarelaunched.challenge.data.repository.VendorsRepository
import com.youarelaunched.challenge.ui.screen.state.VendorsScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
@HiltViewModel
class VendorsVM @Inject constructor(
    private val repository: VendorsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(VendorsScreenUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getVendors()
        _uiState.filter { it.searchFieldValue.length >= MIN_CHARS_NUMBER_TO_SEARCH }
            .debounce(SEARCH_DEBOUNCE_TIME)
            .distinctUntilChanged()
            .onEach { search() }
            .launchIn(viewModelScope)
    }

    fun inputSearchQuery(value: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(searchFieldValue = value)
            }
        }
    }

    fun search() {
        getVendors(uiState.value.searchFieldValue)
    }

    private fun getVendors(searchQuery: String? = null) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(vendors = repository.getVendors(searchQuery))
            }
        }
    }

    private companion object {
        const val MIN_CHARS_NUMBER_TO_SEARCH = 3
        const val SEARCH_DEBOUNCE_TIME = 500L
    }
}
