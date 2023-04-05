package com.example.halanchallenge.utils.extensions

import android.content.Context
import android.os.Build
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.Interpolator
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.morse.valu.data.dto.Product
import com.morse.valu.databinding.ActivityProductDetailsBinding
import com.morse.valu.databinding.ProductItemLayoutBinding
import com.morse.valu.utils.enums.Actions
import de.hdodenhof.circleimageview.CircleImageView

fun Context.isArabic() = getResources().getConfiguration().locale.language.lowercase().equals("ar")

fun AppCompatImageView.loadImage(url: String, cornerRadius: Int) {
    Glide.with(context).load(url).transform(RoundedCorners(cornerRadius)).into(this)
}

fun CircleImageView.loadImage(url: String) {
    Glide.with(context).load(url).into(this)
}

fun RecyclerView.animateExtendedFab(extendedFab: ExtendedFloatingActionButton?) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY > oldScrollY) {
                extendedFab?.shrink()
            } else {
                extendedFab?.extend()
            }
        }
    }
}

fun View?.animateView(animationRes: Int): View? {
    this?.animation = AnimationUtils.loadAnimation(this?.context, animationRes)
    return this
}

fun View?.setUpListener(listerner: Animation.AnimationListener? = null): View? {
    this?.animation?.setAnimationListener(listerner)
    return this
}

fun View?.setInterpolator(inter: Interpolator? = null): View? {
    this?.animation?.interpolator = inter
    return this
}

fun View?.run() = this?.animation?.startNow()

fun View.isVisible() = this.visibility == View.VISIBLE

fun View.isGone() = this.visibility == View.GONE

fun View.visible() {
    if (isGone()) {
        this.visibility = View.VISIBLE
    }
}

fun View.gone() {
    if (isVisible()) {
        this.visibility = View.GONE
    }
}

fun ProductItemLayoutBinding.render(product: Product, onClickInvoke: (Actions, Product) -> Unit) {
    apply {
        with(product) {
            root.setOnClickListener{
                onClickInvoke.invoke(Actions.ProductClick , product)
            }
            FavouriteIcon.setOnClickListener {
                onClickInvoke.invoke(Actions.FavouriteClick , product)
            }
            RateValue.text = rating.rate.toString()
            PriceValue.text = getPrice()
            ProductName.text = title
            ProductImage.loadImage(image,50)
        }
    }
}

fun ActivityProductDetailsBinding.render (product : Product, onBack : () -> Unit){
    apply {
        with(product){
            DetailToolbar.setNavigationOnClickListener {
                onBack.invoke()
            }
            NameValue.text = title
            DetailsBody.text = description
            CategoryChip.text = category
            PriceValue.text = price.toString()
            RateValue.text = rating.rate.toString()
            ProductImage.loadImage(image , 100)
        }
    }
}