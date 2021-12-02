package aut.arch.networking.models

import com.google.gson.annotations.SerializedName


data class UserModel(
    @SerializedName("id")
    val userId: String = "-1",
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("token")
    val token: String? = null/*,
    @SerializedName("teams")
    val teams: List<TeamModel>? = null,
    @SerializedName("trainings")
    val trainings: List<TrainingModel>? = null*/
)
