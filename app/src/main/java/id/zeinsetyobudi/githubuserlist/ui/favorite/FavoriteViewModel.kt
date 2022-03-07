package id.zeinsetyobudi.githubuserlist.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import id.zeinsetyobudi.githubuserlist.database.Favorite
import id.zeinsetyobudi.githubuserlist.repository.FavoriteRepository

class FavoriteViewModel(application: Application) : ViewModel() {
    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    fun getAllFavorite(): LiveData<List<Favorite>> = mFavoriteRepository.getAllFavorite()

    fun select(username: String): LiveData<Int> = mFavoriteRepository.select(username)

    fun insert(favorite: Favorite) {
        mFavoriteRepository.insert(favorite)
    }

    fun delete(username: String) {
        mFavoriteRepository.delete(username)
    }
}