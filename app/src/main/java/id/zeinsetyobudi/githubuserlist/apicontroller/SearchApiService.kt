package id.zeinsetyobudi.githubuserlist.apicontroller

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface SearchApiService {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getSearch(
        @Query("q") username : String
    ): Call<SearchResponse>
}