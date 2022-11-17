package com.sdj2022.tp07nurseryapp

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.ContentResolver
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.CalendarView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.sdj2022.tp07nurseryapp.databinding.ActivityRegisterParentBinding
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStreamReader
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class RegisterParentActivity : AppCompatActivity() {

    val binding by lazy{ActivityRegisterParentBinding.inflate(layoutInflater)}

    lateinit var imgUri:Uri
    var imgUrl:String = "https://firebasestorage.googleapis.com/v0/b/tp07nurseryapp.appspot.com/o/profile%2FIMG_20221108102257.png?alt=media&token=4cdc1a0a-fda7-4496-989b-a583ea332842"


    var nurseryList = mutableListOf<String>()

    var title = mutableListOf<String>()
    var addr = mutableListOf<String>()
    var tel = mutableListOf<String>()

    lateinit var nurseryTitle:String
    lateinit var nurseryAddr:String
    lateinit var nurseryTel:String

    val firebaseFirestore = FirebaseFirestore.getInstance()
    val firebaseStorage = FirebaseStorage.getInstance()
    var auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_new_brand_24)


        binding.date.text = SimpleDateFormat("yyyy.MM.dd").format(Date().time)

        binding.btnEmail.setOnClickListener {

            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)

            if (binding.etEmail.text.length<6 || !binding.etEmail.text.contains("@")) {
                Toast.makeText(this, "이메일 형식을 맞춰주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            firebaseFirestore.collection("account").whereEqualTo("email", binding.etEmail.text.toString()).get().addOnSuccessListener {
                if(it.documents.size == 0) {
                    Toast.makeText(this, "중복된 이메일이 없습니다", Toast.LENGTH_SHORT).show()
                    binding.btnEmail.visibility = View.INVISIBLE
                    binding.ivEmail.visibility = View.VISIBLE
                }
                else Toast.makeText(this, "중복된 이메일이 존재합니다", Toast.LENGTH_SHORT).show()
            }
        }

        binding.etEmail.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(binding.btnEmail.visibility == View.INVISIBLE && binding.ivEmail.visibility == View.VISIBLE){
                    binding.btnEmail.visibility = View.VISIBLE
                    binding.ivEmail.visibility = View.INVISIBLE
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })

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


        imgUri = Uri.parse("drawable://" + resources.getResourcePackageName(R.drawable.user) + '/' + resources.getResourceTypeName(R.drawable.user) + '/' + resources.getResourceEntryName(R.drawable.user))

        binding.btnComplete.setOnClickListener {

            binding.btnComplete.isEnabled = false

            var dialog = ProgressDialog(this)
            dialog.setMessage("회원가입 중...")
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
            dialog.setCancelable(false)
            dialog.show()

            var timestamp = SimpleDateFormat("yyyyMMddHHmmss").format(Date())
            var fileName = "IMG_$timestamp.png"


            if(!binding.spinnerNursery.selectedItem.equals("어린이집을 선택하세요") && !binding.etEmail.text.toString().equals("") && !binding.etPw.text.toString().equals("") && binding.etPw.text.toString().length>=6 && binding.etPw2.text.toString().equals(binding.etPw.text.toString()) && !binding.etName.text.equals("") && binding.spinnerNursery.isVisible){

                auth.createUserWithEmailAndPassword(binding.etEmail.text.toString(), binding.etPw.text.toString()).addOnCompleteListener(this){
                    if(it.isSuccessful){

                        val imgRef = firebaseStorage.reference.child("profile").child(fileName)

                        imgRef.putFile(imgUri).addOnSuccessListener {
                            imgRef.downloadUrl.addOnSuccessListener {

                                imgUrl = it.toString()

                                var email = binding.etEmail.text.toString()
                                var pw = binding.etPw.text.toString()
                                var name = binding.etName.text.toString()
                                var birth = binding.date.text.toString()
                                var nursery = binding.spinnerNursery.selectedItem.toString()

                                var account = GAccount(email, pw, nursery, name, birth, imgUrl,"0", nurseryAddr, nurseryTel)


                                val accountRef = firebaseFirestore.collection("account")
                                accountRef.document(email).set(account)

                                Toast.makeText(this, "회원가입 완료!", Toast.LENGTH_SHORT).show()

                                finish()
                                var intent = Intent(this, AccountActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(intent)
                            }
                        }.addOnFailureListener {
                            imgUrl = "https://firebasestorage.googleapis.com/v0/b/tp07nurseryapp.appspot.com/o/profile%2FIMG_20221108102257.png?alt=media&token=4cdc1a0a-fda7-4496-989b-a583ea332842"

                            var email = binding.etEmail.text.toString()
                            var pw = binding.etPw.text.toString()
                            var name = binding.etName.text.toString()
                            var birth = binding.date.text.toString()
                            var nursery = binding.spinnerNursery.selectedItem.toString()

                            var account = GAccount(email, pw, nursery, name, birth, imgUrl,"0", nurseryAddr, nurseryTel)


                            val accountRef = firebaseFirestore.collection("account")
                            accountRef.document(email).set(account)

                            Toast.makeText(this, "회원가입 완료!", Toast.LENGTH_SHORT).show()

                            finish()
                            var intent = Intent(this, AccountActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                        }

                    }else{
                        dialog.dismiss()
                        Toast.makeText(this, "이메일 중복 또는 형식이 다릅니다.", Toast.LENGTH_SHORT).show()
                        binding.btnComplete.isEnabled = true
                    }
                }

            }else{
                dialog.dismiss()
                Toast.makeText(this, "회원정보를 다시 확인하세요.", Toast.LENGTH_SHORT).show()
                binding.btnComplete.isEnabled = true
            }
        }//btnComplete onClick
        
        binding.profile.setOnClickListener{ clickImg() }
        binding.date.setOnClickListener { showBottomSheetDialogCalendar() }


        // 전국어린이집정보 API 조회버튼
        binding.btnSearch.setOnClickListener {

            nurseryList.clear()
            nurseryList.add("어린이집을 선택하세요")

            title.clear()
            addr.clear()
            tel.clear()

            val arcode = hashMapOf<String, Int>(
                "종로구" to 11110,
                "중구" to 11140,
                "용산구" to 11170,
                "성동구" to 11200,
                "광진구" to 11215,
                "동대문구" to 11230,
                "중랑구" to 11260,
                "성북구" to 11290,
                "강북구" to 11305,
                "도봉구" to 11320,
                "노원구" to 11350,
                "은평구" to 11380,
                "서대문구" to 11410,
                "마포구" to 11440,
                "양천구" to 11470,
                "강서구" to 11500,
                "구로구" to 11530,
                "금천구" to 11545,
                "영등포구" to 11560,
                "동작구" to 11590,
                "관악구" to 11620,
                "서초구" to 11650,
                "강남구" to 11680,
                "송파구" to 11710,
                "강동구" to 11740,
            )
            val key = "80ee2448c7984068b8a6a59130f773f8"
            var address = "https://api.childcare.go.kr/mediate/rest/cpmsapi021/cpmsapi021/request?key=$key&arcode=${arcode[binding.spinnerGu.selectedItem.toString()]}"

            Thread() {
                run() {
                    val url = URL(address)
                    val ins = url.openStream()
                    val isr = InputStreamReader(ins)

                    val factory = XmlPullParserFactory.newInstance()
                    val xpp = factory.newPullParser()

                    xpp.setInput(isr)

                    var eventType = xpp.eventType

                    while (eventType != XmlPullParser.END_DOCUMENT) {

                        when (eventType) {
                            XmlPullParser.START_DOCUMENT -> {
//                                runOnUiThread() {
//                                    var dialog = ProgressDialog(this)
//                                    dialog.setMessage("어린이집 조회 중...")
//                                    dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
//                                    dialog.setCancelable(false)
//                                    dialog.show()
//                                }
                            }
                            XmlPullParser.START_TAG -> {
                                var tagName = xpp.name
                                if (tagName.equals("crname")) {
                                    xpp.next()
                                    nurseryList.add(xpp.text)
                                    title.add(xpp.text)
                                }else if(tagName.equals("craddr")){
                                    xpp.next()
                                    addr.add(xpp.text)
                                }else if(tagName.equals("crtel")){
                                    xpp.next()
                                    tel.add(xpp.text)
                                }
                            }
                            XmlPullParser.TEXT -> {}
                            XmlPullParser.END_TAG -> {}
                        }//when
                        eventType = xpp.next()
                    }//while}

                    binding.spinnerNursery.onItemSelectedListener = object : OnItemSelectedListener{
                        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                            if (p2>0){

                                //AlertDialog.Builder(this@RegisterParentActivity).setMessage("${title[p2-1]}, ${addr[p2-1]}, ${tel[p2-1]}").show()
                                nurseryTitle = title[p2-1]
                                nurseryAddr = addr[p2-1]
                                nurseryTel = tel[p2-1]
                            }

                        }

                        override fun onNothingSelected(p0: AdapterView<*>?) {
                        }
                    }

                    runOnUiThread {
                        binding.spinnerNursery.visibility = View.VISIBLE
                        binding.spinnerNursery.prompt = "${binding.spinnerGu.selectedItem} 어린이집 목록"
                        binding.spinnerNursery.adapter = ArrayAdapter(this, R.layout.spinner_dropdown_item, nurseryList)
                    }

                }
            }.start()

        }//btnSearch onClick

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

    override fun onBackPressed() {
        androidx.appcompat.app.AlertDialog.Builder(this).setTitle("유아노트").setMessage("회원가입을 그만두시겠습니까?").setPositiveButton("확인",
            DialogInterface.OnClickListener { dialogInterface, i ->
                finish()
            }).setNegativeButton("취소", DialogInterface.OnClickListener { dialogInterface, i ->  }).show()
    }
}