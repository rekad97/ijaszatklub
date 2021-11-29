package aut.arch.ui.fragments.teams.teams_recyclerview

import aut.arch.local_data.models.enums.BowType

data class TeamPreview(
    val teamId: String,
    val name: String,
    val isUserMember: Boolean
)
