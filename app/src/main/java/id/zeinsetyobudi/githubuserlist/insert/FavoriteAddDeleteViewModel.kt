package id.zeinsetyobudi.githubuserlist.insert

import android.app.Application
import androidx.lifecycle.ViewModel
import id.zeinsetyobudi.githubuserlist.database.Favorite
import id.zeinsetyobudi.githubuserlist.repository.FavoriteRepository

class FavoriteAddDeleteViewModel(application: Application): ViewModel() {
    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    fun insert(favorite: Favorite){
        mFavoriteRepository.insert(favorite)
    }

    fun delete(favorite: Favorite){
        mFavoriteRepository.delete(favorite)
    }
}