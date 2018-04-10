package cz.levinzonr.damiapp.presenter.signin

import cz.levinzonr.damiapp.model.DamiRemoteDatasource
import cz.levinzonr.damiapp.model.entities.PostObject
import cz.levinzonr.damiapp.model.entities.Response
import cz.levinzonr.damiapp.utils.ErrorHandler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class RegisterPresenter : SingInPresenter(){

    private var user = PostObject.Register()
    private var cd = CompositeDisposable()
    private val remote = DamiRemoteDatasource()

    override fun validate() {

        view?.allowSignIn(
                user.email.isNotEmpty() &&
                        user.password.isNotEmpty() &&
                        user.passwordConfirm.isNotEmpty() &&
                        user.passwordConfirm == user.password
        )
    }

    override fun startSignIn() {
        view?.onSingInStarted()
        cd.add(remote.userRegister(user)
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
    }

    fun setPassword(text: String) {
        user.password = text
    }

    fun setPasswordConfirm(text: String) {
        user.passwordConfirm = text
    }

}