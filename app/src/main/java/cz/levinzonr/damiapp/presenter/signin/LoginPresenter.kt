package cz.levinzonr.damiapp.presenter.signin

import cz.levinzonr.damiapp.utils.ErrorHandler
import cz.levinzonr.damiapp.model.DamiRemoteDatasource
import cz.levinzonr.damiapp.model.entities.PostObject
import cz.levinzonr.damiapp.model.entities.Response
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class LoginPresenter : SingInPresenter(){

    private var loginObject = PostObject.Login()
    private var cd = CompositeDisposable()
    private val remote = DamiRemoteDatasource()



    override fun startSignIn() {
        view?.onSingInStarted()
        cd.add(remote.userLogin(loginObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { t: Response? ->          Thread.sleep(1000)
                            view?.onSignInFinished(t!!.response)},
                        {t: Throwable? ->         Thread.sleep(1000)
                             view?.onSignInError(ErrorHandler().handleError(t!!))
                        }
                )
        )
    }

    override fun validate() {
        view?.allowSignIn(
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