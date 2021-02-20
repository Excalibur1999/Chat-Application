package com.hrishi.chatapplication.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.hrishi.chatapplication.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var mAuth: FirebaseAuth? = null
    var user: FirebaseUser? = null
    var mAuthListener: FirebaseAuth.AuthStateListener? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mAuth = FirebaseAuth.getInstance()
        mAuthListener = FirebaseAuth.AuthStateListener { firebaseAuth: FirebaseAuth ->
            user = firebaseAuth.currentUser
            if (user != null) {
                var intent = Intent(this, DashboardActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        createActBtnId.setOnClickListener {
            val intent = Intent(this, CreateAccountActivity::class.java)
            startActivity(intent)

        }
        loginBtnId.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)

        }
    }

    override fun onStart() {
        super.onStart()
        mAuth!!.addAuthStateListener(mAuthListener!!)

    }

    override fun onStop() {
        super.onStop()
        mAuth!!.removeAuthStateListener { mAuthListener }
    }

}