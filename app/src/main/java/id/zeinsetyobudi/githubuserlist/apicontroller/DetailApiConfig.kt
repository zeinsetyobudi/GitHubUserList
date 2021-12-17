package id.zeinsetyobudi.githubuserlist.apicontroller

import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class DetailApiConfig {
    companion object{
        fun getDetailApiService(): DetailApiService {
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            val dispatcher = Dispatcher()
            dispatcher.maxRequests = 50
            dispatcher.maxRequestsPerHost = 50
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .dispatcher(dispatcher)
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(DetailApiService::class.java)
        }
    }
}