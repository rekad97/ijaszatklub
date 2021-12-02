package aut.arch.networking.models

import aut.arch.local_data.models.enums.TargetType
import com.google.gson.annotations.SerializedName


data class ShotModel(
    @SerializedName("shotId")
    val shotId: String = "-1",
    @SerializedName("trainingId")
    val trainingId: String,
    @SerializedName("target")
    val target: TargetType,
    @SerializedName("distance")
    val distance: Long,
    @SerializedName("placeX")
    val placeX: Int,
    @SerializedName("placeY")
    val placeY: Int,
    @SerializedName("score")
    val score: Int
)
