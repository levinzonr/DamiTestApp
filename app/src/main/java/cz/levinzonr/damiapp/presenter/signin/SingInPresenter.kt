package cz.levinzonr.damiapp.presenter.signin

import android.content.Context
import cz.levinzonr.damiapp.model.Repository
import cz.levinzonr.damiapp.presenter.Presenter
import cz.levinzonr.damiapp.view.session.SignInView

abstract class SingInPresenter(context: Context) : Presenter<SignInView> {

    val repository = Repository(context)
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