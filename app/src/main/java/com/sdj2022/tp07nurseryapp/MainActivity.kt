package com.sdj2022.tp07nurseryapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.navigation.NavigationView
import com.sdj2022.tp07nurseryapp.databinding.ActivityMainBinding
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    var fragments = mutableListOf<Fragment?>()
    private val fragmentManager by lazy { supportFragmentManager }

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // BottomNavigationView+Fragments
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


        // DrawerNavigation
        val drawerToggle = ActionBarDrawerToggle(this, binding.drawerLayout, binding.toolbar, R.string.drawer_open, R.string.drawer_close)
        drawerToggle.syncState()
        binding.drawerLayout.addDrawerListener(drawerToggle)
        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_menu_24)

        val tvName =binding.nav.getHeaderView(0).findViewById<TextView>(R.id.tv_name) //DrawerNavigation headerView

        binding.nav.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_logout -> {
                    val intent = Intent(this@MainActivity, AccountActivity::class.java)
                    startActivity(intent)
                    finish()
                }

                R.id.menu_nursery_info -> startActivity(Intent(this, NurseryInfoActivity::class.java))
            }
            binding.drawerLayout.closeDrawer(binding.nav)

            false
        }
    }//onCreate

    override fun onBackPressed() {
        if(binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }else finish()
    }
}//MainActivity