package aut.arch.ui.fragments.training.setup_picker_recyclerview

import aut.arch.local_data.models.enums.BowType

data class SetupPreview(
    val setupId: String,
    val name: String,
    val type: BowType
)
