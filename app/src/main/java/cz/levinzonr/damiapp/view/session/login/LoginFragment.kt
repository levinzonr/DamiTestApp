package cz.levinzonr.damiapp.view.session.login


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import cz.levinzonr.damiapp.R
import cz.levinzonr.damiapp.extensions.EditTextListener
import cz.levinzonr.damiapp.model.entities.User
import cz.levinzonr.damiapp.presenter.signin.LoginPresenter
import cz.levinzonr.damiapp.view.session.BaseSignInFragment
import cz.levinzonr.damiapp.view.session.SignInView
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

        button_register.setOnClickListener({
            listener.registerMode(true)
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun allowSignIn(enable: Boolean) {
        button_login.isEnabled = enable
    }

    override fun onSignInError(error: String) {
        super.onSignInError(error)
        Log.d(TAG, "Error: $error")
    }

    override fun onSignInFinished(user: User) {
        super.onSignInFinished(user)
        Log.d(TAG,"Done: ${user.token}")
    }

    override fun showHintMessage(status: SignInView.Status) {
        hint_view.visibility = View.VISIBLE
        when(status) {
            SignInView.Status.EMPTY_FIELD -> hint_view.text = "Empty field"
            SignInView.Status.PASSWORD_MISMATCH -> hint_view.text = "Mismatch"

        }
    }


}
