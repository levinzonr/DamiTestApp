package cz.levinzonr.damiapp.view.account


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import cz.levinzonr.damiapp.R
import cz.levinzonr.damiapp.extensions.letText
import cz.levinzonr.damiapp.model.entities.User
import cz.levinzonr.damiapp.presenter.account.AccountDetailsPresenter
import kotlinx.android.synthetic.main.fragment_account_details.*

class AccountDetailsFragment : Fragment(), AccountDetailsView {

    private lateinit var presenter: AccountDetailsPresenter

    companion object {
        const val TAG = "AccountDetailsFragment"

        fun newInstance() : AccountDetailsFragment {
            val fragment = AccountDetailsFragment()
            fragment.arguments = Bundle()
            return fragment
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account_details, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = AccountDetailsPresenter()
        presenter.attachView(this)
    }

    override fun onLoadingStarted() {
        Log.d(TAG, "Started")
    }

    override fun onLoadingFinished(result: User) {
        account_email.letText( result.email)
        account_description.letText( result.description )
        account_name.letText(result.displayName())
        account_phone.letText(result.phone)
    }

    override fun onLoadingError(error: String) {
        Log.d(TAG, "Error: $error")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }
}
