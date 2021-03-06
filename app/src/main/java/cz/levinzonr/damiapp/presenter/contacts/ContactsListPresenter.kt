package cz.levinzonr.damiapp.presenter.contacts

import android.util.Log
import cz.levinzonr.damiapp.model.Repository
import cz.levinzonr.damiapp.model.entities.Contact
import cz.levinzonr.damiapp.model.remote.Response
import cz.levinzonr.damiapp.presenter.Presenter
import cz.levinzonr.damiapp.utils.ErrorHandler
import cz.levinzonr.damiapp.view.contacts.list.ContactsListView
import cz.levinzonr.damiapp.view.contacts.list.RecycledItemView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ContactsListPresenter : Presenter<ContactsListView> {

    private val repository = Repository()
    private val cd = CompositeDisposable()
    private var view : ContactsListView? = null

    private lateinit var items: ArrayList<Contact>

    override fun attachView(view: ContactsListView) {
        this.view = view

    }

    fun updateContacts() {
       cd.add(repository.getContacts()
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe({t: List<Contact>? -> t?.let { items = ArrayList(t) }})
       )

    }

    fun getContacts() {
        view?.onLoadingStarted()
        cd.add(repository.getContacts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { t: List<Contact>? ->
                            if (t != null) {
                                items = ArrayList(t)
                                if (t.isEmpty())
                                    view?.onEmptyView()
                                else
                                    view?.onLoadingFinished(ArrayList(t))
                            }},
                        { e: Throwable? -> view?.onLoadingError(ErrorHandler().handleError(e!!))}
                ))
    }

    fun itemsCount() : Int = items.size

    fun bindItemAtPosition(position: Int, itemView: RecycledItemView) {
        val item = items[position]
        itemView.set(item)
    }

    override fun detachView() {
        view = null
        if (!cd.isDisposed) cd.dispose()
    }
}