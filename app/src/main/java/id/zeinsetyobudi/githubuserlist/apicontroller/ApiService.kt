package id.zeinsetyobudi.githubuserlist.apicontroller

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getSearch(
        @Query("q") username: String
    ): Call<SearchResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getDetail(
        @Path("username") id: String
    ): Call<DetailResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollower(
        @Path("username") id: String
    ): Call<List<FollowResponseItem>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(
        @Path("username") id: String
    ): Call<List<FollowResponseItem>>
}