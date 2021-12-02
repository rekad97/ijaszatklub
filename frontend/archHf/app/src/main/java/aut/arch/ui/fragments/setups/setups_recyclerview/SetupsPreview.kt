package aut.arch.ui.fragments.setups.setups_recyclerview

import aut.arch.local_data.models.enums.BowType

data class SetupsPreview(
    val setupId: String,
    val name: String,
    val type: BowType
)
