package com.example.recyclerviewapp.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.recyclerviewapp.dto.DataDTO
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_add_news.*
import java.util.*


class AddNewsActivity : AppCompatActivity() {

    val progressDialog: ProgressDialog? = null
    private var filePath: Uri? = null

    internal var storage: FirebaseStorage? = null
    internal var storageReference: StorageReference? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.recyclerviewapp.R.layout.activity_add_news)

        //check runtime permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {

                val permissions = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                // show pop up to request runtime permission
                requestPermissions(permissions, PERMISSION_CODE)
            }
        }

        chooseImageButton.setOnClickListener {
            pickImageFromGaleri()
        }
        addNewsFirebaseButton.setOnClickListener {
            saveNews()
        }

    }

    private fun saveNews() {

        storage = FirebaseStorage.getInstance()
        storageReference = storage!!.reference
        lateinit var database: DatabaseReference

        database = FirebaseDatabase.getInstance().reference.child("news")
        val newsId = database.push().key
        val title = addNewsTitleEditText.text.toString().trim()
        val descrip = addNewsDescripEditText.text.toString().trim()
        // val imageUrl adinda bir degisken eklememize gerek yok biz image secimi yaparken gelen image icin url aliyoruz bir daha ayni urli cekmemize gerek yok


        if (filePath != null && !title.equals("") && !descrip.equals("")) {

            val imageRef = storageReference!!.child("images/" + UUID.randomUUID().toString())
            imageRef.putFile(filePath!!)
                    .addOnSuccessListener {
                        progressDialog?.dismiss()
                        Toast.makeText(this, "Upload is success", Toast.LENGTH_LONG).show()
                    }
                    .addOnFailureListener {
                        progressDialog?.dismiss()
                        Toast.makeText(this, "Can not upload", Toast.LENGTH_LONG).show()
                    }
                    .addOnProgressListener {
                        //Yuklenme sirasinda kac byte olacagi ayarlaniyor
                        val progress = 100.0 * it.bytesTransferred / it.totalByteCount
                        progressDialog?.setMessage("Uploaded " + progress.toInt() + "/")
                    }


            //news metod added for getting news items from model
            val news = DataDTO(title, filePath.toString(), descrip)
            //addOnCanceledListener listen my firebase when I added new Item turn to me
            newsId?.let {
                database.child(it).setValue(news).addOnCanceledListener {

                    //    Toast.makeText(this, "News added with success", Toast.LENGTH_LONG).show()
                }
            }
            showProgressDialog()
            val intent = Intent(this, MainActivity::class.java)
            this.startActivity(intent)
            progressDialog?.dismiss()

        } else {
            Toast.makeText(this, "anyone colomn is not empty", Toast.LENGTH_LONG).show()

        }

    }

    private fun pickImageFromGaleri() {

        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Resim Seçiniz"), IMAGE_PICK_CODE)
    }

    companion object {

        private val IMAGE_PICK_CODE = 1000
        private val PERMISSION_CODE = 1001
    }

    //Fotograflara erisim izni istemek icicn kullanilan override metodu
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    //permisson from pop up  granted
                    pickImageFromGaleri()
                } else {
                    //permisson from pop up denied
                    Toast.makeText(this, "Permisssion DENIED", Toast.LENGTH_LONG).show()

                }
            }
        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            filePath = data?.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
                addNewsImageView.setImageBitmap(bitmap)

            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

    }

    private fun showProgressDialog() {

        progressDialog?.setTitle("Uploading...")
        progressDialog?.show()
    }


}
