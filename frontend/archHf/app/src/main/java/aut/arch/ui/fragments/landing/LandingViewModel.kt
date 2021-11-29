package aut.arch.ui.fragments.landing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import aut.arch.local_data.dao.ArchDao
import kotlinx.coroutines.launch

class LandingViewModel : ViewModel() {
    private lateinit var navController: NavController
    private lateinit var dao: ArchDao

    fun navigateToStatistics() {
        navController.navigate(
            LandingFragmentDirections.actionLandingFragmentToStatisticsFragment()
        )
    }

    fun navigateToTraining() {
        navController.navigate(LandingFragmentDirections.actionLandingFragmentToTrainingFragment())
    }

    fun navigateToSetups() {
        navController.navigate(LandingFragmentDirections.actionLandingFragmentToSetupsFragment())
    }

    fun navigateToTeams() {
        navController.navigate(LandingFragmentDirections.actionLandingFragmentToTeamsFragment())
    }

    fun navigateToSettings() {
    }

    fun logout() {
        viewModelScope.launch {
            dao.logout()
        }
        navController.navigate(LandingFragmentDirections.actionLandingFragmentToLoginFragment())
    }

    fun setNav(navController: NavController) {
        this.navController = navController
    }

    fun setDao(archDao: ArchDao) {
        dao = archDao
    }

    fun deleteEmptyTrainings() {
        viewModelScope.launch {
            dao.deleteEmptyTrainings()
        }
    }

}