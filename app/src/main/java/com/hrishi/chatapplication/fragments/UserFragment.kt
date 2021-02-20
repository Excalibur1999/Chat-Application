package com.hrishi.chatapplication.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.hrishi.chatapplication.R
import com.hrishi.chatapplication.adapters.UsersAdapter
import com.hrishi.chatapplication.models.Users
import kotlinx.android.synthetic.main.fragment_user.*

class UserFragment : Fragment() {
    var databaseReference:DatabaseReference?=null
    var options: FirebaseRecyclerOptions<Users>?=null
    var adapter:UsersAdapter?=null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        databaseReference=FirebaseDatabase.getInstance().reference

        var linerLayoutManager=LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        options=FirebaseRecyclerOptions.Builder<Users>().
        setQuery(
            databaseReference!!.child("Users")
            ,Users::class.java).build()
        userRecyclerView.setHasFixedSize(true)

        userRecyclerView.layoutManager=linerLayoutManager
        adapter=UsersAdapter(databaseReference!!,context!!,
            options as FirebaseRecyclerOptions<Users>
        )

        userRecyclerView.adapter=adapter

    }

    override fun onStart() {
        super.onStart()
        adapter!!.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter!!.stopListening()
    }


}
