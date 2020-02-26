package com.example.filecreationandreading

import `in`.mayanknagwanshi.imagepicker.ImageSelectActivity
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var buttonPick: Button
    private lateinit var imageView: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        buttonPick = findViewById(R.id.btnPick)
        imageView = findViewById(R.id.imgSelected)

        buttonPick.setOnClickListener(this)


    }

    override fun onClick(v: View?) {
        when (v) {
            buttonPick -> showSelectionDialog()

        }
    }

    @SuppressLint("InflateParams")
    private fun showSelectionDialog() {
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_selection, null)
        //AlertDialogBuilder
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
            .setTitle("Select a way to Pick Image")
        //show dialog
        val mAlertDialog = mBuilder.create().show()

        val camText: TextView = mDialogView.findViewById(R.id.textCam)
        val galText: TextView = mDialogView.findViewById(R.id.textGal)

        camText.setOnClickListener { pickImageCamera() }
        galText.setOnClickListener { pickImageGallery() }

    }

    private fun pickImageGallery() {
        val intent = Intent(this, ImageSelectActivity::class.java)
        intent.putExtra(ImageSelectActivity.FLAG_COMPRESS, false) //default is true

        intent.putExtra(ImageSelectActivity.FLAG_CAMERA, false) //default is true

        intent.putExtra(ImageSelectActivity.FLAG_GALLERY, true) //default is true

        startActivityForResult(intent, 1057)
    }

    private fun pickImageCamera() {
        val intent = Intent(this, ImageSelectActivity::class.java)
        intent.putExtra(ImageSelectActivity.FLAG_COMPRESS, false) //default is true

        intent.putExtra(ImageSelectActivity.FLAG_CAMERA, true) //default is true

        intent.putExtra(ImageSelectActivity.FLAG_GALLERY, false) //default is true

        startActivityForResult(intent, 1057)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1057 && resultCode == Activity.RESULT_OK) {
            val filePath: String? =
                data?.getStringExtra(ImageSelectActivity.RESULT_FILE_PATH)
            val selectedImage = BitmapFactory.decodeFile(filePath)
            val fName="ImageDemo"+System.currentTimeMillis()+".png"
            println(fName)
            Toast.makeText(this@MainActivity, "Image Name in your Storage is :$fName", Toast.LENGTH_SHORT).show();
            ImageUtils.saveToInternalStorage(this@MainActivity,selectedImage,fName)
            imageView.setImageBitmap(ImageUtils.getImageFromInternalStorage(this@MainActivity,fName))
        }
    }


}
