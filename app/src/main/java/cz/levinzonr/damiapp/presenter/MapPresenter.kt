package cz.levinzonr.damiapp.presenter

import cz.levinzonr.damiapp.model.Repository
import cz.levinzonr.damiapp.model.entities.MapPoint
import cz.levinzonr.damiapp.model.remote.Response
import cz.levinzonr.damiapp.utils.ErrorHandler
import cz.levinzonr.damiapp.view.map.MapView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MapPresenter : Presenter<MapView>{

    private var view: MapView? = null
    private val repository = Repository()
    private val cd = CompositeDisposable()

    override fun attachView(view: MapView) {
       this.view = view
        getPointsOnMap()
    }

    fun getPointsOnMap() {
        view?.onPointsLoadingStarted()
        cd.add(repository.getPointsOnMap()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({resp: Response<ArrayList<MapPoint>>? -> view?.onPointsLoaded(resp!!.response) },
                        {error: Throwable? ->  view?.onPointsLoadingError(ErrorHandler().handleError(error!!))}

                ))
    }

    override fun detachView() {
        view = null
        if (!cd.isDisposed) cd.dispose()
    }
}