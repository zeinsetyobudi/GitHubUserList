package id.zeinsetyobudi.githubuserlist.apicontroller

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface FollowingApiService {
    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(
        @Path("username") id: String
    ): Call<List<FollowResponseItem>>
}