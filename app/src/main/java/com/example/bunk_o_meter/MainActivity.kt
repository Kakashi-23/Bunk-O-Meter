package com.example.bunk_o_meter

import Fragments.AddTimeTable
import Fragments.MapsFragment
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView
    lateinit var toolbar: androidx.appcompat.widget.Toolbar
    lateinit var frameLayout: FrameLayout
    var previousMenuItem:MenuItem?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar=findViewById(R.id.mainToolBar)
        navigationView=findViewById(R.id.mainNavigationView)
        frameLayout=findViewById(R.id.mainFrameLayout)
        drawerLayout=findViewById(R.id.mainDrawerLayout)
        ToolBar()
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

                    drawerLayout.closeDrawers()
                }
                R.id.menuLHL->{
                    val manager=supportFragmentManager
                    val transaction=manager.beginTransaction()
                    val fragment=MapsFragment()
                    transaction.replace(R.id.mainFrameLayout,fragment)
                    transaction.commit()
                    toolbar.visibility=View.GONE
                    drawerLayout.closeDrawers()
                }
                R.id.menuTimeTable->{

                    val manager=supportFragmentManager
                    val transaction=manager.beginTransaction()
                    val fragment=AddTimeTable()
                    transaction.replace(R.id.mainFrameLayout,fragment)
                    transaction.commit()
                    toolbar.title="Time table"
                    drawerLayout.closeDrawers()
                }
                R.id.menucalender->{}



            }
            return@setNavigationItemSelectedListener true
        }


    }


// toolbar setup
    fun ToolBar(){
        setSupportActionBar(toolbar)
    supportActionBar?.title="home"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId
        if (id ==android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        return super.onOptionsItemSelected(item)
    }
}
