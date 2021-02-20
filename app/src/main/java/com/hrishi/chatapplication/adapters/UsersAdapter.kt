package com.hrishi.chatapplication.adapters

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.hrishi.chatapplication.R
import com.hrishi.chatapplication.activities.ChatActivity
import com.hrishi.chatapplication.activities.ProfileActivity
import com.hrishi.chatapplication.models.Users
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class UsersAdapter(databaseQuery:DatabaseReference, var context: Context,
                   options: FirebaseRecyclerOptions<Users>
):FirebaseRecyclerAdapter<Users,UsersAdapter.ViewHolder>(options) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int, user: Users) {
        var userId=getRef(position).key
       holder.bindView(user,context)
        holder.itemView.setOnClickListener{
            var options= arrayOf("Open Profile","Send Message")
            var builder=AlertDialog.Builder(context)
            builder.setTitle("Select Option")
            builder.setItems(options,DialogInterface.OnClickListener { dialogInterface, i ->
                var userName=holder.displayNameText
                var status=holder.statusText
                var profilePic=holder.userProfileTxt

                if (i==0) {
                    var intent= Intent(context, ProfileActivity::class.java)
                    intent.putExtra("userId",userId)
                    context.startActivity(intent)
                }
                    else{
                    //send message
                    var intent=Intent(context,ChatActivity::class.java)
                    intent.putExtra("userId",userId)
                    intent.putExtra("userName",userName)
                    intent.putExtra("status",status)
                    intent.putExtra("profilePic",profilePic)
                    context.startActivity(intent)
                }
            })
            builder.show()
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view:View=LayoutInflater.from(context).inflate(R.layout.user_row,parent,false)
        return ViewHolder(view)
    }

    class ViewHolder( itemView:View):RecyclerView.ViewHolder(itemView) {
        var displayNameText:String?=null
        var statusText:String?=null
        var userProfileTxt:String?=null

        fun bindView(user:Users,context: Context){
            var displayName=itemView.findViewById<TextView>(R.id.userRowName)
            var status=itemView.findViewById<TextView>(R.id.userRowStatus)
            var userProfile=itemView.findViewById<CircleImageView>(R.id.userRowProfile)

            displayNameText=user.displayName
            statusText=user.status
            userProfileTxt=user.image


            displayName.text=user.displayName
            status.text=user.status
            Picasso.get().load(userProfileTxt).placeholder(R.drawable.profile).into(userProfile)
        }

    }




}