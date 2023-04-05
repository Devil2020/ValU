package com.morse.valu.ui.detail

import androidx.databinding.DataBindingUtil
import com.example.halanchallenge.utils.base.BaseActivity
import com.example.halanchallenge.utils.extensions.render
import com.morse.valu.R
import com.morse.valu.app.ProductDetailDirection
import com.morse.valu.data.dto.Product
import com.morse.valu.databinding.ActivityProductDetailsBinding
import com.morse.valu.fakeProducts

class ProductDetailsActivity : BaseActivity<ActivityProductDetailsBinding>() {

    val product: Product by lazy {
        intent.getParcelableExtra(ProductDetailDirection.argsKey) ?: fakeProducts.first()
    }

    override fun bindDataBinding(): ActivityProductDetailsBinding =
        DataBindingUtil.setContentView(this, R.layout.activity_product_details)

    override fun onStart() {
        super.onStart()
        binding.render(product) { finish() }
    }

}