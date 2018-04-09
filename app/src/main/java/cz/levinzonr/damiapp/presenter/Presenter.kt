package cz.levinzonr.damiapp.presenter

interface Presenter<in V> {

    fun attachView(view : V)

    fun detachView()

}