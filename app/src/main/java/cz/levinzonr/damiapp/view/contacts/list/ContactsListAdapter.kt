package cz.levinzonr.damiapp.view.contacts.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import cz.levinzonr.damiapp.R
import cz.levinzonr.damiapp.extensions.letText
import cz.levinzonr.damiapp.model.entities.Contact
import cz.levinzonr.damiapp.presenter.contacts.ContactsListPresenter
import kotlinx.android.synthetic.main.item_contact.view.*

class ContactsListAdapter(val presenter: ContactsListPresenter, val listener: OnItemClickListener ) :
        RecyclerView.Adapter<ContactsListAdapter.ViewHolder>(){

    interface OnItemClickListener {
        fun onItemSelected(contact: Contact)
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view), RecycledItemView {

        override fun set(contact: Contact) {
            view.contact_name.letText(contact.displayName())
            Picasso.get().load(contact.photo)
                    .error(R.mipmap.ic_launcher)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(view.contact_image)
            view.setOnClickListener({
                listener.onItemSelected(contact)
            })
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