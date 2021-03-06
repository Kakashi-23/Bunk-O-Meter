package com.example.bunk_o_meter


import com.example.bunk_o_meter.fragments.MapsFragment
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import com.example.bunk_o_meter.fragments.HomeFragment
import com.example.bunk_o_meter.fragments.TimeTableFragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView
    lateinit var toolbar:MaterialToolbar
    lateinit var frameLayout: FrameLayout
    var previousMenuItem:MenuItem?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar=findViewById(R.id.mainToolBar)
        navigationView=findViewById(R.id.mainNavigationView)
        frameLayout=findViewById(R.id.mainFrameLayout)
        drawerLayout=findViewById(R.id.mainDrawerLayout)
        setHome()
        // navigation bar setup
        val actionBarDrawerToggle=ActionBarDrawerToggle(this,drawerLayout,R.string.open_Drawers,R.string.close_Drawers)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        // menu item check and function setup
        navigationView.setNavigationItemSelectedListener {
            if (previousMenuItem!=null) {
                previousMenuItem?.isChecked = false
            }
            it.isCheckable=true
            it.isChecked=true
            previousMenuItem=it
            when(it.itemId) {
                R.id.menuHome->{
                    val manager=supportFragmentManager
                    val transaction=manager.beginTransaction()
                    val fragment=HomeFragment()
                    transaction.replace(R.id.mainFrameLayout,fragment)
                    transaction.commit()
                    toolBar("Home")
                    drawerLayout.closeDrawers()
                }
                R.id.menuLHL->{
                    val manager=supportFragmentManager
                    val transaction=manager.beginTransaction()
                    val fragment=MapsFragment()
                    transaction.replace(R.id.mainFrameLayout,fragment)
                    transaction.commit()
             //       toolbar.visibility=View.GONE
                    toolBar("Add Location")
                    drawerLayout.closeDrawers()
                }
                R.id.menuTimeTable->{
                    val manager =supportFragmentManager
                    val transaction=manager.beginTransaction()
                    val fragment=TimeTableFragment()
                    transaction.replace(R.id.mainFrameLayout,fragment)
                    transaction.commit()
                    toolBar("Add TimeTable")
                    drawerLayout.closeDrawers()

                }
                R.id.menucalender->{}



            }
            return@setNavigationItemSelectedListener true
        }


    }


// toolbar setup
    private fun toolBar(title:String){
        setSupportActionBar(toolbar)
        supportActionBar?.title=title
    supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId
        if (id ==android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        return super.onOptionsItemSelected(item)
    }
    private fun setHome(){
        val manager=supportFragmentManager
        val transaction=manager.beginTransaction()
        val fragment=HomeFragment()
        transaction.replace(R.id.mainFrameLayout,fragment)
        transaction.commit()
        toolBar("Home")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
