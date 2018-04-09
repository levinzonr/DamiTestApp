package cz.levinzonr.damiapp.presenter

import cz.levinzonr.damiapp.model.entities.PostObject
import cz.levinzonr.damiapp.view.LoginView

class LoginPresenter : Presenter<LoginView> {

    private var view: LoginView? = null
    private var loginObject = PostObject.Login()

    override fun attachView(view: LoginView) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
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