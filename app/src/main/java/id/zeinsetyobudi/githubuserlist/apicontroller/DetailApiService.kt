package id.zeinsetyobudi.githubuserlist.apicontroller

import retrofit2.Call
import retrofit2.http.*

interface DetailApiService {
    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getDetail(
        @Path("username") id: String
    ): Call<DetailResponse>
}