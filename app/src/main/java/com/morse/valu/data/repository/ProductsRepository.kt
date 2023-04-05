package com.morse.valu.data.repository

import com.mohammedmorse.utils.extensions.consume
import com.morse.valu.data.dto.Product
import com.morse.valu.data.dto.Response
import com.morse.valu.data.remote.ProductsAPI
import com.morse.valu.data.remote.RetrofitCore
import kotlinx.coroutines.flow.Flow

class ProductsRepository(private val remote: ProductsAPI = RetrofitCore.getGatewayAgent()) :
    IProductsRepository {
    override fun getProducts(): Flow<Response> {
        return consume<Product> {
            remote.getProducts()
        }
    }
}