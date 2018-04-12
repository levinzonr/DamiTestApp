package cz.levinzonr.damiapp.view

interface BaseView<in V> {

    fun onLoadingStarted()

    fun onLoadingFinished(result : V)

    fun onLoadingError(error: String)

}