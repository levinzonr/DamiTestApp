package cz.levinzonr.damiapp.presenter.signin

import cz.levinzonr.damiapp.model.Repository
import cz.levinzonr.damiapp.presenter.Presenter
import cz.levinzonr.damiapp.view.unsigned.SignInView

abstract class SignInPresenter : Presenter<SignInView> {

    val repository = Repository()
    var view: SignInView? = null

    override fun attachView(view: SignInView) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }

    abstract fun validate()

    abstract fun startSignIn()

}