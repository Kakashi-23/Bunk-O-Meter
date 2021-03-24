package com.example.bunk_o_meter.utils

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.LatLng
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

    fun checkUser(activity:Activity):Boolean{
        val sharedPreferences = activity.getSharedPreferences("User",Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("FirstTime",false)
    }

    fun setUser(activity: Activity){
        val sharedPref = activity.getSharedPreferences("User",Context.MODE_PRIVATE).edit()
        sharedPref.putBoolean("FirstTime",false)
        sharedPref.apply()
    }

    fun setUserLocation(activity: Activity,location:LatLng){
        val sharedPref = activity.getSharedPreferences("User",Context.MODE_PRIVATE).edit()
        sharedPref.putString("Location",location.toString())
        sharedPref.apply()
    }
}