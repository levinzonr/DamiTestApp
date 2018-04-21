package cz.levinzonr.damiapp.presenter.account

import cz.levinzonr.damiapp.model.Repository
import cz.levinzonr.damiapp.model.entities.User
import cz.levinzonr.damiapp.model.remote.Response
import cz.levinzonr.damiapp.presenter.Presenter
import cz.levinzonr.damiapp.utils.ErrorHandler
import cz.levinzonr.damiapp.view.account.AccountEditView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class AccountEditPresenter : Presenter<AccountEditView> {

    private val repository = Repository()
    private val cd = CompositeDisposable()
    private var view: AccountEditView? = null
    private lateinit var user: User

    override fun attachView(view: AccountEditView) {
        this.view = view
        getCurrentUser()
    }

    private fun getCurrentUser() {
        cd.add(repository.getCurrentUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({res: User? -> res?.let { user = res; view?.onLoadingFinished(res) }}))
    }


    fun postChanges() {
        cd.add(repository.updateAccount(user, null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {t: Response<User>? -> view?.onPostChangesSuccess() },
                        {t: Throwable? -> t?.let { view?.onPostChangesError(ErrorHandler().handleError(t)) } }
                ))
    }


    fun setName(name: String) {
        user.name = name
    }

    fun setLastname(lastname: String) {
        user.lastname = lastname
    }

    fun setEmail(email: String){
        user.email = email
    }

    fun setPhone(phone: String) {
        user.phone = phone
    }

    fun setDescription(desc: String) {
        user.description = desc
    }


    override fun detachView() {
        if (!cd.isDisposed)cd.dispose()
        view = null
    }
}