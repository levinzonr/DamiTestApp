package cz.levinzonr.damiapp.view.account.edit

import cz.levinzonr.damiapp.model.entities.User
import cz.levinzonr.damiapp.view.BaseView

interface AccountEditView : BaseView<User> {

    fun onPostChangesError(error: String)

    fun onPostChangesSuccess()

    fun onInputError()

}
