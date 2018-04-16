package cz.levinzonr.damiapp.view.contacts.edit

import cz.levinzonr.damiapp.model.entities.Contact
import cz.levinzonr.damiapp.view.BaseView

interface ContactEditView : BaseView<Contact> {

    enum class STATUS {
        MAIL_EMPTY, MAIL_INVALID, OK
    }

    fun onNewContact()

    fun showInputHint(show: Boolean, status: STATUS)

    fun hideInputHint()

    fun onChangesLoaded()

    fun onChangesError(e: String)

}