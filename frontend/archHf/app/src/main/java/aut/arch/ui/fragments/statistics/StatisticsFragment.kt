package aut.arch.ui.fragments.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import aut.arch.charts.ChartGenerator
import aut.arch.databinding.FragmentStatisticsBinding
import aut.arch.local_data.database.ArchDatabase
import aut.arch.networking.RetrofitFactory
import aut.arch.ui.fragments.landing.statistics_preview_recyclerview.StatisticsPreviewAdapter
import aut.arch.ui.fragments.landing.statistics_preview_recyclerview.models.BarChartPreview

class StatisticsFragment : Fragment() {

    private lateinit var viewModel: StatisticsViewModel
    private lateinit var binding: FragmentStatisticsBinding
    private lateinit var teamsRecyclerView: RecyclerView
    private lateinit var teamsAdapter: StatisticsPreviewAdapter
    private lateinit var ownRecyclerView: RecyclerView
    private lateinit var ownAdapter: StatisticsPreviewAdapter
    private lateinit var teamMatesRecyclerView: RecyclerView
    private lateinit var teamMatesAdapter: StatisticsPreviewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStatisticsBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(StatisticsViewModel::class.java)

        teamsRecyclerView = binding.statisticsTeamsRecyclerview
        teamsAdapter = StatisticsPreviewAdapter()
        ownRecyclerView = binding.statisticsOwnRecyclerview
        ownAdapter = StatisticsPreviewAdapter()
        teamMatesRecyclerView = binding.statisticsTeamMatesRecyclerview
        teamMatesAdapter = StatisticsPreviewAdapter()

        context?.let { context ->
            viewModel.setDao(ArchDatabase.getInstance(context).archDao())
            viewModel.setChartsInteractor(RetrofitFactory.getChartsInteractor(context))
            viewModel.setTrainingInteractor(RetrofitFactory.getTrainingInteractor(context))
            viewModel.setUserInteractor(RetrofitFactory.getUserInteractor(context))
            viewModel.setContext(context)
        }

        val teamsLayoutManager = LinearLayoutManager(
            view.context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        val ownLayoutManager = LinearLayoutManager(
            view.context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        val teamMatesLayoutManager = LinearLayoutManager(
            view.context,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        val snapHelper: SnapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(teamsRecyclerView)
        teamsRecyclerView.layoutManager = teamsLayoutManager
        snapHelper.attachToRecyclerView(ownRecyclerView)
        ownRecyclerView.layoutManager = ownLayoutManager
        snapHelper.attachToRecyclerView(teamMatesRecyclerView)
        teamMatesRecyclerView.layoutManager = teamMatesLayoutManager

        binding.executePendingBindings()
        teamsRecyclerView.adapter = teamsAdapter
        ownRecyclerView.adapter = ownAdapter
        teamMatesRecyclerView.adapter = teamMatesAdapter

        viewModel.getCharts()

        initRecyclerViews()
    }

    private fun initRecyclerViews() {
        viewModel.teamMateCharts.observe(viewLifecycleOwner, { list ->
            list.forEach {
                teamMatesAdapter.addItem(
                    BarChartPreview(
                        "Friss edzés eredmények", ChartGenerator.getBarChart(it)
                    )
                )
            }

            teamMatesRecyclerView.adapter = teamMatesAdapter
            binding.executePendingBindings()
        })
    }

}