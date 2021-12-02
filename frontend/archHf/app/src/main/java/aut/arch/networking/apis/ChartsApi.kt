package aut.arch.networking.apis

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface ChartsApi {

    companion object {
        private const val BASE_PATH = ""
        private const val TEAM = BASE_PATH + "teams/{id}"
        private const val USER = BASE_PATH + "users/{id}"
        private const val TRAINING = BASE_PATH + "trainings/{id}"
        const val Users_Of_TEAM = "$TEAM/users"
        const val TRAININGS_OF_USER = "$USER/trainings"
        const val SHOTS_OF_TRAINING = "$TRAINING/shots"

    }

    @GET(Users_Of_TEAM)
    fun getUsersFromTeam(
        @Path("id") id: String
    ): Single<List<String>>

    @GET(TRAININGS_OF_USER)
    fun getTrainingsOfUser(
        @Path("id") id: String
    ): Single<List<String>>

    @GET(SHOTS_OF_TRAINING)
    fun getShotsOfTraining(
        @Path("id") id: String
    ): Single<List<String>>

}