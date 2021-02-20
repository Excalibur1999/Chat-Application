package com.hrishi.chatapplication.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.hrishi.chatapplication.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {
    var mCurrentUser:FirebaseUser?=null
    var mDatabase:DatabaseReference?=null
    var userId:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        supportActionBar!!.title="Profile"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        if (intent!=null){
            userId=intent.extras!!.getString("userId").toString()
            mCurrentUser=FirebaseAuth.getInstance().currentUser
            mDatabase= FirebaseDatabase.getInstance().reference.child("Users").child(userId!!)

            setUpProfile()
        }
    }
    private fun setUpProfile(){
        mDatabase!!.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var displayName=snapshot.child("displayName").value.toString()
                var status=snapshot.child("status").value.toString()
                var image=snapshot.child("image").value.toString()

                profilName.text= displayName
                profileStatus.text=status
                Picasso.get().load(image).placeholder(R.drawable.profile).into(profileImageId)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}