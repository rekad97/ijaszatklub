package aut.arch.networking.models

import aut.arch.local_data.models.Setup
import aut.arch.local_data.models.enums.BowType
import com.google.gson.annotations.SerializedName

data class SetupModel(
    @SerializedName("id")
    val setupId: String = "-1",
    @SerializedName("userId")
    val userId: String = "-1",
    @SerializedName("setupName")
    val setupName: String,
    @SerializedName("middlePart")
    val middlePart: String?,
    @SerializedName("arm")
    val arm: String?,
    @SerializedName("retice")
    val retice: String?,
    @SerializedName("stabilizator")
    val stabilizator: String?,
    @SerializedName("trainingType")
    val bowType: String?,
    @SerializedName("arrow")
    val arrow: String?
) {
    fun map(): Setup {
        return Setup(
            setupId = setupId,
            setupName = setupName,
            middlePart = middlePart?: "",
            arm = arm?: "",
            retice = retice?: "",
            stabilizator = stabilizator?: "",
            bowType = mapBowType(),
            arrow = arrow?: ""
        )
    }

    private fun mapBowType(): BowType {
        return when (bowType) {
            "traditional" -> BowType.TRADITIONAL
            "recurve" -> BowType.RECURVE
            "compound" -> BowType.COMPOUND
            else -> BowType.COMPOUND
        }
    }
}
