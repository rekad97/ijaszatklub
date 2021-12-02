package aut.arch.local_data.models.relations

import androidx.room.Entity

@Entity(primaryKeys = ["userId", "teamId"])
data class UserTeamCrossRef(
    val userId: String,
    val teamId: String
)