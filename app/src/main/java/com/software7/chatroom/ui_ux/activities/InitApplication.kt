package com.software7.chatroom.ui_ux.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.software7.chatroom.ChatRoom

class InitApplication : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(ChatRoom.userAuth != null ){
            val intent = Intent(this,HomeActivity::class.java)
            startActivity(intent)
            finish()
        }else{
            val intent = Intent(this,LogInActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
