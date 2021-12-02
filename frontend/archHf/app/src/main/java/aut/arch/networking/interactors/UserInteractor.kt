package aut.arch.networking.interactors

import aut.arch.local_data.models.User
import aut.arch.networking.apis.UserApi
import aut.arch.networking.models.UserModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UserInteractor(private val userApi: UserApi) {

    fun registerUser(user: UserModel): Single<String>{
        return userApi.registerUser(user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun authUser(user: UserModel): Single<UserModel>{
        return userApi.authenticateUser(user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getAllUser(): Single<List<UserModel>> {
        return userApi.getAllUser()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getUser(id: String): Single<UserModel>{
        return userApi.getUser(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}
