package id.zeinsetyobudi.githubuserlist.repository

import android.app.Application
import androidx.lifecycle.LiveData
import id.zeinsetyobudi.githubuserlist.database.Favorite
import id.zeinsetyobudi.githubuserlist.database.FavoriteDao
import id.zeinsetyobudi.githubuserlist.database.FavoriteRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {
    private val mFavoriteDao: FavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteRoomDatabase.getDatabase(application)
        mFavoriteDao = db.favoriteDao()
    }

    fun getAllFavorite(): LiveData<List<Favorite>> = mFavoriteDao.getAllNotes()

    fun select(username: String): LiveData<Int> = mFavoriteDao.select(username)

    fun insert(favorite: Favorite) {
        executorService.execute { mFavoriteDao.insert(favorite) }
    }

    fun delete(username: String) {
        executorService.execute { mFavoriteDao.delete(username) }
    }
}