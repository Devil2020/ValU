package com.morse.valu.app

import android.app.Activity
import android.content.Intent
import com.morse.valu.data.dto.Product
import com.morse.valu.ui.detail.ProductDetailsActivity
import com.morse.valu.ui.list.ProductsListActivity

interface Direction {
    fun add()
    fun delete()
}

interface Coordinator {
    fun navigate(direction: Direction)
    fun back()
}

class ProductListDirection(private val current: Activity) : Direction {
    override fun add() {
        current.finish()
        current.startActivity(Intent(current, ProductsListActivity::class.java))
    }

    override fun delete() {
        current.finish()
    }
}

class ProductDetailDirection(
    private val current: Activity,
    private val product: Product
) : Direction {
    companion object {
    val argsKey = "PRODUCT-DETAILS"
    }
    override fun add() {
        current.startActivity(Intent(current, ProductDetailsActivity::class.java).apply {
            putExtra(
                argsKey,
                product
            )
        })
    }

    override fun delete() {
        current.finish()
    }
}


object ValUCoordinator : Coordinator {
    private lateinit var lastDirection: Direction
    override fun navigate(direction: Direction) = direction.add().also { lastDirection = direction }
    override fun back() = lastDirection.delete()
}