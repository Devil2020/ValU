package com.morse.valu.data.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
    val image: String,
    val title: String,
    val id: Int,
    val price: Double,
    val category: String,
    val description : String,
    val rating: Rating
) : Parcelable {
    fun getPrice () = "$$price"
    @Parcelize
    data class Rating(val rate: Double, val count: Int) : Parcelable
}