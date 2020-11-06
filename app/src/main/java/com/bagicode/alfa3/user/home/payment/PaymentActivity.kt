package com.bagicode.alfa3.user.home.payment

import android.app.*
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.bagicode.alfa3.R
import com.bagicode.alfa3.user.home.HomeActivity
import com.bagicode.alfa3.utils.Preferences
import com.bumptech.glide.Glide
import com.github.dhaval2404.imagepicker.ImagePicker
import com.github.dhaval2404.imagepicker.constant.ImageProvider
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.activity_payment.*
import java.util.*
import kotlin.collections.ArrayList

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS",
    "RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS"
)
class PaymentActivity : AppCompatActivity(), PermissionListener{

    lateinit var preferences : Preferences

    var statusAdd: Boolean = false
    lateinit var fileRef: Uri


    lateinit var storage: FirebaseStorage
    lateinit var storageReference: StorageReference

//    private var total: Int = 0
//    private var hargaTotal = total

    private lateinit var mFirebaseDatabase: DatabaseReference
    private lateinit var mFirebaseInstance: FirebaseDatabase
//    private var transaksi = ArrayList<Transaksi>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        preferences = Preferences(applicationContext)

        storage = FirebaseStorage.getInstance()
        storageReference = storage.getReference("User")
            .child("Transaksi")

        mFirebaseInstance = FirebaseDatabase.getInstance()
        mFirebaseDatabase = mFirebaseInstance.getReference("User")
            .child(preferences.getValues("user")!!)

        val harga = intent.getIntExtra("harga", 0)
        val key = intent.getStringExtra("key")

        val Nama = preferences.getValues("nama")
        val Nomor = preferences.getValues("nomor")
//        for (a in transaksi.indices){
//            hargaTotal + transaksi[a].hargaTotal!!



        tvNama.text = Nama
        tvNomor.text = Nomor
        tvHargaTotal.text = harga.toString()


        Log.v("yo", "harganya"+harga)

        btn_upload.setOnClickListener {
            if (statusAdd) {
                statusAdd = false
                btn_kirim.visibility = View.INVISIBLE

                btn_upload.text = "Upload"

            } else {
                Dexter.withActivity(this)
                    .withPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    .withListener(this)
                    .check()
            }
        }

        btn_kirim.setOnClickListener {
            if (fileRef != null){
                val progressDialog = ProgressDialog(this)
                progressDialog.setTitle("Uploading..")
                progressDialog.show()

                Log.v("tamvan", "file uri upload 2" + fileRef)

                val ref = storageReference.child("transaksi/"+ UUID.randomUUID().toString())
                ref.putFile(fileRef)
                    .addOnSuccessListener {
                        progressDialog.dismiss()
                        Toast.makeText(this@PaymentActivity,"Terkirim",
                            Toast.LENGTH_SHORT).show()


                        ref.downloadUrl.addOnSuccessListener {
//                            updateData(it.toString())
//                          BERHASIL MASUK
                            val hashMap: HashMap<String, String> = HashMap()
                            hashMap.put("bukti", it.toString())
                            hashMap.put("status", "Pending")

                            mFirebaseDatabase.child("transaksi")
                                .child(key)
                                .updateChildren(hashMap as Map<String, Any>)



                            Log.v("dapat", "url" + it.toString())

                            finish()

                            mFirebaseDatabase.child("cart")
                                .removeValue()

                            showNotif()

                            startActivity(Intent(this@PaymentActivity, HomeActivity::class.java))
                        }
                        // Update Data, FayFay Version


                    }
                    .addOnFailureListener { e ->
                        progressDialog.dismiss()
                        Toast.makeText(this@PaymentActivity,
                            "Failed"+ e.message, Toast.LENGTH_SHORT).show()
                    }
                    .addOnProgressListener { taskSnapshot ->
                        val progress = 100.0 * taskSnapshot.bytesTransferred / taskSnapshot
                            .totalByteCount
                        progressDialog.setMessage("Terkirim " + progress.toInt() + "%")
                    }


            }



        }
    }


//    MASIH GAGAL
//    private fun updateData(bukti: String) {
//        mFirebaseDatabase.child("transaksi").addValueEventListener(object : ValueEventListener{
//            override fun onDataChange(p0: DataSnapshot) {
//                val transaksi = intent.getParcelableExtra<Transaksi>("total")
//                val key = intent.getStringExtra("key")
//                transaksi.bukti = bukti
//
//                mFirebaseDatabase
//                    .child(key)
//                    .setValue(transaksi)
//
//
//            }
//
//            override fun onCancelled(p0: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//
//
//        })
//
//
//
//    }

//    private fun saveData(key: String,nama: String, nomor: String, hargaTotal: Int, bukti: String): Transaksi {
//        val data = Transaksi (key,
//            nama,nomor,
//            hargaTotal,bukti
//        )
//        return data
//
//    }

    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
        // Use Gallery and Camera
        ImagePicker.with(this)
            .provider(ImageProvider.BOTH)
            .start()
    }

    override fun onPermissionRationaleShouldBeShown(
        permission: PermissionRequest?,
        token: PermissionToken?
    ) {
        TODO("Not yet implemented")
    }

    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
        Toast.makeText(this, "Silahkan Upload Ulang Bukti Transfer", Toast.LENGTH_SHORT ).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            statusAdd = true
            fileRef = data?.data!!

            Glide.with(this)
                .load(fileRef)
                .into(iv_bukti)


            btn_kirim.visibility = View.VISIBLE

            btn_upload.text = "Hapus"
        } else if (resultCode == ImagePicker.RESULT_ERROR){
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }


    }

    private fun showNotif() {
        val NOTIFICATION_CHANNEL_ID = "Channel_Alfa3_Notif"
        val context = this.applicationContext
        var notificationManager =
            context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channelName = "Alfa 3 Notif Channel"
            val importance = NotificationManager.IMPORTANCE_HIGH

            val mChannel = NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, importance)
            notificationManager.createNotificationChannel(mChannel)
        }

//        val mIntent = Intent(this, CheckoutSuccessActivity::class.java)
//        val bundle = Bundle()
//        bundle.putString("id", "id_film")
//        mIntent.putExtras(bundle)

        val mIntent = Intent(this, HomeActivity::class.java)
//        val bundle = Bundle()
//        bundle.putParcelable("data", datas)
//        mIntent.putExtras(bundle)

        val pendingIntent =
            PendingIntent.getActivity(this, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
        builder.setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.logo_alfa3)
            .setLargeIcon(
                BitmapFactory.decodeResource(
                    this.resources,
                    R.drawable.logo_alfa3
                )
            )
            .setTicker("notif alfa3 starting")
            .setAutoCancel(true)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setLights(Color.RED, 3000, 3000)
            .setDefaults(Notification.DEFAULT_SOUND)
            .setContentTitle("Produk berhasil terbeli")
            .setContentText("Produk sudah masuk, silahkan tunggu konfirmasi dari admin! Terima Kasih!")

        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(115, builder.build())
    }

}
