package cz.levinzonr.damiapp.presenter

import cz.levinzonr.damiapp.model.Repository
import cz.levinzonr.damiapp.view.MainView

class MainPresenter : Presenter<MainView> {

    private val repository = Repository()
    private var view: MainView? = null

    override fun attachView(view: MainView) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }
}