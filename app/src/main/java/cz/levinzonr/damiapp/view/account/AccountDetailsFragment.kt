package cz.levinzonr.damiapp.view.account


import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import cz.levinzonr.damiapp.R
import cz.levinzonr.damiapp.model.entities.User
import cz.levinzonr.damiapp.presenter.AccountDetailsPresenter
import kotlinx.android.synthetic.main.fragment_account_details.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
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
        account_email.text = result.email
        account_description.text = (result.description ?: getString(R.string.unknown))
        account_name.text = (result.name ?: getString(R.string.unknown))
        account_phone.text = (result.phone ?: getString(R.string.unknown))
    }

    override fun onLoadingError(error: String) {
        Log.d(TAG, "Error: $error")
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}
