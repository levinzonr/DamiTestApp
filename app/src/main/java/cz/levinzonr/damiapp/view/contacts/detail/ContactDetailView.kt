package cz.levinzonr.damiapp.view.contacts.detail

import cz.levinzonr.damiapp.model.entities.Contact
import cz.levinzonr.damiapp.view.BaseView

interface ContactDetailView : BaseView<Contact> {

    fun onContactDeleted()

}