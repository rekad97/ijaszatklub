package aut.arch.ui.fragments.landing.statistics_preview_recyclerview.models

import com.github.mikephil.charting.charts.BarChart

data class BarChartPreview(
    override val title: String,
    val chart: BarChart
) : ChartPreview(TYPE_BAR)