package com.sdj2022.tp07nurseryapp

import android.app.ProgressDialog
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
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.sdj2022.tp07nurseryapp.databinding.ActivityRegisterDirectorBinding
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStreamReader
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

class RegisterDirectorActivity : AppCompatActivity() {

    val binding by lazy { ActivityRegisterDirectorBinding.inflate(layoutInflater) }

    lateinit var imgUri: Uri
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
    val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_new_brand_24)

        binding.btnEmail.setOnClickListener {

            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)

            if (binding.etEmail.text.length<6 || !binding.etEmail.text.contains("@")) {
                Toast.makeText(this, "????????? ????????? ???????????????", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            firebaseFirestore.collection("account").whereEqualTo("email", binding.etEmail.text.toString()).get().addOnSuccessListener {
                if(it.documents.size == 0) {
                    Toast.makeText(this, "????????? ???????????? ????????????", Toast.LENGTH_SHORT).show()
                    binding.btnEmail.visibility = View.INVISIBLE
                    binding.ivEmail.visibility = View.VISIBLE
                }
                else Toast.makeText(this, "????????? ???????????? ???????????????", Toast.LENGTH_SHORT).show()
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


        binding.etPw2.addTextChangedListener(object : TextWatcher {
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
            dialog.setMessage("???????????? ???...")
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
            dialog.setCancelable(false)
            dialog.show()



            var timestamp = SimpleDateFormat("yyyyMMddHHmmss").format(Date())

            var fileName = "IMG_$timestamp.png"

            if(!binding.spinnerNursery.selectedItem.equals("??????????????? ???????????????") && !binding.etEmail.text.toString().equals("") && !binding.etPw.text.toString().equals("") && binding.etPw.text.toString().length>=6 && binding.etPw2.text.toString().equals(binding.etPw.text.toString()) && !binding.etName.text.toString().equals("") && binding.spinnerNursery.isVisible){



                auth.createUserWithEmailAndPassword(binding.etEmail.text.toString(), binding.etPw.text.toString()).addOnCompleteListener(this){
                    if(it.isSuccessful){



                        val imgRef = firebaseStorage.reference.child("profile").child(fileName)
                        imgRef.putFile(imgUri).addOnSuccessListener {
                            imgRef.downloadUrl.addOnSuccessListener {

                                if(imgUri!=Uri.parse("drawable://" + resources.getResourcePackageName(R.drawable.user) + '/' + resources.getResourceTypeName(R.drawable.user) + '/' + resources.getResourceEntryName(R.drawable.user))) imgUrl = it.toString()
                                else imgUrl = "https://firebasestorage.googleapis.com/v0/b/tp07nurseryapp.appspot.com/o/profile%2FIMG_20221108102257.png?alt=media&token=4cdc1a0a-fda7-4496-989b-a583ea332842"

                                var email = binding.etEmail.text.toString()
                                var pw = binding.etPw.text.toString()
                                var name = binding.etName.text.toString()
                                var birth = null
                                var nursery = binding.spinnerNursery.selectedItem.toString()

                                var account = GAccount(email, pw, nursery, name, birth, imgUrl,"2", nurseryAddr, nurseryTel)


                                val accountRef = firebaseFirestore.collection("account")
                                accountRef.document(email).set(account)

                                Toast.makeText(this, "???????????? ??????!", Toast.LENGTH_SHORT).show()

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
                            var birth = null
                            var nursery = binding.spinnerNursery.selectedItem.toString()

                            var account = GAccount(email, pw, nursery, name, birth, imgUrl,"2", nurseryAddr, nurseryTel)


                            val accountRef = firebaseFirestore.collection("account")
                            accountRef.document(email).set(account)

                            Toast.makeText(this, "???????????? ??????!", Toast.LENGTH_SHORT).show()

                            finish()
                            var intent = Intent(this, AccountActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                        }

                    }else{
                        dialog.dismiss()
                        Toast.makeText(this, "????????? ?????? ?????? ????????? ????????????.", Toast.LENGTH_SHORT).show()
                        binding.btnComplete.isEnabled = true
                    }
                }

            }else{
                dialog.dismiss()
                Toast.makeText(this, "??????????????? ?????? ???????????????.", Toast.LENGTH_SHORT).show()
                binding.btnComplete.isEnabled = true
            }
        }//btnComplete onClick

        binding.profile.setOnClickListener{ clickImg() }


        // ???????????????????????? API ????????????
        binding.btnSearch.setOnClickListener {

            nurseryList.clear()
            nurseryList.add("??????????????? ???????????????")

            title.clear()
            addr.clear()
            tel.clear()

            val arcode = hashMapOf<String, Int>(
                "?????????" to 11110,
                "??????" to 11140,
                "?????????" to 11170,
                "?????????" to 11200,
                "?????????" to 11215,
                "????????????" to 11230,
                "?????????" to 11260,
                "?????????" to 11290,
                "?????????" to 11305,
                "?????????" to 11320,
                "?????????" to 11350,
                "?????????" to 11380,
                "????????????" to 11410,
                "?????????" to 11440,
                "?????????" to 11470,
                "?????????" to 11500,
                "?????????" to 11530,
                "?????????" to 11545,
                "????????????" to 11560,
                "?????????" to 11590,
                "?????????" to 11620,
                "?????????" to 11650,
                "?????????" to 11680,
                "?????????" to 11710,
                "?????????" to 11740,
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
//                                    dialog.setMessage("???????????? ?????? ???...")
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
                    }//while

                    binding.spinnerNursery.onItemSelectedListener = object :
                        AdapterView.OnItemSelectedListener {
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
                        binding.spinnerNursery.prompt = "${binding.spinnerGu.selectedItem} ???????????? ??????"
                        binding.spinnerNursery.adapter = ArrayAdapter(this, R.layout.spinner_dropdown_item, nurseryList)
                    }

                }
            }.start()

        }//btnSearch onClick
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
        androidx.appcompat.app.AlertDialog.Builder(this).setTitle("????????????").setMessage("??????????????? ?????????????????????????").setPositiveButton("??????",
            DialogInterface.OnClickListener { dialogInterface, i ->
                finish()
            }).setNegativeButton("??????", DialogInterface.OnClickListener { dialogInterface, i ->  }).show()
    }
}