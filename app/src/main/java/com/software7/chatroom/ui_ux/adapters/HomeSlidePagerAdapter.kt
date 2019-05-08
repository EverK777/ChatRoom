package com.software7.chatroom.ui_ux.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.software7.chatroom.ui_ux.fragments.home_fragments.ChatsFragment
import com.software7.chatroom.ui_ux.fragments.home_fragments.ContactsFragment
import java.util.ArrayList

class HomeSlidePagerAdapter constructor ( fm: FragmentManager?) : FragmentStatePagerAdapter(fm)  {
    private val mFragmentList = ArrayList<Fragment>()
    private val mFragmentTitleList = ArrayList<String>()

    override fun getItem(p0: Int): Fragment {
        return mFragmentList[p0]

    }

    override fun getCount(): Int {

        return mFragmentList.size
    }
    fun addFragment(fragment: Fragment, title: String) {
        mFragmentList.add(fragment)
        mFragmentTitleList.add(title)
    }


    override fun getPageTitle(position: Int): CharSequence? {
        return mFragmentTitleList[position]
    }

}