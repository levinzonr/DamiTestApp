package cz.levinzonr.damiapp.view.login


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import cz.levinzonr.damiapp.R
import cz.levinzonr.damiapp.extensions.EditTextListener
import cz.levinzonr.damiapp.model.entities.User
import cz.levinzonr.damiapp.presenter.LoginPresenter
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment(), LoginView {

    private lateinit var presenter: LoginPresenter
    private lateinit var progressDialog: AlertDialog

    companion object {
        const val TAG = "LoginFragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        progressDialog = AlertDialog.Builder(context)
                .setView(inflater.inflate(R.layout.dialog_progress, container, false))
                .setCancelable(false)
                .create()
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = LoginPresenter()
        presenter.attachView(this)

        input_email.addTextChangedListener(EditTextListener{presenter.setEmail(it)})
        input_password.addTextChangedListener(EditTextListener{presenter.setPassword(it)})

        button_login.setOnClickListener({
            presenter.startSignIn()
        })

    }

    override fun onLoginStart() {
        Log.d(TAG, "Start: ")
        progressDialog.show()
    }

    override fun onLoginFinished(user: User) {
        Log.d(TAG, "Finished: ${user.token}")
        progressDialog.cancel()
    }

    override fun onLoginError(error: String) {
        progressDialog.cancel()
        val dialog = AlertDialog.Builder(context)
                .setTitle(R.string.error_title_login)
                .setMessage(error)
                .setNeutralButton(android.R.string.ok, {_, _ -> })
                .create()
        dialog.show()

    }

    override fun enableLoginButton(enable: Boolean) {
        button_login.isEnabled = enable
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

}
