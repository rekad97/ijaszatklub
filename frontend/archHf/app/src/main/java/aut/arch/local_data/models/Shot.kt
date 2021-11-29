package aut.arch.local_data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import aut.arch.local_data.models.enums.TargetType

@Entity
data class Shot(
    @PrimaryKey(autoGenerate = false)
    val shotId: String = "-1",
    val trainingId: String,
    val target: TargetType,
    val distance: Long,
    val placeX: Int,
    val placeY: Int,
    val score: Int
)
