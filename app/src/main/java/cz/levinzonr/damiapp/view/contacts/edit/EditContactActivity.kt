package cz.levinzonr.damiapp.view.contacts.edit

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import cz.levinzonr.damiapp.R

import kotlinx.android.synthetic.main.activity_edit_contact.*

class EditContactActivity : AppCompatActivity() {

    companion object {
        const val TAG = "EditContacActivity"
        const val ARG_ID = "ContactId"

        fun startAsIntent(context: Context, contactID : String?) {
            val intent = Intent(context, EditContactActivity::class.java)
            intent.putExtra(ARG_ID, contactID)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_contact)
        setSupportActionBar(toolbar)
    }

}
