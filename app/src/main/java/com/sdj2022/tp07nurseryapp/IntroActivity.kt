package com.sdj2022.tp07nurseryapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.sdj2022.tp07nurseryapp.databinding.ActivityIntroBinding

class IntroActivity : AppCompatActivity() {

    private val binding: ActivityIntroBinding by lazy { ActivityIntroBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

       var pi:PackageInfo = packageManager.getPackageInfo(packageName, 0)

        binding.appVersion.text = pi.versionName

        Handler(mainLooper).postDelayed(Runnable { kotlin.run {
            var intent = Intent(this@IntroActivity, AccountActivity::class.java)
            startActivity(intent)
            finish()
        } }, 1000)
    }
}