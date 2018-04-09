package cz.levinzonr.damiapp.presenter

import cz.levinzonr.damiapp.model.DamiRemoteDatasource
import cz.levinzonr.damiapp.model.entities.PostObject
import cz.levinzonr.damiapp.model.entities.User
import cz.levinzonr.damiapp.view.LoginView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class LoginPresenter : Presenter<LoginView> {

    private var view: LoginView? = null
    private var loginObject = PostObject.Login()
    private var cd = CompositeDisposable()
    private val remote = DamiRemoteDatasource()

    override fun attachView(view: LoginView) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }

    fun startSignIn() {
        view?.onLoginStart()
        cd.add(remote.userLogin(loginObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { t: User? ->  view?.onLoginFinished(t!!)},
                        {t: Throwable? -> view?.onLoginError(t.toString())  }
                )
        )
    }

    private fun validate() {
        view?.enableLoginButton(
                loginObject.password.isNotEmpty() && loginObject.email.isNotEmpty())
    }

    fun setEmail(text: String) {
        loginObject.email = text
        validate()
    }

    fun setPassword(text: String) {
        loginObject.password = text
        validate()
    }

}