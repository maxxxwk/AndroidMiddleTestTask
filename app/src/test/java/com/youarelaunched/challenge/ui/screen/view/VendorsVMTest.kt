package com.youarelaunched.challenge.ui.screen.view

import com.youarelaunched.challenge.data.repository.model.Vendor
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class VendorsVMTest {
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `The StateFlow should emit correct state when data loaded successfully`() = runTest {
        val vendors = listOf(Vendor(0, "", "", "", false, null, null))
        val viewModel = VendorsVM(mockk { coEvery { getVendors() } returns vendors })
        assertEquals(vendors, viewModel.uiState.drop(1).first().vendors)
    }

    @Test
    fun `The StateFlow should emit correct state when data loaded with error`() = runTest {
        val viewModel = VendorsVM(mockk { coEvery { getVendors() } throws Exception() })
        assertEquals(null, viewModel.uiState.first().vendors)
    }
}
