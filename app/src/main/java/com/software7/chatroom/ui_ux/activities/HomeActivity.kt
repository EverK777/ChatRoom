package com.software7.chatroom.ui_ux.activities

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast
import com.software7.chatroom.R
import com.software7.chatroom.ui_ux.adapters.HomeSlidePagerAdapter
import com.software7.chatroom.ui_ux.dialogs.ModalBottomDialog
import com.software7.chatroom.ui_ux.dialogs.NewContactDialog
import com.software7.chatroom.ui_ux.fragments.home_fragments.ChatsFragment
import com.software7.chatroom.ui_ux.fragments.home_fragments.ContactsFragment
import com.software7.chatroom.utils.EndToEndEncrypt

import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.content_home.*


class HomeActivity : AppCompatActivity() {


    private var isAddingNewContact = false
    private var contactsFragment = ContactsFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)


        setupViewPager(viewPager)
        tabContainer.setupWithViewPager(viewPager)


        viewPager.addOnPageChangeListener(object: ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(p0: Int) {
            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
            }

            override fun onPageSelected(p0: Int) {
                isAddingNewContact = if(p0 == 1){
                    fab.setImageResource(R.drawable.ic_person_add_black_24dp)
                    true
                }else{
                    fab.setImageResource(R.drawable.ic_message_black_24dp)
                    false
                }
            }

        })

        fab.setOnClickListener { actionButton() }

    }

    private fun actionButton(){
        if(!isAddingNewContact){
            val dialog = ModalBottomDialog()
            dialog.show(supportFragmentManager,"modalBottomSheet")
        }else{
            val dialog = NewContactDialog(this, contactsFragment.contacts,
                contactsFragment.contactsAdapter!!,
                contactsFragment.recyclerContacts!!,
                contactsFragment.emptyMessageTV!!)
            dialog.showDialog()
        }
    }

    private fun setupViewPager(viewPager: ViewPager) {

        val adapter = HomeSlidePagerAdapter(supportFragmentManager)
        adapter.addFragment(ChatsFragment (),"Conversaciones")
        adapter.addFragment(contactsFragment,"Contactos")
        viewPager.adapter = adapter
    }



    fun decryptMesage ():String ?{
      /*  try{
            val messageBytes  = EndToEndEncrypt.decryptBASE64(hashMessage.text.toString())
            val decodeMessage = EndToEndEncrypt.decryptByPrivateKey(messageBytes,privateKey)
            val decodeStr = String(decodeMessage)

            // message to see in the app
            Toast.makeText(this, decodeStr, Toast.LENGTH_LONG).show()
            return String(decodeMessage)

        }catch(e:Exception){
            e.printStackTrace()
        }*/

        return null
    }

}
