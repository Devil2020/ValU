package com.morse.valu.ui.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.morse.valu.TestCoroutineRule
import com.morse.valu.data.dto.Product
import com.morse.valu.data.dto.Response
import com.morse.valu.data.repository.IProductsRepository
import com.morse.valu.fakeProducts
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.*
import org.junit.Assert.*

class ProductsViewModelTest {

    private var vm: ProductsViewModel? = null
    private val repo: IProductsRepository = mockk()

    @ExperimentalCoroutinesApi
    @get:Rule
    var testCoroutineRule = TestCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        vm = ProductsViewModel(repo)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @FlowPreview
    @Test
    fun `Test Loading Products And Check The Response`() {

            testCoroutineRule.launch {
                coEvery { repo.getProducts() } returns MutableStateFlow(
                    Response.Success(
                        fakeProducts
                    )
                )
                vm?.loadProducts()
                vm?.prod?.filterNotNull()?.onEach {
                    Assert.assertTrue("Must Always Return True",it is Response.Success<*> )
                }?.launchIn(this)
            }
    }

    @After
    fun terminate() {
        vm = null
    }

}