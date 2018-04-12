package cz.levinzonr.damiapp.presenter

import cz.levinzonr.damiapp.model.Repository
import cz.levinzonr.damiapp.model.entities.User
import cz.levinzonr.damiapp.view.account.AccountDetailsView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class AccountDetailsPresenter : Presenter<AccountDetailsView>{

    private val repository = Repository()
    private var view : AccountDetailsView? = null
    private val cd = CompositeDisposable()

    override fun attachView(view: AccountDetailsView) {
        this.view = view
        getAccountDetails()
    }

    private fun getAccountDetails() {
        view?.onLoadingStarted()
        cd.add(repository.getCurrentUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({t: User? -> view?.onLoadingFinished(t!!) }))
    }

    override fun detachView() {
        view = null
        if (!cd.isDisposed) cd.dispose()
    }
}