package com.hrishi.chatapplication.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.hrishi.chatapplication.R
import kotlinx.android.synthetic.main.activity_status_change.*

class StatusChangeActivity : AppCompatActivity() {
    var databaseReference: DatabaseReference?=null
    var currentUser: FirebaseUser?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_status_change)
        supportActionBar!!.title="Status"
        if(intent!=null){
            var status= intent.extras!!.get("status").toString()
            editStatusIdE.setText(status)

        }
        statusSaveBtnId.setOnClickListener {
            currentUser=FirebaseAuth.getInstance().currentUser
            var userId=currentUser!!.uid
            databaseReference=FirebaseDatabase.getInstance().reference
                .child("Users").child(userId)
            if (!TextUtils.isEmpty(editStatusIdE.text)) {
                var status = editStatusIdE.text.toString()
                databaseReference!!.child("status").setValue(status).addOnCompleteListener {
                        task:Task<Void>->
                    if(task.isSuccessful)
                        Toast.makeText(this,"Status Updated",Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this,SettingsActivity::class.java))
                    finish()

                }
            }else
                Toast.makeText(this,"Status cant be Empty",Toast.LENGTH_SHORT).show()
        }
    }
}