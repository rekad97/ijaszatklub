package aut.arch.ui.fragments.setups

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import aut.arch.local_data.dao.ArchDao
import aut.arch.local_data.models.Setup
import aut.arch.networking.RetrofitFactory
import aut.arch.networking.RetrofitFactory.ownUserId
import aut.arch.networking.interactors.TrainingInteractor
import aut.arch.ui.fragments.SpinnerRemote
import kotlinx.coroutines.launch

class SetupsViewModel : ViewModel() {
    private lateinit var dao: ArchDao
    private lateinit var navController: NavController
    private lateinit var trainingInteractor: TrainingInteractor
    val setups = MutableLiveData<List<Setup>>()

    fun setNav(navController: NavController) {
        this.navController = navController
    }

    fun setDao(dao: ArchDao) {
        this.dao = dao
    }

    fun createSetup() {
        navController.navigate(
            SetupsFragmentDirections.actionSetupsFragmentToSetupDetailsFragment("-1")
        )
    }

    fun setTrainingInteractor(trainingInteractor: TrainingInteractor) {
        this.trainingInteractor = trainingInteractor
    }

    fun getSetups() {
            SpinnerRemote.startSpinner()
        viewModelScope.launch {
            val setups = trainingInteractor.getAllSetup().subscribe({ allSetup ->
                val localSetups = ArrayList<Setup>()
                allSetup.forEach {
                   localSetups.add(it.map())
                }
                setups.value = localSetups
            }, {
                SpinnerRemote.stopSpinner()
                it.printStackTrace()
            })
            SpinnerRemote.stopSpinner()
    }
}
}
