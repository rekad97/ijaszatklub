package aut.arch.local_data.models

import android.content.Context
import aut.arch.local_data.models.enums.ChartColor
import aut.arch.local_data.models.enums.ChartType

data class Chart(
    val type: ChartType,
    val xs: List<List<Long>>,
    val dataLabels: List<String>,
    val labels: List<String>,
    val context: Context,
    val colors: List<ChartColor>,
    val ys: List<List<Long>>?
)
