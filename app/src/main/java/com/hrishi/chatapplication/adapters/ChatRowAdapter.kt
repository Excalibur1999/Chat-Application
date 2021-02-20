package com.hrishi.chatapplication.adapters

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.hrishi.chatapplication.R
import com.hrishi.chatapplication.models.FriendlyMessage
import com.hrishi.chatapplication.models.Users

class ChatRowAdapter(var userId:String,var mDatabaseReference: DatabaseReference,var mFirebaseUser: FirebaseUser,var context: Context,options: FirebaseRecyclerOptions<FriendlyMessage>):
    FirebaseRecyclerAdapter<FriendlyMessage, ChatRowAdapter.ViwHolder>(options) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViwHolder {
        var view=LayoutInflater.from(context).inflate(R.layout.item_message,parent,false)
        return ViwHolder(view)
    }



    override fun onBindViewHolder(holder: ViwHolder, position: Int, model: FriendlyMessage) {

        if (model.text!=null) {
            holder.bindView(model)
            var currentUserId=mFirebaseUser.uid
            var isMe:Boolean=model.id.equals(currentUserId)
            if (isMe){
                holder.card!!.setCardBackgroundColor(context.resources.getColor(R.color.light_green))
                holder.messengerName!!.setTextColor(context.resources.getColor(R.color.white))
                holder.message!!.setTextColor(context.resources.getColor(R.color.white))
                mDatabaseReference.child("Users").child(currentUserId)
                    .addValueEventListener(object :ValueEventListener{
                        override fun onDataChange(snapshot: DataSnapshot) {
                            var displayName=snapshot.child("displayName").value.toString()
                            holder.messengerName!!.text=displayName

                        }

                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }

                    })
            }else{


                mDatabaseReference.child("Users").child(userId)
                    .addValueEventListener(object :ValueEventListener{
                        override fun onDataChange(snapshot: DataSnapshot) {
                            var displayName=snapshot.child("displayName").value.toString()
                            holder.messengerName!!.text=displayName

                        }

                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }

                    })
            }
        }
    }
    class ViwHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        var messengerName:TextView?= null
        var message:TextView?=null
        var card:CardView?=null
        fun bindView(friendlyMessage: FriendlyMessage){
             messengerName=itemView.findViewById(R.id.messengerName)
            message=itemView.findViewById(R.id.messageId)
            card=itemView.findViewById(R.id.card)
            messengerName!!.text=friendlyMessage.name
            message!!.text=friendlyMessage.text
        }
    }
}