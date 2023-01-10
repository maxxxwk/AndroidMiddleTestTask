package com.youarelaunched.challenge.ui.screen.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.youarelaunched.challenge.data.repository.VendorsRepository
import com.youarelaunched.challenge.ui.screen.state.VendorsScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class VendorsVM @Inject constructor(
    private val repository: VendorsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(VendorsScreenUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getVendors()
    }

    fun inputSearchQuery(value: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(searchFieldValue = value)
            }
        }
    }

    fun search() {
        //TODO search
    }

    private fun getVendors() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    vendors = repository.getVendors()
                )
            }
        }
    }
}
