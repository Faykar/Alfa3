package com.bagicode.alfa3.user.log.register

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bagicode.alfa3.R
import com.bagicode.alfa3.user.home.HomeActivity
import com.bagicode.alfa3.user.log.login.LoginActivity
import com.bagicode.alfa3.user.log.login.User
import com.bagicode.alfa3.utils.Preferences
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.github.dhaval2404.imagepicker.ImagePicker
import com.github.dhaval2404.imagepicker.constant.ImageProvider
import com.google.firebase.database.*

import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.activity_register_photoscreen.*
import kotlinx.android.synthetic.main.activity_register_photoscreen.btn_save

import java.util.*


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class RegisterPhotoscreenActivity : AppCompatActivity(), PermissionListener {


    var statusAdd: Boolean = false
    lateinit var filePath: Uri
    lateinit var user : User

    lateinit var storage: FirebaseStorage
    lateinit var storageReference: StorageReference
    lateinit var  preferences: Preferences
    private lateinit var mFirebaseDatabase: DatabaseReference
    private lateinit var mFirebaseInstance: FirebaseDatabase



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_photoscreen)

        mFirebaseInstance = FirebaseDatabase.getInstance()
        mFirebaseDatabase = mFirebaseInstance.getReference("User")



        preferences = Preferences(this)
        storage = FirebaseStorage.getInstance()
        storageReference = storage.getReference("User")

        user = intent.getParcelableExtra("data")
        tv_hello.text = "Selamat Datang\n"+user.nama

        iv_add.setOnClickListener {
            if (statusAdd) {
                statusAdd = false
                btn_save.visibility = View.INVISIBLE

                iv_add.setImageResource(R.drawable.ic_btn_plus)

                iv_profile.setImageResource(R.drawable.user)
            } else {
                Dexter.withActivity(this)
                    .withPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    .withListener(this)
                    .check()
            }
        }

        btn_later.setOnClickListener{
            finishAffinity()

            val intent = Intent(this@RegisterPhotoscreenActivity,
            LoginActivity::class.java)
            startActivity(intent)
        }

        btn_save.setOnClickListener {
            if (filePath != null){
                val progressDialog = ProgressDialog(this)
                progressDialog.setTitle("Uploading..")
                progressDialog.show()

                Log.v("tamvan", "file uri upload 2" + filePath)

                val ref = storageReference.child("images/"+UUID.randomUUID().toString())
                ref.putFile(filePath)
                    .addOnSuccessListener {
                        progressDialog.dismiss()
                        Toast.makeText(this@RegisterPhotoscreenActivity,"Uploaded",
                            Toast.LENGTH_SHORT).show()


                        ref.downloadUrl.addOnSuccessListener {
                            updateData(it. toString())
                        }
                        // Update Data, FayFay Version
//                            val hashMap: HashMap<String, String> = HashMap()
//                            hashMap.put("url", it.toString())
//
//                            mFirebaseDatabase.updateChildren(hashMap as Map<String, Any>)
//
//
//
//                            Log.v("dapat", "url" + it.toString())



//                        finishAffinity()
//                        val intent = Intent (this@RegisterPhotoscreenActivity,
//                            HomeActivity::class.java).putExtra("user", intent.getStringExtra("user"))
//                        startActivity(intent)

                    }
                    .addOnFailureListener { e ->
                        progressDialog.dismiss()
                        Toast.makeText(this@RegisterPhotoscreenActivity,
                            "Failed"+ e.message, Toast.LENGTH_SHORT).show()
                    }
                    .addOnProgressListener { taskSnapshot ->
                        val progress = 100.0 * taskSnapshot.bytesTransferred / taskSnapshot
                            .totalByteCount
                        progressDialog.setMessage("Uploaded " + progress.toInt() + "%")
                    }

            }

        }
    }


    private fun updateData(url: String) {

        mFirebaseDatabase.child(user.username!!).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                user.url = url
                mFirebaseDatabase.child(user.username!!).setValue(user)

                preferences.setValues("nama", user.nama.toString())
                preferences.setValues("user", user.username.toString())
                preferences.setValues("nomor", user.nomor.toString())
                preferences.setValues("url", url)
                preferences.setValues("password", user.password.toString())
                preferences.setValues("status", "1")


                finishAffinity()
                val intent = Intent(this@RegisterPhotoscreenActivity,
                    LoginActivity::class.java)
                startActivity(intent)


            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@RegisterPhotoscreenActivity, ""+error.message, Toast.LENGTH_LONG).show()
            }
        })


    }



    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
//        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
//            takePictureIntent.resolveActivity(packageManager)?.also {
//                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
//            }
//        }
        // Use Gallery and Camera
        ImagePicker.with(this)
            .provider(ImageProvider.BOTH)
            .start()
    }

    override fun onPermissionRationaleShouldBeShown(
        permission: com.karumi.dexter.listener.PermissionRequest?,
        token: PermissionToken?
    ) {
        //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
        //To change body of created functions use File | Settings | File Templates.
        Toast.makeText(this, "Anda tidak bisa menambahkan photo profile", Toast.LENGTH_LONG ).show()
    }


    override fun onBackPressed() {
        Toast.makeText(this, "Tergesa? Klik tombol Upload Nanti aja", Toast.LENGTH_LONG).show()
    }




    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            statusAdd = true
            filePath = data?.data!!

            Glide.with(this)
                .load(filePath)
                .apply(RequestOptions.circleCropTransform())
                .into(iv_profile)


            btn_save.visibility = View.VISIBLE

            iv_add.setImageResource(R.drawable.ic_btn_delete)
        } else if (resultCode == ImagePicker.RESULT_ERROR){
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }
}

