package aut.arch.local_data.dao

import androidx.room.*
import aut.arch.local_data.models.*
import aut.arch.local_data.models.relations.UserTeamCrossRef
import aut.arch.local_data.models.relations.UsersOfTeam

@Dao
interface ArchDao {

    @Update
    suspend fun updateUser(user: User)

    @Update
    suspend fun updateTraining(training: Training)

    @Update
    suspend fun updateSetup(setup: Setup)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSetup(setup: Setup): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShot(shot: Shot): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTeam(team: Team): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTraining(training: Training): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun connectUserWithTeam(userTeamCrossRef: UserTeamCrossRef): Long

    @Transaction
    @Query("SELECT * FROM Team WHERE teamId = :teamId")
    suspend fun getUsersOfTeam(teamId: String): List<UsersOfTeam>

    @Query("SELECT * FROM User WHERE isOwnUser")
    suspend fun getOwnUser(): List<User>

    @Query("DELETE FROM User WHERE isOwnUser")
    suspend fun logout()

    @Query("SELECT * FROM Setup")
    suspend fun getSetups(): List<Setup>

    @Query("DELETE FROM Training WHERE arrowCnt = 0")
    suspend fun deleteEmptyTrainings()

    @Query("SELECT * FROM Setup WHERE setupId = :setupId")
    suspend fun getSetup(setupId: String): Setup

}
