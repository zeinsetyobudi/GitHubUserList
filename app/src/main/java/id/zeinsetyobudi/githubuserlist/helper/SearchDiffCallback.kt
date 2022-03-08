package id.zeinsetyobudi.githubuserlist.helper

import androidx.recyclerview.widget.DiffUtil
import id.zeinsetyobudi.githubuserlist.model.User

class SearchDiffCallback(private val mOldSearchList: List<User>, private val mNewSearchList: List<User>): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldSearchList.size
    }

    override fun getNewListSize(): Int {
        return mNewSearchList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldSearchList[oldItemPosition].id == mNewSearchList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldUser = mOldSearchList[oldItemPosition]
        val newUser = mNewSearchList[newItemPosition]
        return oldUser.username == newUser.username &&
                oldUser.surename == newUser.surename &&
                oldUser.avatar == newUser.avatar &&
                oldUser.company == newUser.company &&
                oldUser.location == newUser.location &&
                oldUser.repository == newUser.repository &&
                oldUser.follower == newUser.follower &&
                oldUser.following == newUser.following
    }
}