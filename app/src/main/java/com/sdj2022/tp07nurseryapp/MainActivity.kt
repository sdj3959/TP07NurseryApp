package com.sdj2022.tp07nurseryapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.sdj2022.tp07nurseryapp.databinding.ActivityMainBinding
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()

    var fragments = mutableListOf<Fragment?>()
    private val fragmentManager by lazy { supportFragmentManager }

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

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

        // 현재 로그인된 계정 정보 가져오기
        val user = FirebaseAuth.getInstance().currentUser

        var userEmail:String
        var userPassword:String
        var userProfile:String
        var userName:String
        var userNursery:String
        var userBirth:String
        var userPosition:String

        var userData: HashMap<String, String?>

        db.collection("account").document(user?.email!!).get().addOnSuccessListener { document->
            if(document != null) {

                userEmail = document.get("email").toString()
                userPassword = document.get("pw").toString()
                userProfile = document.get("imgUrl").toString()
                userName = document.get("name").toString()
                userNursery = document.get("nursery").toString()
                userBirth = document.get("birth").toString()
                userPosition = document.get("position").toString()

                userData = hashMapOf<String, String?>(
                    "email" to userEmail,
                    "password" to userPassword,
                    "profile" to userProfile,
                    "name" to userName,
                    "nursery" to userNursery,
                    "birth" to userBirth,
                    "position" to userPosition
                )

                GUserData.userData = userData

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
                        R.id.bnv_menu_home -> {
                            binding.toolbar.visibility = View.VISIBLE
                            tran.show(fragments[0]!!)
                        }

                        R.id.bnv_menu_trip -> {
                            if(fragments[1] == null){
                                fragments[1] = TripFragment()
                                tran.add(R.id.fragment_container, fragments[1]!!)

                            }

                            binding.toolbar.visibility = View.GONE
                            tran.show(fragments[1]!!)
                        }

                        R.id.bnv_menu_more -> {
                            if(fragments[2] == null){
                                fragments[2] = MoreFragment()
                                tran.add(R.id.fragment_container, fragments[2]!!)

                            }

                            binding.toolbar.visibility = View.VISIBLE
                            tran.show(fragments[2]!!)
                        }
                    }

                    tran.commit()

                    true
                }

                if(GUserData.userData["position"].equals("0")) tvName.text = "${GUserData.userData["name"]} 부모님"
                else if(GUserData.userData["position"].equals("1")) tvName.text = "${GUserData.userData["name"]} 교사"
                else tvName.text = "${GUserData.userData["name"]} 원장"

            }
        }
    }//onCreate

    override fun onBackPressed() {
        if(binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }else finish()
    }
}//MainActivity