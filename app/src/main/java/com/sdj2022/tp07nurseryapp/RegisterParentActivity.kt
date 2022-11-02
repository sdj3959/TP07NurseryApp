package com.sdj2022.tp07nurseryapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CalendarView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.sdj2022.tp07nurseryapp.databinding.ActivityRegisterParentBinding
import java.text.SimpleDateFormat
import java.util.*

class RegisterParentActivity : AppCompatActivity() {

    val binding by lazy{ActivityRegisterParentBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_new_brand_24)


        binding.date.text = SimpleDateFormat("yyyy.MM.dd").format(Date().time)


        binding.btnComplete.setOnClickListener {
            finish()
            var intent = Intent(this, AccountActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
        binding.date.setOnClickListener { showBottomSheetDialogCalendar() }
    }

    fun showBottomSheetDialogCalendar(){
        val bsd = BottomSheetDialog(this)
        bsd.setContentView(R.layout.bsd_calendar)
        bsd.show()

        val calendar = bsd.findViewById<CalendarView>(R.id.bsd_calendar)
        calendar?.date = Date().time
        calendar?.setOnDateChangeListener { p0, p1, p2, p3 ->
            val calendar2 = GregorianCalendar(p1, p2, p3)
            val date = SimpleDateFormat("yyyy.MM.dd").format(calendar2.time)
            binding.date.text = date
            bsd.dismiss()
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}