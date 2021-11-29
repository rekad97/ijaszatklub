package aut.arch.ui.fragments.teams

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import aut.arch.databinding.FragmentTeamsBinding
import aut.arch.local_data.dao.ArchDao
import aut.arch.networking.RetrofitFactory
import aut.arch.networking.interactors.TrainingInteractor
import aut.arch.networking.models.TeamModel
import aut.arch.ui.fragments.SpinnerRemote
import aut.arch.ui.fragments.teams.teams_recyclerview.TeamPreview
import kotlinx.coroutines.launch

class TeamsViewModel : ViewModel() {
    private lateinit var binding: FragmentTeamsBinding
    private lateinit var navController: NavController
    private lateinit var dao: ArchDao
    private lateinit var trainingInteractor: TrainingInteractor
    val ownTeams = MutableLiveData<List<TeamPreview>>()
    val otherTeams = MutableLiveData<List<TeamPreview>>()
    val newTeamCreated = MutableLiveData<Unit>()

    fun setNav(navController: NavController) {
        this.navController = navController
    }

    fun setDao(dao: ArchDao) {
        this.dao = dao
    }

    fun setTrainingInteractor(trainingInteractor: TrainingInteractor) {
        this.trainingInteractor = trainingInteractor
    }

    fun getTeams(isSpinnerNeeded: Boolean) {
        if (isSpinnerNeeded)
            SpinnerRemote.startSpinner()
        viewModelScope.launch {
            RetrofitFactory.ownUserId = dao.getOwnUser().first().userId
            val teams = trainingInteractor.getAllTeam().subscribe({ allTeam ->
                val teamsOfUser =
                    trainingInteractor.getTeamsOfUser(RetrofitFactory.ownUserId).subscribe({ teamsOfUser ->

                        val ownTeamsLocal = ArrayList<TeamPreview>()
                        val otherTeamsLocal = ArrayList<TeamPreview>()

                        allTeam.forEach {
                            if (teamsOfUser.contains(it.teamId)) {
                                ownTeamsLocal.add(
                                    TeamPreview(
                                        teamId = it.teamId,
                                        name = it.name,
                                        isUserMember = true
                                    )
                                )
                            } else {
                                otherTeamsLocal.add(
                                    TeamPreview(
                                        teamId = it.teamId,
                                        name = it.name,
                                        isUserMember = false
                                    )
                                )
                            }
                        }
                        ownTeams.value = ownTeamsLocal
                        otherTeams.value = otherTeamsLocal
                    }, {
                        SpinnerRemote.stopSpinner()
                        it.printStackTrace()
                    })
                SpinnerRemote.stopSpinner()
            }, {
                SpinnerRemote.stopSpinner()
                it.printStackTrace()
            })
        }
    }

    fun createNewTeam() {
        val newTeam = trainingInteractor.createTeam(
            TeamModel(
                adminId = RetrofitFactory.ownUserId,
                name = binding.teamsNewNameText.text.toString(),
                users = null
            )
        ).subscribe({
            binding.teamsNewNameText.text = null
            newTeamCreated.value = Unit
            getTeams(false)
        }, {
            it.printStackTrace()
            binding.teamsNewButtonButton.text = "Valamilyen hiba történt."
        })
    }

    fun setBinding(binding: FragmentTeamsBinding) {
        this.binding = binding
    }
}
