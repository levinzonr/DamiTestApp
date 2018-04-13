package cz.levinzonr.damiapp.view.contacts.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cz.levinzonr.damiapp.R
import cz.levinzonr.damiapp.model.entities.Contact
import cz.levinzonr.damiapp.presenter.ContactsListPresenter
import kotlinx.android.synthetic.main.item_contact.view.*

class ContactsListAdapter(val presenter: ContactsListPresenter) :
        RecyclerView.Adapter<ContactsListAdapter.ViewHolder>(){

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view), RecycledItemView {

        override fun set(contact: Contact) {
            view.contact_name.text = "${contact.name} ${contact.lastname} "
        }
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        presenter.bindItemAtPosition(position, holder!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_contact, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = presenter.itemsCount()




}