package aut.arch.networking.interactors

import aut.arch.local_data.models.Shot
import aut.arch.networking.apis.TrainingApi
import aut.arch.networking.models.SetupModel
import aut.arch.networking.models.ShotModel
import aut.arch.networking.models.TeamModel
import aut.arch.networking.models.TrainingModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TrainingInteractor(private val trainingApi: TrainingApi) {

    fun createTeam(team: TeamModel): Single<String> {
        return trainingApi.createTeam(team)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun addUserToTeam(teamId: String, userId: String): Single<Unit> {
        return trainingApi.addUserToTeam(teamId, userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun removeUserFromTeam(teamId: String, userId: String): Single<Unit> {
        return trainingApi.removeUserFromTeam(teamId, userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getAllTeam(): Single<List<TeamModel>> {
        return trainingApi.getAllTeam()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getTeamsOfUser(userId: String): Single<List<String>> {
        return trainingApi.getTeamsOfUser(userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getTeam(id: String): Single<TeamModel> {
        return trainingApi.getTeam(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun createShot(shot: ShotModel): Single<String> {
        return trainingApi.createShot(shot)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun createSetup(setup: SetupModel): Single<String> {
        return trainingApi.createSetup(setup)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getAllSetup(): Single<List<SetupModel>> {
        return trainingApi.getAllSetup()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getSetup(id: String): Single<SetupModel> {
        return trainingApi.getSetup(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun createTraining(training: TrainingModel): Single<String> {
        return trainingApi.createTraining(training)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getTraining(id: String): Single<TrainingModel> {
        return trainingApi.getTraining(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getShot(id: String): Single<ShotModel>{
        return trainingApi.getShot(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun deleteTraining(id: Long) {
        val mappedId = id.toString()
        trainingApi.deleteTraining(mappedId)
    }

}