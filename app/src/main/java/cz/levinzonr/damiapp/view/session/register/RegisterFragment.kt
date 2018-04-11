package cz.levinzonr.damiapp.view.session.register


import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import cz.levinzonr.damiapp.R
import cz.levinzonr.damiapp.extensions.EditTextListener
import cz.levinzonr.damiapp.model.entities.User
import cz.levinzonr.damiapp.presenter.signin.RegisterPresenter
import cz.levinzonr.damiapp.view.session.BaseSignInFragment
import cz.levinzonr.damiapp.view.session.SignInView
import kotlinx.android.synthetic.main.fragment_register.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class RegisterFragment : BaseSignInFragment() {

    private lateinit var presenter: RegisterPresenter

    companion object {
        const val TAG = "RegisterFragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = RegisterPresenter()
        presenter.attachView(this)
        input_email.addTextChangedListener(EditTextListener{presenter.setEmail(it)})
        input_password.addTextChangedListener(EditTextListener{presenter.setPassword(it)})
        input_password_confirm.addTextChangedListener(EditTextListener{presenter.setPasswordConfirm(it)})
        button_register.setOnClickListener({
            presenter.startSignIn()
        })

    }

    override fun allowSignIn(enable: Boolean) {
        Log.d(TAG, "Enabled: $enable")
        button_register.isEnabled = enable
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun onSignInError(error: String) {
        super.onSignInError(error)
        Log.d(TAG, "Error: $error")
    }

    override fun onSignInFinished(user: User) {
        super.onSignInFinished(user)
        Log.d(TAG,"Done: ${user.token}")
    }

    override fun showHintMessage( status: SignInView.Status) {
        hint_view.visibility = View.VISIBLE
        when(status) {
            SignInView.Status.EMPTY_FIELD -> hint_view.text = "Empty field"
            SignInView.Status.PASSWORD_MISMATCH -> hint_view.text = "Mismatch"
        }
    }

}
