package aut.arch.networking.models

import com.google.gson.annotations.SerializedName


data class TeamModel(
    @SerializedName("id")
    val teamId: String = "",
    @SerializedName("adminId")
    val adminId: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("users")
    val users: List<String>?
)
