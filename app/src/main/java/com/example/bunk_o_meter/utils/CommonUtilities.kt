package com.example.bunk_o_meter.utils

import android.content.Context
import android.widget.Toast
import java.lang.ref.WeakReference

object CommonUtilities {
    fun showToast(context:Context,message:String){
        Toast.makeText(WeakReference(context).get(),
            message,
            Toast.LENGTH_SHORT).show()
    }
}