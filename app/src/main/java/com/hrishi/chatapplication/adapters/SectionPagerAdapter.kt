package com.hrishi.chatapplication.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.hrishi.chatapplication.fragments.ChatsFragment
import com.hrishi.chatapplication.fragments.UserFragment

class SectionPagerAdapter(fm:FragmentManager):FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
       when(position){
           0->return UserFragment()
           1->return ChatsFragment()
       }
        return null!!
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when(position){
            0->return "Users"
            1->return "Chats"
        }
        return null!!
    }
}