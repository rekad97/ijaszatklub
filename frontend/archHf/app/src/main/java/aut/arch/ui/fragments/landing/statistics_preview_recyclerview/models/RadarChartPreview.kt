package aut.arch.ui.fragments.landing.statistics_preview_recyclerview.models

import com.github.mikephil.charting.charts.RadarChart

data class RadarChartPreview(
    override val title: String,
    val chart: RadarChart
) : ChartPreview(TYPE_RADAR)