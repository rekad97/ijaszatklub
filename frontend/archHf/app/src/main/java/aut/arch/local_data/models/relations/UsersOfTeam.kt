package aut.arch.local_data.models.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import aut.arch.local_data.models.Team
import aut.arch.local_data.models.User

data class UsersOfTeam(
    @Embedded val team: Team,
    @Relation(
        parentColumn = "teamId",
        entityColumn = "userId",
        associateBy = Junction(UserTeamCrossRef::class)
    )
    val users: List<User>
)
