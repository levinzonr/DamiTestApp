package cz.levinzonr.damiapp.view.contacts.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import cz.levinzonr.damiapp.R
import cz.levinzonr.damiapp.view.contacts.edit.EditContactActivity

import kotlinx.android.synthetic.main.activity_contact_detail.*

class ContactDetailActivity : AppCompatActivity() {

    companion object {

        private const val ARG_ID = "ContactID"

        fun startAsIntent(context: Context, contactID : Int) {
            val intent = Intent(context, ContactDetailActivity::class.java)
            intent.putExtra(ARG_ID, contactID)
            context.startActivity(intent)
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_detail)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            EditContactActivity.startAsIntent(this, intent.getIntExtra(ARG_ID, -1))
        }
    }

}
