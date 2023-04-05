package com.morse.valu.ui.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.halanchallenge.utils.base.BaseActivity
import com.expertapps.base.dialog.Loader
import com.expertapps.base.extensions.toast
import com.mohammedmorse.utils.extensions.collect
import com.morse.valu.R
import com.morse.valu.app.ProductDetailDirection
import com.morse.valu.app.ValUCoordinator
import com.morse.valu.data.dto.Product
import com.morse.valu.data.dto.Response
import com.morse.valu.databinding.ActivityProductsListBinding
import com.morse.valu.fakeProducts
import com.morse.valu.utils.enums.Actions
import java.lang.ref.WeakReference

class ProductsListActivity : BaseActivity<ActivityProductsListBinding>() {
    private val vm : ProductsViewModel by lazy {
        ViewModelProvider(this)[ProductsViewModel::class.java]
    }
    private val adapter: ProductsAdapter by lazy {
        ProductsAdapter()
            .withAction { actions, product ->
                when (actions) {
                    Actions.ProductClick -> ValUCoordinator.navigate(
                        ProductDetailDirection(
                            this,
                            product
                        )
                    )
                    else -> "Not Implemented Yet".toast(this)
                }
            }
    }

    override fun bindDataBinding(): ActivityProductsListBinding =
        DataBindingUtil.setContentView<ActivityProductsListBinding?>(
            this,
            R.layout.activity_products_list
        ).apply {
            ProductsRecyclerView.adapter = adapter
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(vm){
            loader.show(WeakReference(this@ProductsListActivity) , "Loading")
            loadProducts()
        }
    }



    override fun onStart() {
        super.onStart()

        collect(vm.products){
            when(it){
                is Response.Success<*> -> {
                    adapter.submit(it.response as ArrayList<Product>)
                }
                is Response.Error -> {
                    it.reason.toast(this)
                }
            }
            loader.hide()
        }
    }

}