package aut.arch.local_data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import aut.arch.local_data.models.enums.BowType

@Entity
data class Setup(
    @PrimaryKey(autoGenerate = false)
    val setupId: String,
    val userId: String = "-1",
    val setupName: String,
    val middlePart: String,
    val arm: String,
    val retice: String,
    val stabilizator: String,
    val bowType: BowType,
    val arrow: String
){
    fun mapBowType(): String{
        return when(bowType){
            BowType.TRADITIONAL -> "traditional"
            BowType.RECURVE -> "recurve"
            BowType.COMPOUND -> "compound"
        }
    }
}
