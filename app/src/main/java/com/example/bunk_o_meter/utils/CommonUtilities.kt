package com.example.bunk_o_meter.utils

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.lang.ref.WeakReference
import java.security.Permissions
import java.util.jar.Manifest

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
                PackageManager.PERMISSION_GRANTED){
            return true
        }
        return false
    }

    fun getLocationPermission(activity: Activity){
        ActivityCompat.requestPermissions(activity,
        arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),Constants.locationPermission)
    }

}