package com.sdj2022.tp07nurseryapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity() {

    var fragments = mutableListOf<Fragment?>()
    val fragmentManager by lazy { supportFragmentManager }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragments.add(HomeFragment())
        fragments.add(null)
        fragments.add(null)

        fragmentManager.beginTransaction().add(R.id.fragment_container, fragments[0]!!).commit()

        var bnv = findViewById<BottomNavigationView>(R.id.bnv)
        bnv.setOnItemSelectedListener { item ->
            var tran = fragmentManager.beginTransaction()

            if (fragments[0] != null) tran.hide(fragments[0]!!)
            if (fragments[1] != null) tran.hide(fragments[1]!!)
            if (fragments[2] != null) tran.hide(fragments[2]!!)

            when (item.itemId) {
                R.id.bnv_menu_home -> tran.show(fragments[0]!!)

                R.id.bnv_menu_trip -> {
                    if(fragments[1] == null){
                        fragments[1] = TripFragment()
                        tran.add(R.id.fragment_container, fragments[1]!!)

                    }

                    tran.show(fragments[1]!!)
                }

                R.id.bnv_menu_more -> {
                    if(fragments[2] == null){
                        fragments[2] = MoreFragment()
                        tran.add(R.id.fragment_container, fragments[2]!!)

                    }

                    tran.show(fragments[2]!!)
                }
            }

            tran.commit()

            true
        }
    }
}