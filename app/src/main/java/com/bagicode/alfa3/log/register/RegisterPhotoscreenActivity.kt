package com.bagicode.alfa3.log.register

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bagicode.alfa3.R
import com.bagicode.alfa3.home.HomeActivity
import com.bagicode.alfa3.log.login.User
import com.bagicode.alfa3.utils.Preferences
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.database.DataSnapshot

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.activity_register_photoscreen.*
import kotlinx.android.synthetic.main.activity_register_photoscreen.btn_save
import kotlinx.android.synthetic.main.activity_riwayat.*

import java.util.*

import kotlin.collections.HashMap


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class RegisterPhotoscreenActivity : AppCompatActivity(), PermissionListener {


    var statusAdd: Boolean = false
    lateinit var filePath: Uri

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
            .child(intent.getStringExtra("user"))


        preferences = Preferences(this)
        storage = FirebaseStorage.getInstance()
        storageReference = storage.getReference("User")
        tv_hello.text = "Selamat Datang\n"+intent.getStringExtra("nama")

        iv_add.setOnClickListener {
            if (statusAdd) {
                statusAdd = false
                btn_save.visibility = View.INVISIBLE

                iv_add.setImageResource(R.drawable.ic_btn_plus)
                iv_profile.setImageResource(R.drawable.user)
            } else {
                Dexter.withActivity(this)
                    .withPermission(android.Manifest.permission.CAMERA)
                    .withListener(this)
                    .check()

            }
        }

        btn_home.setOnClickListener{
            finishAffinity()

            val intent = Intent(this@RegisterPhotoscreenActivity,
            HomeActivity::class.java)
            startActivity(intent)
        }

        btn_save.setOnClickListener {
            updateData()
        }
    }


  fun updateData(){
      if (filePath != null){
          val progressDialog = ProgressDialog(this)
          progressDialog.setTitle("Uploading..")
          progressDialog.show()

          Log.v("tamvan", "file uri upload 2" + filePath)

          val ref = storageReference.child("images/"+UUID.randomUUID().toString())
//                val getRef = preferences.getValues(ref.toString())
//                preferences.setValues("url", getRef.toString())
//
//                Log.v("tamvan", "url" + getRef.toString())


          ref.putFile(filePath)
              .addOnSuccessListener {
                  progressDialog.dismiss()
                  Toast.makeText(this@RegisterPhotoscreenActivity,"Uploaded",
                      Toast.LENGTH_SHORT).show()


                  val getImg = ref.downloadUrl.addOnSuccessListener {
                      val hashMap: HashMap<String, String> = HashMap()
                      hashMap.put("url", it.toString())

                        mFirebaseDatabase.updateChildren(hashMap as Map<String, Any>)



                      Log.v("dapat", "url" + it.toString())

                  }
//                        val data = mFirebaseInstance.getReference("user").child("url")



                  finishAffinity()
                  val intent = Intent (this@RegisterPhotoscreenActivity,
                      HomeActivity::class.java)
                  startActivity(intent)

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



    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
//        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
//            takePictureIntent.resolveActivity(packageManager)?.also {
//                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
//            }
//        }

        ImagePicker.with(this)
            .galleryOnly()
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

