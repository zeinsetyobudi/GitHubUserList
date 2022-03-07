package id.zeinsetyobudi.githubuserlist.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favorite: Favorite)

    @Query("DELETE from favorite WHERE username = :username")
    fun delete(username: String)

    @Query("SELECT * from favorite ORDER BY id ASC")
    fun getAllNotes(): LiveData<List<Favorite>>

    @Query("SELECT COUNT(username) from favorite WHERE username = :username")
    fun select(username: String): LiveData<Int>
}