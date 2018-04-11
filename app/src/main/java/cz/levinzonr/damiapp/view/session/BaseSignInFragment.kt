package cz.levinzonr.damiapp.view.session


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import cz.levinzonr.damiapp.R
import cz.levinzonr.damiapp.model.entities.User

abstract class BaseSignInFragment : Fragment(), SignInView{

    private lateinit var progressDialog: AlertDialog
    lateinit var listener : SignInInteractionListener

    interface SignInInteractionListener {
        fun registerMode(boolean: Boolean)
        fun finishLogin()
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressDialog = AlertDialog.Builder(context)
                .setView(LayoutInflater.from(context).inflate(R.layout.dialog_progress, null, false))
                .setCancelable(false)
                .create()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        listener = context as SignInInteractionListener
    }

    override fun onSingInStarted() {
        progressDialog.show()
    }

    override fun onSignInFinished(user: User) {
        progressDialog.hide()
        listener.finishLogin()
    }

    override fun onSignInError(error: String) {
        progressDialog.hide()
        val dialog = AlertDialog.Builder(context)
                .setNeutralButton(android.R.string.ok, {_, _ -> })
                .setTitle(R.string.error_title_login)
                .setMessage(error)
                .create()
        dialog.show()
    }
}
