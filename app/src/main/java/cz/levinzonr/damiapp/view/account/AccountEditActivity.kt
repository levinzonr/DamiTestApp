package cz.levinzonr.damiapp.view.account

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import cz.levinzonr.damiapp.R
import cz.levinzonr.damiapp.extensions.EditTextListener
import cz.levinzonr.damiapp.extensions.onTextChange
import cz.levinzonr.damiapp.extensions.validEmail
import cz.levinzonr.damiapp.model.entities.User
import cz.levinzonr.damiapp.presenter.account.AccountEditPresenter

import kotlinx.android.synthetic.main.activity_account_edit.*
import kotlinx.android.synthetic.main.content_account_edit.*

class AccountEditActivity : AppCompatActivity(), AccountEditView {

    private lateinit var presenter: AccountEditPresenter

    companion object {
        const val TAG = "EditAccountActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_edit)
        setSupportActionBar(toolbar)
        presenter = AccountEditPresenter()
        presenter.attachView(this)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressed() }
        input_lastname.onTextChange { presenter.setLastname(it) }
        input_phone.onTextChange { presenter.setPhone(it) }
        input_name.onTextChange { presenter.setName(it) }
        input_email.onTextChange {
            input_email.error = if (it.validEmail()) null else getString(R.string.error_invalid_email)
            presenter.setEmail(it)
        }
        input_desc.onTextChange { presenter.setDescription(it) }


        fab.setOnClickListener { view ->
            if (input_email.error == null)
                 presenter.postChanges()
            else Snackbar.make(view, R.string.error_invalid_field, Snackbar.LENGTH_SHORT).show()
        }
    }


    override fun onPostChangesError(error: String) {
        Log.d(TAG, "PostError: $error")
        AlertDialog.Builder(this)
                .setTitle(R.string.error_title)
                .setMessage(error)
                .setNeutralButton(android.R.string.ok, { _, _ ->  })
                .create().show()
    }

    override fun onPostChangesSuccess() {
        Log.d(TAG, "Updated")
        Toast.makeText(this, getString(R.string.post_success), Toast.LENGTH_SHORT).show()
        onBackPressed()
    }

    override fun onInputError() {
    }

    override fun onLoadingStarted() {
        Log.d(TAG, "User loadedin")
    }

    override fun onLoadingFinished(result: User) {
        Log.d(TAG, "user loaded ")
        input_desc.setText(result.description)
        input_email.setText(result.email)
        input_name.setText(result.name)
        input_phone.setText(result.phone)
        input_lastname.setText(result.lastname)
    }

    override fun onLoadingError(error: String) {
        Log.d(TAG, "LoadEror: $error")

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

}
