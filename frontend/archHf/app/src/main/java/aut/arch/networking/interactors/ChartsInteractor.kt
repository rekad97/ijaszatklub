package aut.arch.networking.interactors

import aut.arch.networking.apis.ChartsApi
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ChartsInteractor(val chartsApi: ChartsApi) {

    fun getUsersFromTeam(id: String): Single<List<String>> {
        return chartsApi.getUsersFromTeam(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getTrainingsOfUser(id: String): Single<List<String>> {
        return chartsApi.getTrainingsOfUser(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getShotsOfTraining(id: String): Single<List<String>> {
        return chartsApi.getShotsOfTraining(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}
