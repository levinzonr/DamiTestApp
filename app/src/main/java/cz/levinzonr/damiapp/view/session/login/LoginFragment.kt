package cz.levinzonr.damiapp.view.session.login


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import cz.levinzonr.damiapp.R
import cz.levinzonr.damiapp.extensions.EditTextListener
import cz.levinzonr.damiapp.presenter.LoginPresenter
import cz.levinzonr.damiapp.view.session.BaseSignInFragment
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : BaseSignInFragment() {

    private lateinit var presenter: LoginPresenter

    companion object {
        const val TAG = "LoginFragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
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

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun allowSignIn(enable: Boolean) {
        button_login.isEnabled = enable
    }
}