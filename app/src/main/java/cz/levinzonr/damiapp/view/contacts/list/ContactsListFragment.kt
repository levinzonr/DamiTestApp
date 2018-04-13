package cz.levinzonr.damiapp.view.contacts.list


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import cz.levinzonr.damiapp.R
import cz.levinzonr.damiapp.model.entities.Contact
import cz.levinzonr.damiapp.presenter.contacts.ContactsListPresenter
import cz.levinzonr.damiapp.view.contacts.edit.EditContactActivity
import kotlinx.android.synthetic.main.fragment_contacts_list.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class ContactsListFragment : Fragment(), ContactsListView, ContactsListAdapter.OnItemClickListener{

    private lateinit var presenter: ContactsListPresenter
    private lateinit var contactsAdapter: ContactsListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contacts_list, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = ContactsListPresenter()
        presenter.attachView(this)
        presenter.getContacts()
        contactsAdapter = ContactsListAdapter(presenter, this)
        recycler_view.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = contactsAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }

        button_create.setOnClickListener({
            EditContactActivity.startAsIntent(context, null)
        })

    }

    override fun onEmptyView() {
        Toast.makeText(context, "Empty", Toast.LENGTH_SHORT).show()
    }

    override fun onItemSelected(contact: Contact) {
        EditContactActivity.startAsIntent(context, contact.id)
    }

    override fun onLoadingStarted() {
        Toast.makeText(context, "start", Toast.LENGTH_SHORT).show()
        recycler_view.visibility = View.GONE
        progress_bar.visibility = View.VISIBLE
    }

    override fun onLoadingFinished(result: ArrayList<Contact>) {
        Toast.makeText(context, "Done", Toast.LENGTH_SHORT).show()
        progress_bar.visibility = View.GONE
        recycler_view.visibility = View.VISIBLE
    }

    override fun onLoadingError(error: String) {
        Toast.makeText(context, "Error $error", Toast.LENGTH_SHORT).show()
    }



    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}
