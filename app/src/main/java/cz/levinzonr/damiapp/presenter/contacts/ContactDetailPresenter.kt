package cz.levinzonr.damiapp.presenter.contacts

import cz.levinzonr.damiapp.model.Repository
import cz.levinzonr.damiapp.model.entities.Contact
import cz.levinzonr.damiapp.presenter.Presenter
import cz.levinzonr.damiapp.utils.ErrorHandler
import cz.levinzonr.damiapp.view.contacts.detail.ContactDetailView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ContactDetailPresenter : Presenter<ContactDetailView> {

    private val repository = Repository()
    private var contactId: Int = -1
    private var view: ContactDetailView? = null
    private val cd = CompositeDisposable()

    override fun attachView(view: ContactDetailView) {
        this.view = view
    }

    fun setContactId(id: Int) {
        contactId = id
        view?.onLoadingStarted()
        cd.add(repository.getContactById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {res: Contact? -> view?.onLoadingFinished(res!!) },
                        {t: Throwable? -> view?.onLoadingError(ErrorHandler().handleError(t!!)) }
                ))

    }

    fun deleteContact() {
        if (contactId != -1) {
            view?.onContactDeleted()
        }
    }

    override fun detachView() {
        this.view = null
        if(!cd.isDisposed)cd.dispose()
    }
}