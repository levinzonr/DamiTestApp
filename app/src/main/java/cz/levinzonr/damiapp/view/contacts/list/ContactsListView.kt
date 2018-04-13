package cz.levinzonr.damiapp.view.contacts.list

import cz.levinzonr.damiapp.model.entities.Contact
import cz.levinzonr.damiapp.view.BaseView

interface ContactsListView : BaseView<ArrayList<Contact>> {

    fun onEmptyView()

}