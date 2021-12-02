package aut.arch.networking.apis

import aut.arch.local_data.models.Shot
import aut.arch.networking.models.SetupModel
import aut.arch.networking.models.ShotModel
import aut.arch.networking.models.TeamModel
import aut.arch.networking.models.TrainingModel
import io.reactivex.Single
import retrofit2.http.*

interface TrainingApi {

    companion object {
        private const val BASE_PATH = ""
        const val TEAMS = BASE_PATH + "teams/"
        const val SHOTS = BASE_PATH + "shots/"
        const val SETUPS = BASE_PATH + "setups/"
        const val USERS = BASE_PATH + "users/"
        const val TRAININGS = BASE_PATH + "trainings/"
        const val TEAM = "$TEAMS{id}"
        const val SHOT = "$SHOTS{id}"
        const val SETUP = "$SETUPS{id}"
        const val USER = "$USERS{id}"
        const val ADD_USER_TO_TEAM = "$TEAM/{userid}/add"
        const val REMOVE_USER_FROM_TEAM = "$TEAM/delete/{userid}"
        const val TEAMS_OF_USER = "$USER/teams"
        const val TRAINING = "$TRAININGS{id}"
        const val CREATE_TEAM = TEAMS + "create"
        const val CREATE_SHOT = SHOTS + "create"
        const val CREATE_SETUP = SETUPS + "create"
        const val CREATE_TRAINING = TRAININGS + "create"
    }

    @POST(CREATE_TEAM)
    fun createTeam(
        @Body team: TeamModel
    ): Single<String>

    @POST(ADD_USER_TO_TEAM)
    fun addUserToTeam(
        @Path("id") id: String,
        @Path("userid") userId: String
    ): Single<Unit>

    @GET(TEAMS)
    fun getAllTeam(): Single<List<TeamModel>>

    @GET(TEAMS_OF_USER)
    fun getTeamsOfUser(
        @Path("id") id: String
    ): Single<List<String>>

    @GET(SHOT)
    fun getShot(
        @Path("id") id: String
    ): Single<ShotModel>

    @GET(TEAM)
    fun getTeam(
        @Path("id") id: String
    ): Single<TeamModel>

    @DELETE(TEAM)
    fun deleteTeam(
        @Path("id") id: String
    )

    @POST(CREATE_SHOT)
    fun createShot(
        @Body shot: ShotModel
    ): Single<String>

    @POST(CREATE_SETUP)
    fun createSetup(
        @Body setup: SetupModel
    ): Single<String>

    @GET(SETUPS)
    fun getAllSetup(): Single<List<SetupModel>>

    @GET(SETUP)
    fun getSetup(
        @Path("id") id: String
    ): Single<SetupModel>

    @DELETE(SETUP)
    fun deleteSetup(
        @Path("id") id: String
    )

    @POST(CREATE_TRAINING)
    fun createTraining(
        @Body training: TrainingModel
    ): Single<String>

    @GET(TRAINING)
    fun getTraining(
        @Path("id") id: String
    ): Single<TrainingModel>

    @DELETE(TRAINING)
    fun deleteTraining(
        @Path("id") id: String
    )

    @DELETE(REMOVE_USER_FROM_TEAM)
    fun removeUserFromTeam(
        @Path("id") teamId: String,
        @Path("userid") userId: String
    ): Single<Unit>

}