package com.morse.valu.data.remote

import com.morse.valu.BuildConfig
import com.morse.valu.data.dto.Product
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET

interface ProductsAPI {
    @GET(BuildConfig.PRODUCTS_PATH)
    suspend fun getProducts() : Response<ArrayList<Product>>
}