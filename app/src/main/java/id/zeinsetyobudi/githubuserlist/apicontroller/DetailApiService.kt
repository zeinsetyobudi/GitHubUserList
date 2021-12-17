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