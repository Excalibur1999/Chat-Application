package com.hrishi.chatapplication.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.hrishi.chatapplication.R
import com.hrishi.chatapplication.adapters.SectionPagerAdapter
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity() {
    var sectionPagerAdapter:SectionPagerAdapter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        sectionPagerAdapter= SectionPagerAdapter(supportFragmentManager)
        dashPagerId.adapter=sectionPagerAdapter
        mainTabs.setupWithViewPager(dashPagerId)

        if (intent.extras!=null){
            var name= intent.extras!!.get("userName")
            Toast.makeText(this,"Welcome "+name, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
         super.onCreateOptionsMenu(menu)
         menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
         super.onOptionsItemSelected(item)
        when(item.itemId){
            R.id.logoutId->{FirebaseAuth.getInstance().signOut()
             startActivity(Intent(this,MainActivity::class.java))
                finish()
            }
            R.id.settingsId->startActivity(Intent(this,SettingsActivity::class.java))
        }
        return true
    }
}