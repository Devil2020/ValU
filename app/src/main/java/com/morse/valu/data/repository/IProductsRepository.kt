package com.morse.valu.data.repository

import com.morse.valu.data.dto.Response
import kotlinx.coroutines.flow.Flow

interface IProductsRepository {
    fun getProducts() : Flow<Response>
}