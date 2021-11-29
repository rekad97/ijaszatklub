package aut.arch.ui.fragments.landing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import aut.arch.charts.ChartGenerator
import aut.arch.databinding.FragmentLandingBinding
import aut.arch.local_data.database.ArchDatabase
import aut.arch.local_data.models.Chart
import aut.arch.local_data.models.enums.ChartColor
import aut.arch.local_data.models.enums.ChartType
import aut.arch.ui.fragments.SpinnerRemote
import aut.arch.ui.fragments.landing.statistics_preview_recyclerview.StatisticsPreviewAdapter
import aut.arch.ui.fragments.landing.statistics_preview_recyclerview.models.BarChartPreview
import aut.arch.ui.fragments.landing.statistics_preview_recyclerview.models.PieChartPreview
import aut.arch.ui.fragments.landing.statistics_preview_recyclerview.models.RadarChartPreview

class LandingFragment : Fragment() {

    private lateinit var viewModel: LandingViewModel
    private lateinit var binding: FragmentLandingBinding
    private lateinit var navController: NavController
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: StatisticsPreviewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLandingBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onResume() {
        viewModel.deleteEmptyTrainings()
        super.onResume()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(LandingViewModel::class.java)
        binding.viewModel = viewModel
        recyclerView = binding.landingPreviewRecyclerview
        adapter = StatisticsPreviewAdapter()

        navController = findNavController()
        viewModel.setNav(navController)
        context?.let {
            viewModel.setDao(ArchDatabase.getInstance(it).archDao())
        }

        val layoutManager = LinearLayoutManager(
            view.context,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        val snapHelper: SnapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(recyclerView)
        recyclerView.layoutManager = layoutManager

        context?.let {

            adapter.addItem(
                BarChartPreview(
                    "Verseny eredmények", ChartGenerator.getBarChart(
                        Chart(
                            type = ChartType.BAR,
                            xs = arrayListOf(
                                arrayListOf(
                                    9L,
                                    2L,
                                    3L
                                ),
                                arrayListOf(
                                    2L,
                                    4L,
                                    8L
                                )
                            ),
                            dataLabels = arrayListOf("Peti eredményei", "Gyulus eredményei"),
                            labels = arrayListOf("Kültér", "Beltér", "Csigás"),
                            context = it,
                            colors = arrayListOf(ChartColor.ORANGE, ChartColor.PURPLE),
                            ys = null
                        )
                    )
                )
            )

            adapter.addItem(
                RadarChartPreview(
                    "Verseny eredmények", ChartGenerator.getRadarChart(
                        Chart(
                            type = ChartType.RADAR,
                            xs = arrayListOf(
                                arrayListOf(
                                    9L,
                                    2L,
                                    3L
                                ),
                                arrayListOf(
                                    2L,
                                    4L,
                                    8L
                                )
                            ),
                            dataLabels = arrayListOf("Peti eredményei", "Gyulus eredményei"),
                            labels = arrayListOf("Kültér", "Beltér", "Csigás"),
                            context = it,
                            colors = arrayListOf(ChartColor.ORANGE, ChartColor.PURPLE),
                            ys = null
                        )
                    )
                )
            )

            adapter.addItem(
                PieChartPreview(
                    "Verseny eredmények", ChartGenerator.getPieChart(
                        Chart(
                            type = ChartType.PIE,
                            xs = arrayListOf(
                                arrayListOf(
                                    9L,
                                    2L,
                                    3L
                                )
                            ),
                            dataLabels = arrayListOf("Saját eredmények"),
                            labels = arrayListOf("Kültér", "Beltér", "Csigás"),
                            context = it,
                            colors = arrayListOf(ChartColor.ORANGE),
                            ys = null
                        )
                    )
                )
            )

            binding.executePendingBindings()
            recyclerView.adapter = adapter
        }
    }
}
