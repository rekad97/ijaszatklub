package aut.arch.ui.fragments.statistics

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import aut.arch.local_data.dao.ArchDao
import aut.arch.local_data.models.Chart
import aut.arch.local_data.models.enums.ChartColor
import aut.arch.local_data.models.enums.ChartType
import aut.arch.networking.RetrofitFactory.ownUserId
import aut.arch.networking.interactors.ChartsInteractor
import aut.arch.networking.interactors.TrainingInteractor
import aut.arch.networking.interactors.UserInteractor
import aut.arch.ui.fragments.SpinnerRemote

class StatisticsViewModel : ViewModel() {
    private lateinit var context: Context
    private lateinit var userInteractor: UserInteractor
    private lateinit var trainingInteractor: TrainingInteractor
    private lateinit var chartsInteractor: ChartsInteractor
    private lateinit var dao: ArchDao
    val teamMateCharts = MutableLiveData<List<Chart>>()

    fun setDao(dao: ArchDao) {
        this.dao = dao
    }

    fun setChartsInteractor(chartsInteractor: ChartsInteractor) {
        this.chartsInteractor = chartsInteractor

    }

    fun setTrainingInteractor(trainingInteractor: TrainingInteractor) {
        this.trainingInteractor = trainingInteractor
    }

    fun setUserInteractor(userInteractor: UserInteractor) {
        this.userInteractor = userInteractor
    }

    fun setContext(context: Context) {
        this.context = context
    }

    // TODO there's a problem with the calls
    fun getCharts() {
        SpinnerRemote.startSpinner()
        /*val localTeamMateCharts = ArrayList<Chart>()
        val teams = trainingInteractor.getTeamsOfUser(ownUserId).subscribe({
            val dataLabels = ArrayList<String>()
            val xs = ArrayList<Long>()
            it.forEach {
                val members = chartsInteractor.getUsersFromTeam(it).subscribe({
                    it.forEach {
                        val labels = ArrayList<String>()
                        val ixs = ArrayList<Long>()
                        val member = userInteractor.getUser(it).subscribe({
                            dataLabels.add(it.email + "legutóbbi edzésének eredménye")
                        }, {
                            SpinnerRemote.stopSpinner()
                            it.printStackTrace()
                        })
                        val trainingsOfMember = chartsInteractor.getTrainingsOfUser(it).subscribe({
                            val shots = chartsInteractor.getShotsOfTraining(it.last()).subscribe({
                                var score = 0L
                                it.forEach {
                                    val point = trainingInteractor.getShot(it).subscribe({
                                        score += it.score.toLong()
                                    }, {
                                        SpinnerRemote.stopSpinner()
                                        it.printStackTrace()
                                    })
                                }
                                ixs.add(score)
                            }, {
                                SpinnerRemote.stopSpinner()
                                it.printStackTrace()
                            })
                        }, {
                            SpinnerRemote.stopSpinner()
                            it.printStackTrace()
                        })
                    xs.addAll(ixs)
                    }
                }, {
                    SpinnerRemote.stopSpinner()
                    it.printStackTrace()
                })
            }
            localTeamMateCharts.add(Chart(
                colors = arrayListOf(ChartColor.ORANGE,ChartColor.PURPLE,ChartColor.RED,ChartColor.YELLOW),
                context = context,
                xs = arrayListOf(xs),
                ys = null,
                dataLabels = dataLabels,
                type = ChartType.BAR,
                labels = arrayListOf("Összes pont")
            ))
        }, {
            SpinnerRemote.stopSpinner()
            it.printStackTrace()
        })
        SpinnerRemote.stopSpinner()*/
    }
}
