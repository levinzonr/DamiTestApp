package cz.levinzonr.damiapp.presenter

import android.util.Log
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import cz.levinzonr.damiapp.model.DamiRemoteDatasource
import cz.levinzonr.damiapp.model.entities.PostObject
import cz.levinzonr.damiapp.model.entities.Response
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
                        { t: Response? ->  view?.onLoginFinished(t!!.response)},
                        {t: Throwable? ->
                            view?.onLoginError(t.toString())
                            if (t is HttpException) {
                                val a = t.response().errorBody().string()
                                val reps = Gson().fromJson(a, Response::class.java)
                                Log.d("EROR: ", reps.responseCodeText)
                            }
                        }
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