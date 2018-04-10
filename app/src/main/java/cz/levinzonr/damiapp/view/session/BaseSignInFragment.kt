package cz.levinzonr.damiapp.view.session


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import cz.levinzonr.damiapp.R
import cz.levinzonr.damiapp.model.entities.User

abstract class BaseSignInFragment : Fragment(), SignInView{

    private lateinit var progressDialog: AlertDialog

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressDialog = AlertDialog.Builder(context)
                .setView(LayoutInflater.from(context).inflate(R.layout.dialog_progress, null, false))
                .setCancelable(false)
                .create()
    }

    override fun onSingInStarted() {
        progressDialog.show()
    }

    override fun onSignInFinished(user: User) {
        progressDialog.hide()
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
