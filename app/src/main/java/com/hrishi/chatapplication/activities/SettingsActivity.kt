package com.hrishi.chatapplication.activities

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.hrishi.chatapplication.R
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageActivity
import com.theartofdev.edmodo.cropper.CropImageView
import id.zelory.compressor.Compressor
import kotlinx.android.synthetic.main.activity_settings.*
import java.io.ByteArrayOutputStream
import java.io.File

class SettingsActivity : AppCompatActivity() {
    private val TAG = "SettingsActivity"
    var databaseReference: DatabaseReference? = null
    var currentUser: FirebaseUser? = null
    var storageReference: StorageReference? = null
    var GALLERY_ID: Int = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        currentUser = FirebaseAuth.getInstance().currentUser
        storageReference = FirebaseStorage.getInstance().reference
        var userId = currentUser!!.uid

        databaseReference = FirebaseDatabase.getInstance().reference
            .child("Users").child(userId)

        databaseReference!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var displayName = snapshot.child("displayName").value
                var image = snapshot.child("image").value.toString()
                var status = snapshot.child("status").value

                settingsDisplayNameId.text = displayName.toString()
                settingsStatusID.text = status.toString()
                if (image!! != "default") {
                    Picasso.get()
                        .load(image)
                        .placeholder(R.drawable.profile)
                        .into(settingsProfileImgId)
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }


        })
        settingsProfileChangeBtn.setOnClickListener {
            var galleryIntent = Intent()
            galleryIntent.type = "image/*"
            galleryIntent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(galleryIntent, "SELECT_IMAGE"), GALLERY_ID)

        }
        settingsStatusChangeBtn.setOnClickListener {
            var intent = Intent(this, StatusChangeActivity::class.java)
            intent.putExtra("status", settingsStatusID.text.toString())
            startActivity(intent)
            finish()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY_ID && resultCode == Activity.RESULT_OK) {
            var image: Uri? = data!!.data
            CropImage.activity(image)
                .setAspectRatio(1, 1)
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this)

        } else {
            if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                Log.d("before", "before file path")
                val result = CropImage.getActivityResult(data)
                if (resultCode == RESULT_OK) {
                    val resultUri = result.uri
                    val userId = currentUser!!.uid

                    var filePath = storageReference!!.child("chat_profile_images")
                        .child("$userId.jpg")
                    filePath.putFile(resultUri)
                        .addOnCompleteListener { task: Task<UploadTask.TaskSnapshot> ->

                            if (task.isSuccessful) {

                                //error = downladdurl is no correct
                                var downloadUrl = task.result!!.toString()
                                Log.d(TAG, downloadUrl)
                                var updateObject = HashMap<String, Any>()
                                updateObject["image"] = downloadUrl
                                databaseReference!!.updateChildren(updateObject)
                                    .addOnCompleteListener { task: Task<Void> ->
                                        if (task.isSuccessful) {
                                            Toast.makeText(
                                                this,
                                                "Profile Updated",
                                                Toast.LENGTH_SHORT
                                            )
                                                .show()
                                        }
                                    }
                            }

                        }
                }
            }
        }
    }


}