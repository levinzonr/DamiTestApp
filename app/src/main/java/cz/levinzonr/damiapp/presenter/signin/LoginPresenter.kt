package cz.levinzonr.damiapp.presenter.signin

import cz.levinzonr.damiapp.model.entities.User
import cz.levinzonr.damiapp.utils.ErrorHandler
import cz.levinzonr.damiapp.model.remote.PostObject
import cz.levinzonr.damiapp.model.remote.Response
import cz.levinzonr.damiapp.view.unsigned.SignInView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class LoginPresenter: SignInPresenter(){

    private var loginObject = PostObject.Login()
    private var cd = CompositeDisposable()



    override fun startSignIn() {
        view?.onSingInStarted()
        cd.add(repository.login(loginObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { t: Response<User>? ->          Thread.sleep(1000)
                            view?.onSignInFinished(t!!.response)},
                        {t: Throwable? ->         Thread.sleep(1000)
                             view?.onSignInError(ErrorHandler().handleError(t!!))
                        }
                )
        )
    }

    override fun validate() {
        if (loginObject.password.isEmpty() || loginObject.email.isEmpty()) {
            view?.allowSignIn(false)
            view?.showHintMessage(SignInView.Status.EMPTY_FIELD)
        } else {
            view?.allowSignIn(true)
        }
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