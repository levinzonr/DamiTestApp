package cz.levinzonr.damiapp.presenter

import cz.levinzonr.damiapp.view.LoginView

class LoginPresenter : Presenter<LoginView> {

    private var view: LoginView? = null

    override fun attachView(view: LoginView) {
        this.view = view
    }

    override fun detachView(view: LoginView) {
        this.view = null
    }
}