package cz.levinzonr.damiapp.presenter

import cz.levinzonr.damiapp.model.Repository
import cz.levinzonr.damiapp.model.entities.Contact
import cz.levinzonr.damiapp.model.remote.Response
import cz.levinzonr.damiapp.utils.ErrorHandler
import cz.levinzonr.damiapp.view.contacts.ContactsListView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ContactsListPresenter : Presenter<ContactsListView> {

    private val repository = Repository()
    private val cd = CompositeDisposable()
    private var view : ContactsListView? = null

    override fun attachView(view: ContactsListView) {
        this.view = view
    }

    fun getContacts() {
        view?.onLoadingStarted()
        cd.add(repository.getContacts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { t: Response<ArrayList<Contact>>? -> view?.onLoadingFinished(t!!.response)  },
                        { e: Throwable? -> view?.onLoadingError(ErrorHandler().handleError(e!!))}
                ))
    }

    override fun detachView() {
        view = null
        if (!cd.isDisposed) cd.dispose()
    }
}