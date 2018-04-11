package cz.levinzonr.damiapp.presenter

import cz.levinzonr.damiapp.model.Repository
import cz.levinzonr.damiapp.model.entities.User
import cz.levinzonr.damiapp.view.signed.MainView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainPresenter : Presenter<MainView> {

    private val repository = Repository()
    private var view: MainView? = null
    private val cd = CompositeDisposable()

    override fun attachView(view: MainView) {
        this.view = view
    }

    fun getUserInfo() {
        cd.add(repository.getCurrentUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({user: User? -> if (user!=null)  view?.onUserInfoLoaded(user) }))
    }

    fun logout() {
        cd.add(repository.logout()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ view?.onLogoutComplete()}))
    }

    override fun detachView() {
        view = null
        if (!cd.isDisposed) cd.dispose()
    }



}