package com.software7.chatroom.ui_ux.dialogs

import android.app.Dialog
import android.content.Context
import android.view.Window
import android.widget.TextView
import com.software7.chatroom.R

class DialogLoading(var context: Context, var message: String) {

    private var dialog: Dialog ?= null

    init {
        dialog = Dialog(context)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setContentView(R.layout.loading_progress_dialog)
        dialog!!.setCancelable(false)
        val textView = dialog!!.findViewById<TextView>(R.id.textWait)
        textView.text = message
    }

    fun showDialog(){

        dialog!!.show()


    }

    fun hideDialog(){
        dialog!!.dismiss()
    }

}