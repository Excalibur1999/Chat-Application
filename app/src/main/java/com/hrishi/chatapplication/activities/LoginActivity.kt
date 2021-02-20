package com.hrishi.chatapplication.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.hrishi.chatapplication.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    var mAuth: FirebaseAuth?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mAuth=FirebaseAuth.getInstance()
        loginBtnId.setOnClickListener {
            var email=inputEmail.text.toString().trim()
            var password=inputPass.text.toString().trim()
            if (!TextUtils.isEmpty(email) || !TextUtils.isEmpty(password))
                login(email,password)
            else
                Toast.makeText(this,"Please fill all details", Toast.LENGTH_SHORT).show()

        }
    }
    private fun login(email:String,password:String){
        mAuth!!.signInWithEmailAndPassword(email,password).addOnCompleteListener {
            task:Task<AuthResult>->

                if (task.isSuccessful) {
                    var userName = email.split("@")[0]
                    val intent = Intent(this, DashboardActivity::class.java)
                    intent.putExtra("userName", userName)
                    startActivity(intent)
                    finish()
                }else{
                    Toast.makeText(this,"Login failed",Toast.LENGTH_SHORT).show()
                }

        }

    }
}