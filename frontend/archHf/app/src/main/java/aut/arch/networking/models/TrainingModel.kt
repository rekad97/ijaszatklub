package aut.arch.networking.models

import com.google.gson.annotations.SerializedName
import java.util.*


data class TrainingModel(
    @SerializedName("id")
    val trainingId: String = "-1",
    @SerializedName("userId")
    val userId: String,
    @SerializedName("isIndoor")
    val isIndoor: Boolean,
    @SerializedName("setupId")
    val setupId: String,
    @SerializedName("arrowCnt")
    val arrowCnt: Long?,
    @SerializedName("isPublic")
    val isPublic: Boolean,
    @SerializedName("date")
    val date: Date?
)
