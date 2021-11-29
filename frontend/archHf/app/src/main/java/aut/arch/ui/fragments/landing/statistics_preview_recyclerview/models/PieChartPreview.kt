package aut.arch.ui.fragments.landing.statistics_preview_recyclerview.models

import com.github.mikephil.charting.charts.PieChart

data class PieChartPreview(
    override val title: String,
    val chart: PieChart
) : ChartPreview(TYPE_PIE)
