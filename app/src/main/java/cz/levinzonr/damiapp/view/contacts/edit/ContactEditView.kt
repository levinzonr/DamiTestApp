package cz.levinzonr.damiapp.view.contacts.edit

import cz.levinzonr.damiapp.model.entities.Contact
import cz.levinzonr.damiapp.view.BaseView

interface ContactEditView : BaseView<Contact> {

    enum class STATUS {
        MAIL_EMPTY, MAIL_INVALID
    }

    fun onContactCreate()

    fun setInputOk(valid: Boolean)

    fun showInputHint(status: STATUS)

    fun hideInputHint()

}