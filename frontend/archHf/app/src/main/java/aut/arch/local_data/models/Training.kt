package aut.arch.local_data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Training(
    @PrimaryKey(autoGenerate = false)
    val trainingId: String = "-1",
    val userId: String,
    val isIndoor: Boolean,
    val setupId: String,
    val arrowCnt: Int,
    val isPublic: Boolean
)
