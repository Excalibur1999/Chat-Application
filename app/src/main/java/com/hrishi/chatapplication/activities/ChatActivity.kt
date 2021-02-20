package com.hrishi.chatapplication.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.hrishi.chatapplication.R
import com.hrishi.chatapplication.adapters.ChatRowAdapter
import com.hrishi.chatapplication.adapters.UsersAdapter
import com.hrishi.chatapplication.models.FriendlyMessage
import kotlinx.android.synthetic.main.activity_chat.*

class ChatActivity : AppCompatActivity() {
    var userId: String? = null
    var mFirebaseDatabase: DatabaseReference? = null
    private var mFirebaseUser: FirebaseUser? = null
    var options: FirebaseRecyclerOptions<FriendlyMessage>? = null
    var mLinearLayoutManager: LinearLayoutManager? = null
    var chatRowAdapter: ChatRowAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        mFirebaseUser = FirebaseAuth.getInstance().currentUser

        userId = intent.extras!!.get("userId").toString()
        mLinearLayoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)


        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        mFirebaseDatabase = FirebaseDatabase.getInstance().reference

        options = FirebaseRecyclerOptions.Builder<FriendlyMessage>().setQuery(
            mFirebaseDatabase!!.child("messages"), FriendlyMessage::class.java
        ).build()


        chatRecyclerView.layoutManager = mLinearLayoutManager


        sendMessageBtn.setOnClickListener {
            if (intent.extras!!.get("userName").toString() != "") {
                var currentUserName = intent.extras!!.get("userName").toString()
                var mCurrentUser = mFirebaseUser!!.uid

                var friendlyMessage =
                    FriendlyMessage(mCurrentUser, currentUserName, sendMessageEdit.text.toString())

                mFirebaseDatabase!!.child("messages")
                    .push().setValue(friendlyMessage)
                sendMessageEdit.setText("")
            }


        }
        setAdapter()
    }

        private fun setAdapter() {
            chatRowAdapter =
                ChatRowAdapter(userId!!, mFirebaseDatabase!!, mFirebaseUser!!, this, options!!)
            chatRecyclerView.adapter=chatRowAdapter

        }
    override fun onStart() {
        super.onStart()
        chatRowAdapter!!.startListening()
    }
    override fun onStop() {
        super.onStop()
        chatRowAdapter!!.stopListening()
    }

}