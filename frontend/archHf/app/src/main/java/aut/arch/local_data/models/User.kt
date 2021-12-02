package aut.arch.local_data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = false)
    val userId: String = "-1",
    val isOwnUser: Boolean,
    val email: String,
    val password: String
)
