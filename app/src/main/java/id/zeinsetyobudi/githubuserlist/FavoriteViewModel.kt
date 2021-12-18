package id.zeinsetyobudi.githubuserlist

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import id.zeinsetyobudi.githubuserlist.database.Favorite
import id.zeinsetyobudi.githubuserlist.repository.FavoriteRepository

class FavoriteViewModel(application: Application): ViewModel() {
    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    fun getAllFavorite(): LiveData<List<Favorite>> = mFavoriteRepository.getAllFavorite()
}