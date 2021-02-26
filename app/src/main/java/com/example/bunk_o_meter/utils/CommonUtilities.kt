package com.example.bunk_o_meter.utils

import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import java.lang.ref.WeakReference

object CommonUtilities {
    fun showToast(context:Context,message:String){
        Toast.makeText(WeakReference(context).get(),
            message,
            Toast.LENGTH_SHORT).show()
    }
    fun checkLocationPermission(context: Context):Boolean{
        if (ContextCompat.checkSelfPermission(context,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(context,
                android.Manifest.permission.ACCESS_BACKGROUND_LOCATION) ==
                PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(context,
                android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED){
            return true
        }
        return false
    }

}