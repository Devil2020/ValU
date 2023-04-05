package com.expertapps.base.extensions

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.isGone
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.platform.MaterialArcMotion
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.morse.valu.BuildConfig
import java.io.IOException

fun Activity.isOnline(context: Context): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        checkConnection(context)
    } else {
        checkPing()
    }
}


@RequiresApi(Build.VERSION_CODES.M)
private fun checkConnection(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (connectivityManager != null) {
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                return true
            }
        }
    }
    return false
}

private fun checkPing(): Boolean {
    val command = "ping -c 1 google.com"
    return try {
        Runtime.getRuntime().exec(command).waitFor() == 0
    } catch (ioException: IOException) {
        return false
    } catch (interruptedException: InterruptedException) {
        return false
    }
}


fun showSnackbar(
    activity: Activity,
    message: String,
    time: Int? = null,
    retryFunctionalityName: String? = "RETRY",
    retryFunctionality: () -> Unit
) {
    message.let {
        val containerView: View? = activity.findViewById(android.R.id.content)
        containerView?.let { contentView ->
            Snackbar.make(
                contentView,
                it,
                time ?: Snackbar.LENGTH_SHORT
            )
                .setAction(retryFunctionalityName) { retryFunctionality.invoke() }
                .show()
        }

    }
}

fun String.toast (context: Context){
    Toast.makeText(context , this , Toast.LENGTH_SHORT).show()
}

fun showLog(message: String) {
    if (BuildConfig.DEBUG) {
        Log.i("Mohammed-Morse-Logger", message)
    }
}

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
fun Activity.returnCardToOriginPosition(
    screenRoot: ViewGroup,
    cardRoot: View,
    recyclerviewItem: View,
    duration: Long
) {
    android.transition.TransitionManager.beginDelayedTransition(
        screenRoot,
        getTransform(cardRoot, recyclerviewItem, duration)
    )
    cardRoot.isGone = true
    recyclerviewItem.isGone = false
}

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
fun Activity.animateCard(screenRoot: ViewGroup, cardRoot: View, recyclerviewItem: View) {
    android.transition.TransitionManager.beginDelayedTransition(
        screenRoot,
        getTransform(recyclerviewItem, cardRoot, 650)
    )
    recyclerviewItem.isGone = true
    cardRoot.isGone = false
}

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
fun Activity.getTransform(
    mStartView: View,
    mEndView: View,
    customDuration: Long
): MaterialContainerTransform {
    return MaterialContainerTransform().apply {
        startView = mStartView
        endView = mEndView
        addTarget(mEndView)
        pathMotion = MaterialArcMotion()
        duration = customDuration
        scrimColor = Color.TRANSPARENT
    }
}

fun Activity.isCanceled(isCanceled: Boolean) {
    if (isCanceled) {
        finish()
    }
}