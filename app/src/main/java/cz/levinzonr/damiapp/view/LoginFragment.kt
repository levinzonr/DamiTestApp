package cz.levinzonr.damiapp.view


import android.nfc.Tag
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import cz.levinzonr.damiapp.R
import cz.levinzonr.damiapp.model.entities.User
import cz.levinzonr.damiapp.presenter.LoginPresenter
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment(), LoginView {

    private lateinit var presenter: LoginPresenter

    companion object {
        const val TAG = "LoginFragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = LoginPresenter()
        presenter.attachView(this)

        input_email.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                presenter.setEmail(p0.toString())
            }
        })

        input_password.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                presenter.setPassword(p0.toString())
            }
        })

        button_login.setOnClickListener({
            presenter.startSignIn()
        })

    }

    override fun onLoginStart() {
        Log.d(TAG, "Start: ")
    }

    override fun onLoginFinished(user: User) {
        Log.d(TAG, "Finished: ${user.token}")
    }

    override fun onLoginError(error: String) {
        Log.d(TAG, "Error: $error")
    }

    override fun enableLoginButton(enable: Boolean) {
        button_login.isEnabled = enable
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

}
