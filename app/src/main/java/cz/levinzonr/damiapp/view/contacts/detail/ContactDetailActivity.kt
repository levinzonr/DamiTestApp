package cz.levinzonr.damiapp.view.contacts.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.squareup.picasso.Picasso
import cz.levinzonr.damiapp.R
import cz.levinzonr.damiapp.extensions.letText
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
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressed() }
        presenter = ContactDetailPresenter()
        presenter.attachView(this)
        presenter.setContactId(intent.getIntExtra(ARG_ID, -1))
        fab.setOnClickListener { view ->
            EditContactActivity.startAsIntent(this, intent.getIntExtra(ARG_ID, -1))
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_contact_detail, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId) {
            R.id.button_delete -> {
                val d = AlertDialog.Builder(this)
                        .setTitle(R.string.title_contact_delete)
                        .setMessage(R.string.contact_delete_message)
                        .setNegativeButton(android.R.string.no, {_, _ ->})
                        .setPositiveButton(android.R.string.yes, {_, _ -> presenter.deleteContact()})
                        .create()
                d.show()
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onContactDeleted() {
        Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show()
        onBackPressed()
    }

    override fun onLoadingStarted() {
    }

    override fun onLoadingFinished(result: Contact) {
        contact_name.letText( result.displayName())
        contact_phone.letText(result.phone)
        contact_email.letText( result.email)
        Picasso.get().load(result.photo)
                .error(R.mipmap.ic_launcher)
                .placeholder(R.mipmap.ic_launcher)
                .into(contact_image)

    }

    override fun onLoadingError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }
}
