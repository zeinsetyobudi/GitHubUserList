package id.zeinsetyobudi.githubuserlist.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.zeinsetyobudi.githubuserlist.Event
import id.zeinsetyobudi.githubuserlist.User
import id.zeinsetyobudi.githubuserlist.apicontroller.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {

    private val _userDetail = MutableLiveData<User>()
    val userDetail: LiveData<User> = _userDetail

    private val _listFollower = MutableLiveData<List<FollowResponseItem>>()
    val listFollower: LiveData<List<FollowResponseItem>> = _listFollower

    private val _listFollowing = MutableLiveData<List<FollowResponseItem>>()
    val listFollowing: LiveData<List<FollowResponseItem>> = _listFollowing

    private val _stateFollower = MutableLiveData<Event<String>>()
    val stateFollower: LiveData<Event<String>> = _stateFollower

    private val _stateFollowing = MutableLiveData<Event<String>>()
    var stateFollowing: LiveData<Event<String>> = _stateFollowing

    private val _isLoadingFollower = MutableLiveData<Boolean>()
    val isLoadingFollower: LiveData<Boolean> = _isLoadingFollower

    private val _isLoadingFollowing = MutableLiveData<Boolean>()
    val isLoadingFollowing: LiveData<Boolean> = _isLoadingFollowing

    init {
        _stateFollower.value = Event("")
        _stateFollowing.value = Event("")
    }

    fun detailUser(username: String) {
        val client = ApiConfig.getDetailApiService().getDetail(username)
        client.enqueue(object : Callback<DetailResponse> {
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        val user = User(
                            responseBody.id,
                            responseBody.login,
                            responseBody.name,
                            responseBody.avatarUrl,
                            responseBody.company,
                            responseBody.location,
                            responseBody.publicRepos,
                            responseBody.followers,
                            responseBody.following
                        )
                        _userDetail.value = user
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    fun getFollowerList(username: String) {
        _isLoadingFollower.value = true
        val client = ApiConfig.getFollowerApiService().getFollower(username)
        client.enqueue(object : Callback<List<FollowResponseItem>> {
            override fun onResponse(
                call: Call<List<FollowResponseItem>>,
                response: Response<List<FollowResponseItem>>
            ) {
                _isLoadingFollower.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listFollower.value = responseBody
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<FollowResponseItem>>, t: Throwable) {
                _isLoadingFollower.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    fun getFollowingList(username: String) {
        _isLoadingFollowing.value = true
        val client = ApiConfig.getFollowingApiService().getFollowing(username)
        client.enqueue(object : Callback<List<FollowResponseItem>> {
            override fun onResponse(
                call: Call<List<FollowResponseItem>>,
                response: Response<List<FollowResponseItem>>
            ) {
                _isLoadingFollowing.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listFollowing.value = responseBody
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<FollowResponseItem>>, t: Throwable) {
                _isLoadingFollowing.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    companion object {
        private const val TAG = "DetailViewModel"
    }
}