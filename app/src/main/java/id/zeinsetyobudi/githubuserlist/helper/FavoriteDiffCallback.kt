package id.zeinsetyobudi.githubuserlist.helper

import androidx.recyclerview.widget.DiffUtil
import id.zeinsetyobudi.githubuserlist.database.Favorite

class FavoriteDiffCallback
    (private val mOldFavList: List<Favorite>, private val mNewFavList: List<Favorite>) :
    DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return mOldFavList.size
    }

    override fun getNewListSize(): Int {
        return mNewFavList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldFavList[oldItemPosition].id == mNewFavList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldFavList = mOldFavList[oldItemPosition]
        val newFavList = mNewFavList[newItemPosition]
        return oldFavList.username == newFavList.username &&
                oldFavList.avatar == newFavList.avatar
    }
}