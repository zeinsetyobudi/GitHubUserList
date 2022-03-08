package id.zeinsetyobudi.githubuserlist.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.zeinsetyobudi.githubuserlist.Event
import id.zeinsetyobudi.githubuserlist.model.User
import id.zeinsetyobudi.githubuserlist.apicontroller.DetailResponse
import id.zeinsetyobudi.githubuserlist.apicontroller.ApiConfig
import id.zeinsetyobudi.githubuserlist.apicontroller.SearchResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    var tmpListUser: ArrayList<User> = ArrayList()
    var isRun: Boolean = false

    private val _listUsers = MutableLiveData<ArrayList<User>>()
    val listUsers: LiveData<ArrayList<User>> = _listUsers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _notification = MutableLiveData<Event<String>>()
    val notification: LiveData<Event<String>> = _notification

    fun searchUser(username: String?) {
        _isLoading.value = true
        tmpListUser.clear()
        val user = username ?: ""
        val client = ApiConfig.getSearchApiService().getSearch(user)
        client.enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        for (users in responseBody.items) {
                            val result = User(
                                users.id,
                                users.login,
                                "",
                                users.avatarUrl,
                                "",
                                "",
                                0,
                                0,
                                0
                            )
                            detailUser(users.login, result)
                            tmpListUser.add(result)
                        }
                    }
                    isRun = false
                } else {
                    isRun = false
                    _notification.value = Event("Coba Lagi")
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                isRun = false
                _notification.value = Event("Kesalahan Koneksi")
                _isLoading.value = false
            }

        })
    }

    private fun detailUser(username: String, users: User) {
        val client = ApiConfig.getDetailApiService().getDetail(username)
        client.enqueue(object : Callback<DetailResponse> {
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>
            ) {
                (
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
                                tmpListUser[tmpListUser.indexOf(users)] = user
                            }
                            _listUsers.value = tmpListUser
                            _isLoading.value = false
                        } else {
                            _notification.value = Event("Gagal Memuat Profil, Coba Lagi")
                            Log.e(TAG, "onFailure: ${response.message()}")
                        }
                        )
            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                _isLoading.value = false
                _notification.value = Event("Kesalahan Koneksi")
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}