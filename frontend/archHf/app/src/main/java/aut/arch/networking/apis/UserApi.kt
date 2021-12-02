package aut.arch.networking.apis

import aut.arch.local_data.models.User
import aut.arch.networking.models.UserModel
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import io.reactivex.Single
import retrofit2.http.Body

interface UserApi {

    companion object {
        private const val BASE_PATH = ""
        const val USERS = BASE_PATH + "users/"
        const val USER = "$USERS{id}"
        const val REGISTER = USERS + "register"
        const val AUTH = USERS + "auth"
    }

    @POST(REGISTER)
    fun registerUser(@Body user: UserModel): Single<String>

    @POST(AUTH)
    fun authenticateUser(@Body user: UserModel): Single<UserModel>

    @GET(USERS)
    fun getAllUser(): Single<List<UserModel>>

    @GET(USER)
    fun getUser(
        @Path("id") id: String
    ): Single<UserModel>

}
