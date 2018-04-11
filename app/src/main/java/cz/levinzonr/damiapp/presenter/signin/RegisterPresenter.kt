package cz.levinzonr.damiapp.presenter.signin

import cz.levinzonr.damiapp.model.remote.PostObject
import cz.levinzonr.damiapp.model.remote.Response
import cz.levinzonr.damiapp.utils.ErrorHandler
import cz.levinzonr.damiapp.view.unsigned.SignInView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class RegisterPresenter : SingInPresenter(){

    private var user = PostObject.Register()
    private var cd = CompositeDisposable()

    override fun validate() {
        if (user.passwordConfirm.isEmpty() || user.password.isEmpty()
        || user.email.isEmpty()) {
            view?.allowSignIn(false)
            view?.showHintMessage( SignInView.Status.EMPTY_FIELD)
        } else if (user.password != user.passwordConfirm) {
            view?.showHintMessage( SignInView.Status.PASSWORD_MISMATCH)
            view?.allowSignIn(false)
        } else {
            view?.allowSignIn(true)
        }
    }

    override fun startSignIn() {
        view?.onSingInStarted()
        cd.add(repository.register(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {responce: Response.OK? ->
                            Thread.sleep(1000)
                            view?.onSignInFinished(responce!!.response)},
                        {error: Throwable? ->
                            Thread.sleep(1000)
                            view?.onSignInError(ErrorHandler().handleError(error!!))  }
                ))
    }

    fun setEmail(text: String) {
        user.email = text
        validate()
    }

    fun setPassword(text: String) {
        user.password = text
        validate()
    }

    fun setPasswordConfirm(text: String) {
        user.passwordConfirm = text
        validate()
    }

}