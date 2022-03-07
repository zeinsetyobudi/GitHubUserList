package id.zeinsetyobudi.githubuserlist.helper

import androidx.recyclerview.widget.DiffUtil
import id.zeinsetyobudi.githubuserlist.database.Favorite

class FavoriteDiffCallback
    (private val mOldNoteList: List<Favorite>, private val mNewNoteList: List<Favorite>) :
    DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return mOldNoteList.size
    }

    override fun getNewListSize(): Int {
        return mNewNoteList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldNoteList[oldItemPosition].id == mNewNoteList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldEmployee = mOldNoteList[oldItemPosition]
        val newEmployee = mNewNoteList[newItemPosition]
        return oldEmployee.username == newEmployee.username &&
                oldEmployee.avatar == newEmployee.avatar
    }
}