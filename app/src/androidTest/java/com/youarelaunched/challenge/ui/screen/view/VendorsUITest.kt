package com.youarelaunched.challenge.ui.screen.view

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithContentDescription
import com.youarelaunched.challenge.data.repository.VendorsRepository
import com.youarelaunched.challenge.data.repository.model.Vendor
import com.youarelaunched.challenge.ui.theme.VendorAppTheme
import org.junit.Rule
import org.junit.Test

class VendorsUITest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun noResultTest() {
        val viewModel = VendorsVM(
            object : VendorsRepository {
                override suspend fun getVendors(searchQuery: String?): List<Vendor> = emptyList()
            }
        )
        composeTestRule.setContent {
            VendorAppTheme {
                VendorsRoute(viewModel = viewModel)
            }
        }
        composeTestRule.onNodeWithContentDescription("No result").assertIsDisplayed()
    }

    @Test
    fun vendorsListTest() {
        val viewModel = VendorsVM(
            object : VendorsRepository {
                override suspend fun getVendors(searchQuery: String?): List<Vendor> {
                    return listOf(
                        Vendor(
                            id = 0,
                            companyName = "",
                            coverPhoto = "",
                            area = "",
                            favorite = false,
                            categories = null,
                            tags = null
                        )
                    )
                }
            }
        )
        composeTestRule.setContent {
            VendorAppTheme {
                VendorsRoute(viewModel = viewModel)
            }
        }
        composeTestRule.onAllNodesWithContentDescription("Vendors list item").onFirst()
            .assertIsDisplayed()
    }

}
