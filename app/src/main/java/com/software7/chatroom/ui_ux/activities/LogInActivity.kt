package com.software7.chatroom.ui_ux.activities


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.widget.Toast
import com.software7.chatroom.R
import com.software7.chatroom.ui_ux.adapters.LoginSlidePager
import kotlinx.android.synthetic.main.activity_log_in.*
import java.util.ArrayList

class LogInActivity : FragmentActivity(){

    private lateinit var mPager : ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        mPager = findViewById(R.id.loginPager)
        val  loginAdapterPager = LoginSlidePager(supportFragmentManager)
        mPager.adapter = loginAdapterPager
        loginPager.setOnTouchListener { _, _ -> true }


    }


}
