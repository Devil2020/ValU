package com.expertapps.base.dialog

import android.app.ProgressDialog
import androidx.fragment.app.FragmentActivity
import java.lang.ref.WeakReference


class Loader constructor(){
    companion object {
        internal var progressDialog: ProgressDialog? = null
    }

    fun show(context: WeakReference<FragmentActivity?>, message: String? = null) {
        context.get()?.let {
            if (progressDialog != null && progressDialog!!.isShowing) {
                if (message.isNullOrEmpty()) {
                    return
                } else {
                    progressDialog!!.setMessage(message)
                    return
                }
            }
            progressDialog = if (message.isNullOrEmpty()) {
                ProgressDialog.show(
                    it, "", "Loading", // todo get from xml (AR _EN)
                    false, true, null
                )
            } else {
                ProgressDialog.show(
                    it, "", message,
                    false, false, null
                )
            }
        } ?: hide()
    }

     fun hide() {
        try {
            if (progressDialog != null && progressDialog!!.isShowing)
                progressDialog!!.dismiss()

            progressDialog = null
        } catch (e: Exception) {

        }

    }
}