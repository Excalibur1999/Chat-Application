package com.hrishi.chatapplication.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.hrishi.chatapplication.R
import kotlinx.android.synthetic.main.activity_create_account.*

class CreateAccountActivity : AppCompatActivity() {
    var mAuth: FirebaseAuth?=null
    var mDatabase:DatabaseReference?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)
        mAuth=FirebaseAuth.getInstance()
        createAccBtnIdE.setOnClickListener{
            var email=createMailId.text.toString().trim()
            var password=createPassId.text.toString().trim()
            var displayName=displayNameId.text.toString().trim()

            if(!TextUtils.isEmpty(email) || !TextUtils.isEmpty(password) || !TextUtils.isEmpty(displayName))
                createAccount(email,password,displayName)
            else
                Toast.makeText(this,"Please fill all details",Toast.LENGTH_SHORT).show()
        }

    }
    private fun createAccount(email:String, password:String, displayName:String){
        mAuth!!.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task: Task<AuthResult> ->
            if (task.isSuccessful) {

                var currentUser = mAuth!!.currentUser
                var userId = currentUser!!.uid
                mDatabase = FirebaseDatabase.getInstance().reference
                    .child("Users").child(userId)

                var userObject = HashMap<String, String>()
                userObject.put("displayName", displayName)
                userObject.put("status", "Hello there...")
                userObject.put("image", "default")
                userObject.put("thumb_image", "default")
                mDatabase!!.setValue(userObject).addOnCompleteListener{
                        task:Task<Void>->
                    if (task.isSuccessful){
                        val intent= Intent(this, DashboardActivity::class.java)
                        intent.putExtra("display",displayName)
                        startActivity(intent)
                        finish()
                    }else
                        Toast.makeText(this,"fail to create user",Toast.LENGTH_SHORT).show()
                }
            }else
                Toast.makeText(this,"failed",Toast.LENGTH_SHORT).show()

        }
    }
}