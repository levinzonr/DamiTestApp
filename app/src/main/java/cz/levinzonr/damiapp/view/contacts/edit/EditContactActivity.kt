package cz.levinzonr.damiapp.view.contacts.edit

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import cz.levinzonr.damiapp.R
import cz.levinzonr.damiapp.extensions.EditTextListener
import cz.levinzonr.damiapp.model.entities.Contact
import cz.levinzonr.damiapp.presenter.contacts.ContactEditPresenter

import kotlinx.android.synthetic.main.activity_edit_contact.*
import kotlinx.android.synthetic.main.content_edit_contact.*

class EditContactActivity : AppCompatActivity(), ContactEditView {

    private lateinit var presenter: ContactEditPresenter

    companion object {
        const val TAG = "EditContacActivity"
        const val ARG_ID = "ContactId"

        fun startAsIntent(context: Context, contactID : Int?) {
            val intent = Intent(context, EditContactActivity::class.java)
            intent.putExtra(ARG_ID, contactID ?: -1)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_contact)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar?.setNavigationOnClickListener { onBackPressed() }
        presenter = ContactEditPresenter()
        presenter.attachView(this)
        presenter.setContactId(intent.getIntExtra(ARG_ID, -1))

        contact_input_email.addTextChangedListener(EditTextListener{presenter.setEmail(it)})
        contact_input_name.addTextChangedListener(EditTextListener{presenter.setName(it)})
        contact_input_lastname.addTextChangedListener(EditTextListener{presenter.setLastname(it)})
        contact_input_phone.addTextChangedListener(EditTextListener{presenter.setPhone(it)})
    }

    override fun onNewContact() {
    }

    override fun hideInputHint() {
        hint_view.visibility = View.GONE
    }

    override fun showInputHint(show: Boolean, status: ContactEditView.STATUS) {
      hint_view.visibility = View.VISIBLE
      when(status) {
          ContactEditView.STATUS.MAIL_EMPTY -> "Email empty"
      }
    }

    override fun onLoadingStarted() {
        supportActionBar?.title = "Edit"
    }

    override fun onLoadingFinished(result: Contact) {
        supportActionBar?.title = result.name
        contact_input_phone.setText(result.phone)
        contact_input_email.setText(result.email)
        contact_input_lastname.setText(result.lastname)
        contact_input_name.setText(result.name)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_contact_edit, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
       return when(item?.itemId) {
            R.id.button_post -> {
                presenter.postContact()
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onLoadingError(error: String) {
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun onChangesLoaded() {
        onBackPressed()
        Toast.makeText(this, "Changes uploaded", Toast.LENGTH_SHORT).show()
    }

    override fun onChangesError(e: String) {
        Toast.makeText(this, "Changes faiked: $e", Toast.LENGTH_SHORT).show()
    }

}
