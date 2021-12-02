package aut.arch.networking.models

import android.content.Context
import aut.arch.local_data.models.enums.ChartColor
import aut.arch.local_data.models.enums.ChartType
import com.google.gson.annotations.SerializedName

data class ChartModel(
    @SerializedName("type")
    val type: ChartType,
    @SerializedName("xs")
    val xs: List<List<Long>>,
    @SerializedName("dataLabels")
    val dataLabels: List<String>,
    @SerializedName("labels")
    val labels: List<String>,
    @SerializedName("context")
    val context: Context,
    @SerializedName("colors")
    val colors: List<ChartColor>,
    @SerializedName("ys")
    val ys: List<List<Long>>?
)
