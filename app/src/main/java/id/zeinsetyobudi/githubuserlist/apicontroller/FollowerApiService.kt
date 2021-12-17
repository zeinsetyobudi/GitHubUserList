package id.zeinsetyobudi.githubuserlist.apicontroller

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface FollowerApiService {
    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollower(
        @Path("username") id: String
    ): Call<List<FollowResponseItem>>
}