package cz.levinzonr.damiapp.view.contacts.edit

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import cz.levinzonr.damiapp.R
import cz.levinzonr.damiapp.model.entities.Contact
import cz.levinzonr.damiapp.presenter.contacts.ContactEditPresenter

import kotlinx.android.synthetic.main.activity_edit_contact.*

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
        presenter = ContactEditPresenter()
        presenter.attachView(this)
        presenter.setContactId(intent.getIntExtra(ARG_ID, -1))

    }

    override fun onContactCreate() {
    }

    override fun setInputOk(valid: Boolean) {
    }

    override fun showInputHint(status: ContactEditView.STATUS) {
    }

    override fun hideInputHint() {
    }

    override fun onLoadingStarted() {
        supportActionBar?.title = "Edit"
    }

    override fun onLoadingFinished(result: Contact) {
        supportActionBar?.title = result.name
    }

    override fun onLoadingError(error: String) {
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

}
