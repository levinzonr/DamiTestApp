package cz.levinzonr.damiapp.view.contacts.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import cz.levinzonr.damiapp.R
import cz.levinzonr.damiapp.model.entities.Contact
import cz.levinzonr.damiapp.presenter.contacts.ContactDetailPresenter
import cz.levinzonr.damiapp.view.contacts.edit.EditContactActivity

import kotlinx.android.synthetic.main.activity_contact_detail.*
import kotlinx.android.synthetic.main.content_contact_detail.*

class ContactDetailActivity : AppCompatActivity(), ContactDetailView {

    private lateinit var presenter: ContactDetailPresenter

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
        presenter = ContactDetailPresenter()
        presenter.attachView(this)
        presenter.setContactId(intent.getIntExtra(ARG_ID, -1))
        fab.setOnClickListener { view ->
            EditContactActivity.startAsIntent(this, intent.getIntExtra(ARG_ID, -1))
        }
    }

    override fun onContactDeleted() {

    }

    override fun onLoadingStarted() {
    }

    override fun onLoadingFinished(result: Contact) {
        contact_name.text = "${result.name} ${result.lastname}"
        contact_phone.text = ""
        contact_email.text = result.email
    }

    override fun onLoadingError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }
}
