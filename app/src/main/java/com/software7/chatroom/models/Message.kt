package com.software7.chatroom.models

import com.google.firebase.database.Exclude

class Message (var idMessage : String,
               var hourMessage:Int,
               var minuteMessage:Int,
               var secondMessage:Int,
               var milliSecondMessage:Int,
               var dayMessage: Int,
               var monthMessage: Int,
               var yearMessage : Int,
               var content: String,
               var emisor: String,
               var sender: Int,
               var publicKey: String,
               var seen :String,
               var received:String)