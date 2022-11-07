package com.sdj2022.tp07nurseryapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.CalendarView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.sdj2022.tp07nurseryapp.databinding.ActivityRegisterParentBinding
import java.text.SimpleDateFormat
import java.util.*

class RegisterParentActivity : AppCompatActivity() {

    val binding by lazy{ActivityRegisterParentBinding.inflate(layoutInflater)}
    lateinit var imgUri:Uri

    val firebaseFirestore = FirebaseFirestore.getInstance()
    lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_new_brand_24)


        binding.date.text = SimpleDateFormat("yyyy.MM.dd").format(Date().time)

        binding.etPw2.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(binding.etPw2.text.toString().equals(binding.etPw.text.toString())){
                    binding.tvPwCheckCorrect.visibility = View.VISIBLE
                    binding.tvPwCheckWrong.visibility = View.INVISIBLE
                }else{
                    binding.tvPwCheckCorrect.visibility = View.INVISIBLE
                    binding.tvPwCheckWrong.visibility = View.VISIBLE
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })



        binding.btnComplete.setOnClickListener {

            auth = FirebaseAuth.getInstance()

            if(!binding.etEmail.text.toString().equals(null) && !binding.etPw.text.toString().equals(null) && binding.etPw.text.toString().length>=6 && binding.etPw2.text.toString().equals(binding.etPw.text.toString()) && !binding.etName.text.toString().equals(null)){

                auth.createUserWithEmailAndPassword(binding.etEmail.text.toString(), binding.etPw.text.toString()).addOnCompleteListener(this){
                    if(it.isSuccessful){

                        Toast.makeText(this, "회원가입 완료!", Toast.LENGTH_SHORT).show()

                        var email = binding.etEmail.text.toString()
                        var pw = binding.etPw.text.toString()
                        var name = binding.etName.text.toString()
                        var birth = binding.date.text.toString()
                        var nursery = binding.spinnerNursery.selectedItem.toString()
                        var account = GAccount(email, pw, nursery, name, birth, null)



                        var timestamp = SimpleDateFormat("yyyyMMddHHmmss").format(Date())

                        val accountRef = firebaseFirestore.collection("account")
                        accountRef.document(timestamp).set(account)

                        finish()
                        var intent = Intent(this, AccountActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)

                    }else{
                        Toast.makeText(this, "이메일 중복 또는 형식이 다릅니다.", Toast.LENGTH_SHORT).show()
                    }
                }

            }else{
                Toast.makeText(this, "회원정보를 다시 확인하세요.", Toast.LENGTH_SHORT).show()
            }
        }//btnComplete onClick
        
        binding.profile.setOnClickListener{ clickImg() }
        binding.date.setOnClickListener { showBottomSheetDialogCalendar() }

    }//onCreate



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
    
    fun clickImg(){
        activityResultLauncher.launch(Intent(Intent.ACTION_PICK).setType("image/*"))
    }
    
    var activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult(), ActivityResultCallback {
        if(it.resultCode == RESULT_CANCELED) return@ActivityResultCallback
        imgUri = it.data?.data!!
        Glide.with(this).load(imgUri).into(binding.profile)
    })

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}