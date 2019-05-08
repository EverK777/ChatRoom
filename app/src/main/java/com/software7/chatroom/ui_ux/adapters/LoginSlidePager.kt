package com.software7.chatroom.ui_ux.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.software7.chatroom.ui_ux.fragments.login_fragments.FragmentLogin
import com.software7.chatroom.ui_ux.fragments.login_fragments.FragmentRegister
import java.util.ArrayList

class LoginSlidePager constructor( fm: FragmentManager?) : FragmentStatePagerAdapter(fm) {

    private val mFragmentList = ArrayList<Fragment>()

    override fun getItem(p0: Int): Fragment {
        mFragmentList.add(FragmentLogin())
        mFragmentList.add(FragmentRegister())

        return  mFragmentList[p0]
    }

    override fun getCount(): Int {
       return  2
    }
}




