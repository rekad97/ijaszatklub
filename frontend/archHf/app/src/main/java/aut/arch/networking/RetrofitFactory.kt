package aut.arch.networking

import android.content.Context
import aut.arch.networking.apis.ChartsApi
import aut.arch.networking.apis.TrainingApi
import aut.arch.networking.apis.UserApi
import aut.arch.networking.interactors.ChartsInteractor
import aut.arch.networking.interactors.TrainingInteractor
import aut.arch.networking.interactors.UserInteractor
import aut.arch.networking.interceptors.AuthInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

object RetrofitFactory {
    private var retrofit: Retrofit? = null
    private var trainingInteractor: TrainingInteractor? = null
    private var userInteractor: UserInteractor? = null
    private var chartsInteractor: ChartsInteractor? = null
    var ownUserId = "-1"

    fun getTrainingInteractor(context: Context): TrainingInteractor{
         if(trainingInteractor == null){
              (return if(retrofit == null){
                 val api = createRetrofit(context).create(TrainingApi::class.java)
                  val trainingInteractor = TrainingInteractor(api)
                  this.trainingInteractor = trainingInteractor
                  trainingInteractor
             }else{
                 val api = retrofit?.create(TrainingApi::class.java)?:
                 createRetrofit(context).create(TrainingApi::class.java)

                  val trainingInteractor = TrainingInteractor(api)
                  this.trainingInteractor = trainingInteractor
                  trainingInteractor
             })
        }else{
            return trainingInteractor as TrainingInteractor
         }
    }

    fun getUserInteractor(context: Context): UserInteractor{
        if(userInteractor == null){
            (return if(retrofit == null){
                val api = createRetrofit(context).create(UserApi::class.java)
                val userInteractor = UserInteractor(api)
                this.userInteractor = userInteractor
                userInteractor
            }else{
                val api = retrofit?.create(UserApi::class.java)?:
                createRetrofit(context).create(UserApi::class.java)

                val userInteractor = UserInteractor(api)
                this.userInteractor = userInteractor
                userInteractor
            })
        }else{
            return userInteractor as UserInteractor
        }
    }

    fun getChartsInteractor(context: Context): ChartsInteractor{
        if(chartsInteractor == null){
            (return if(retrofit == null){
                val api = createRetrofit(context).create(ChartsApi::class.java)
                val chartsInteractor = ChartsInteractor(api)
                this.chartsInteractor = chartsInteractor
                chartsInteractor
            }else{
                val api = retrofit?.create(ChartsApi::class.java)?:
                createRetrofit(context).create(ChartsApi::class.java)

                val chartsInteractor = ChartsInteractor(api)
                this.chartsInteractor = chartsInteractor
                chartsInteractor
            })
        }else{
            return chartsInteractor as ChartsInteractor
        }
    }

    fun createRetrofit(context: Context): Retrofit{
        val converter = GsonConverterFactory.create()
        val callAdapterFactory = RxJava2CallAdapterFactory.create()
        val clientBuilder = OkHttpClient()
            .newBuilder()
            .addInterceptor(AuthInterceptor(context))
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://salty-tundra-60897.herokuapp.com/")
            .addConverterFactory(converter)
            .addCallAdapterFactory(callAdapterFactory)
            .client(clientBuilder)
            .build()

        this.retrofit = retrofit

        return retrofit
    }

}