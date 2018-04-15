package cz.levinzonr.damiapp.presenter.contacts

import cz.levinzonr.damiapp.model.Repository
import cz.levinzonr.damiapp.model.entities.Contact
import cz.levinzonr.damiapp.model.remote.Response
import cz.levinzonr.damiapp.presenter.Presenter
import cz.levinzonr.damiapp.view.contacts.edit.ContactEditView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ContactEditPresenter : Presenter<ContactEditView> {

    private var view: ContactEditView? =  null
    private val repository = Repository()
    private val cd = CompositeDisposable()

    private lateinit var contact: Contact

    override fun attachView(view: ContactEditView) {
        this.view = view
    }

    fun setContactId(id: Int) {
        if (id == -1) {
            view?.onNewContact()
            contact = Contact()
        } else {
            getContactById(id)
        }
    }

   private fun getContactById(id: Int) {
        cd.add(repository.getContactById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({t: Response<Contact>? ->
                    contact = t!!.response
                    view?.onLoadingFinished(t.response) }))
    }

    fun setName(text: String) {
        contact.name = text
        validate()
    }

    fun setEmail(text: String) {
        contact.email = text
        validate()
    }

    fun setPhone(text: String) {
       // contact.p
    }

    fun setLastname(text: String) {
        contact.lastname = text
    }

    private fun validate() {
        if (contact.email.isEmpty()) {
            view?.showInputHint(false, ContactEditView.STATUS.MAIL_EMPTY)
        } else {
            view?.hideInputHint()   
        }
    }

    override fun detachView() {
        view = null
        if (!cd.isDisposed) cd.dispose()
    }
}