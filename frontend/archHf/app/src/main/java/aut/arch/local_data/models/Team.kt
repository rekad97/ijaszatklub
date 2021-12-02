package aut.arch.local_data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Team(
    @PrimaryKey(autoGenerate = false)
    val teamId: String = "-1",
    val adminId: Long
)
