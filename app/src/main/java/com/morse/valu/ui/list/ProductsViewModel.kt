package com.morse.valu.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.morse.valu.data.dto.Response
import com.morse.valu.data.repository.IProductsRepository
import com.morse.valu.data.repository.ProductsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ProductsViewModel constructor(private val repository: IProductsRepository = ProductsRepository()) :
    ViewModel() {

    private val _products = MutableSharedFlow<Response>()
    val products: Flow<Response> get() = _products

    fun loadProducts() {
        repository.getProducts().onEach { _products.emit(it) }.launchIn(viewModelScope)
    }

}