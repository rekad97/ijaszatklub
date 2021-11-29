package aut.arch.ui.fragments.setup_details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import aut.arch.local_data.dao.ArchDao
import aut.arch.local_data.models.Setup
import aut.arch.local_data.models.enums.BowType
import aut.arch.networking.RetrofitFactory.ownUserId
import aut.arch.networking.interactors.TrainingInteractor
import aut.arch.networking.models.SetupModel
import aut.arch.ui.fragments.SpinnerRemote
import kotlinx.coroutines.launch

class SetupDetailsViewModel : ViewModel() {
    private lateinit var navController: NavController
    private lateinit var dao: ArchDao
    private lateinit var trainingInteractor: TrainingInteractor
    val activeBow = MutableLiveData<BowType?>()
    val activeTypeText = MutableLiveData<String>("Nincs választott típus")
    val save = MutableLiveData<Unit>()
    val updateSetup = MutableLiveData<Setup?>()
    var isNewSetup = false
    var setupId = "-1"

    fun activate(activatedBow: BowType) {
        activeBow.value = activatedBow
        when (activatedBow) {
            BowType.TRADITIONAL -> activeTypeText.value = "Tradícionális"
            BowType.RECURVE -> activeTypeText.value = "Feszített"
            BowType.COMPOUND -> activeTypeText.value = "Csigás"
        }
    }

    fun deactivate() {
        activeBow.value = null
        activeTypeText.value = "Nincs választott típus"
    }

    fun setIsNewSetup(isNew: Boolean) {
        isNewSetup = isNew
    }

    fun addNewSetup(setup: Setup) {
        SpinnerRemote.startSpinner()
        var id = setup.setupId
        if (isNewSetup) {
            val addSetup = trainingInteractor.createSetup(
                SetupModel(
                    setupId = setup.setupId,
                    userId = ownUserId,
                    setupName = setup.setupName,
                    middlePart = setup.middlePart,
                    arm = setup.arm,
                    retice = setup.retice,
                    stabilizator = setup.stabilizator,
                    bowType = setup.mapBowType(),
                    arrow = setup.arrow
                )
            ).subscribe({
                id = it
            }, {
                it.printStackTrace()
            })
        }
        viewModelScope.launch {
            dao.insertSetup(
                Setup(
                    setupId = id,
                    userId = ownUserId,
                    setupName = setup.setupName,
                    middlePart = setup.middlePart,
                    arm = setup.arm,
                    retice = setup.retice,
                    stabilizator = setup.stabilizator,
                    bowType = setup.bowType,
                    arrow = setup.arrow
                )
            )
            navController.navigate(SetupDetailsFragmentDirections.actionSetupDetailsFragmentToLandingFragment())
            SpinnerRemote.stopSpinner()
        }
    }

    fun save() {
        save.value = Unit
    }

    fun setDao(dao: ArchDao) {
        this.dao = dao
    }

    fun setNav(navController: NavController) {
        this.navController = navController
    }

    fun setTrainingInteractor(interactor: TrainingInteractor) {
        trainingInteractor = interactor
    }

    fun initSetup(setupId: String) {
        this.setupId = setupId

        SpinnerRemote.startSpinner()
        if (setupId == "-1") {
            isNewSetup = true
            updateSetup.value = null
            SpinnerRemote.stopSpinner()
        } else {
            isNewSetup = false
            val setup = trainingInteractor.getSetup(setupId).subscribe({
                updateSetup.value = it.map()
                SpinnerRemote.stopSpinner()
            }, {
                SpinnerRemote.stopSpinner()
                it.printStackTrace()
            })
        }
    }

    fun back() {
        navController.navigate(
            SetupDetailsFragmentDirections.actionSetupDetailsFragmentToLandingFragment()
        )
    }

}
