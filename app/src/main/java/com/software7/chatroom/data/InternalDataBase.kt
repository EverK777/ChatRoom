package com.software7.chatroom.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.software7.chatroom.models.Contacts
import com.software7.chatroom.models.Message

class InternalDataBase(var context: Context) : SQLiteOpenHelper(context,"chat_room",null,1)  {
    private val chatsTable           = "chats"
    private val contactsTable        = "contacts"
    private var sdb:SQLiteDatabase? = null
    private var fila: Cursor?       = null




    override fun onCreate(db: SQLiteDatabase?) {
        //contactsTable
        db!!.execSQL("CREATE TABLE $contactsTable(id_contact TEXT PRIMARY KEY, name_contact TEXT,email_contact TEXT)")
        //messageTable
        db.execSQL("CREATE TABLE $chatsTable(id_chat INTEGER  PRIMARY KEY AUTOINCREMENT, id_message TEXT, milli INTEGER, second INTEGER, minute INTEGER, hour INTEGER,day INTEGER,month INTEGER,year INTEGER,content TEXT,is_emisor INTEGER, id_contact TEXT,seen INTEGER,received INTEGER,FOREIGN KEY (id_contact) REFERENCES contacts(id_contact))")


    }

    override fun onUpgrade(dB: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        dB!!.execSQL("DROP TABLE IF EXISTS $chatsTable;"+"DROP TABLE IF EXISTS $contactsTable;")
        onCreate(dB) }


    fun saveContact (contact : Contacts){
        val values = ContentValues()

        values.put("id_contact",contact.idContact)
        values.put("name_contact",contact.nameContact)
        values.put("email_contact",contact.emailContact)

        this.writableDatabase.insert(contactsTable,null,values)

    }

    fun saveMessage (message : Message){


        val values = ContentValues()

        values.put("id_message",message.idMessage)
        values.put("milli",message.milliSecondMessage)
        values.put("second",message.secondMessage)
        values.put("minute",message.minuteMessage)
        values.put("hour",message.hourMessage)
        values.put("day",message.dayMessage)
        values.put("month",message.monthMessage)
        values.put("year",message.yearMessage)
        values.put("content",message.content)
        values.put("is_emisor",message.sender)
        values.put("id_contact",message.emisor)
        values.put("seen",message.seen.toInt())
        values.put("received",message.received.toInt())

        this.writableDatabase.insert(chatsTable,null,values)

    }

    fun getContactByEmail(email:String): Cursor{
        val columns = arrayOf("id_contact","name_contact","email_contact")
        return readableDatabase.query(contactsTable, columns, "email_contact=?", arrayOf(email), null, null, "name_contact", null)

    }

    fun getContactById(idContact:String): Cursor{
        val columns = arrayOf("id_contact","name_contact","email_contact")
        return readableDatabase.query(contactsTable, columns, "id_contact='$idContact'", null, null, null, "name_contact", null)
    }

    fun getContacts(): Cursor{
        val columns = arrayOf("id_contact","name_contact","email_contact")
        return readableDatabase.query(contactsTable, columns, null, null, null, null, "name_contact", null)
    }


    fun getAllMessages() :Cursor{
        val rawQuery = "SELECT a.id_message,a.hour,a.minute,a.second, a.milli, a.day,a.month, a.year,a.content, a.id_contact,a.is_emisor , b.name_contact ,SUM(a.seen) as unseen, a.received, MAX(a.id_chat) FROM chats a INNER JOIN contacts b ON a.id_contact=b.id_contact GROUP BY a.id_contact  ORDER BY id_chat   "

         return readableDatabase.rawQuery(rawQuery,null)

    }

    fun getMessageFromIdMessage(idMessage: String) : Cursor{
        val rawQuery  = "SELECT * FROM chats where id_message='$idMessage' ORDER BY id_chat"

        return readableDatabase.rawQuery(rawQuery,null)
    }

    fun getChatMessage(idContact:String) : Cursor{
        val rawQuery  = "SELECT * FROM chats where id_contact='$idContact' ORDER BY id_chat"

        return readableDatabase.rawQuery(rawQuery,null)

    }

    fun updateMessages(idMessage:String){
        val values = ContentValues()
        values.put("seen",0)
        values.put("received",0)
        writableDatabase.update(chatsTable, values, "id_message='$idMessage'", null)
    }


    override fun close() {
      this.close()
    }
}