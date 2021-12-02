package aut.arch.ui.fragments.training

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import aut.arch.local_data.dao.ArchDao
import aut.arch.local_data.models.Setup
import aut.arch.local_data.models.Shot
import aut.arch.local_data.models.Training
import aut.arch.networking.RetrofitFactory.ownUserId
import aut.arch.networking.interactors.TrainingInteractor
import aut.arch.networking.models.ShotModel
import aut.arch.networking.models.TrainingModel
import aut.arch.ui.fragments.SpinnerRemote
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class TrainingViewModel : ViewModel() {
    private lateinit var dao: ArchDao
    private lateinit var navController: NavController
    private lateinit var trainingInteractor: TrainingInteractor
    val popupNeeded = MutableLiveData<Unit>()
    val shot = MutableLiveData<Unit>()
    val setups = MutableLiveData<List<Setup>>()
    val done = MutableLiveData<Unit>()
    val shots = ArrayList<Shot>()

    fun activatePopup() {
        popupNeeded.value = Unit
    }

    fun shot() {
        shot.value = Unit
    }

    fun setNav(navController: NavController) {
        this.navController = navController
    }

    fun done() {
        done.value = Unit
    }

    fun addNewTraining(training: Training) {
        SpinnerRemote.startSpinner()
        val training = trainingInteractor.createTraining(
            TrainingModel(
                userId = ownUserId,
                isIndoor = training.isIndoor,
                setupId = training.setupId,
                arrowCnt = training.arrowCnt.toLong(),
                isPublic = training.isPublic,
                date = null
            )
        ).subscribe({ actualTrainingId ->
            shots.forEach { newShot ->
                val shot = trainingInteractor.createShot(
                    ShotModel(
                        trainingId = actualTrainingId,
                        target = newShot.target,
                        distance = newShot.distance,
                        placeX = newShot.placeX,
                        placeY = newShot.placeY,
                        score = newShot.score,
                    )
                ).subscribe({

                }, {
                    SpinnerRemote.stopSpinner()
                    it.printStackTrace()
                })
            }
        }, {
            SpinnerRemote.stopSpinner()
            it.printStackTrace()
        })
        SpinnerRemote.stopSpinner()
        navController.navigate(TrainingFragmentDirections.actionTrainingFragmentToLandingFragment())
    }

    fun getSetups() {
        viewModelScope.launch {
            val tempSetups = dao.getSetups()
            setups.value = tempSetups
        }
    }

    fun addNewShot(shot: Shot) {
        shots.add(shot)
    }

    fun setTrainingInteractor(trainingInteractor: TrainingInteractor) {
        this.trainingInteractor = trainingInteractor
    }

    fun setDao(dao: ArchDao) {
        this.dao = dao
    }

}
