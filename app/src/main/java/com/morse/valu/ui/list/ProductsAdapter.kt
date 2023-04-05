package com.morse.valu.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.halanchallenge.utils.extensions.render
import com.morse.valu.data.dto.Product
import com.morse.valu.databinding.ProductItemLayoutBinding
import com.morse.valu.utils.enums.Actions

inline fun ProductsAdapter.withAction(noinline action: (Actions, Product) -> Unit): ProductsAdapter {
    this.clickListener = action
    return this
}

class ProductsAdapter : RecyclerView.Adapter<ProductsAdapter.ProductViewHolder>() {

    @PublishedApi
    internal val data: ArrayList<Product> = ArrayList()

    lateinit var clickListener: (Actions, Product) -> Unit

    fun submit(newData: List<Product>) {
        data.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            ProductItemLayoutBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.renderProduct(data[position])
    }

    override fun getItemCount(): Int {
        return data.count()
    }

    inner class ProductViewHolder(private val binding: ProductItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun renderProduct(product: Product) {
            binding.render(product, clickListener)
        }

    }

}