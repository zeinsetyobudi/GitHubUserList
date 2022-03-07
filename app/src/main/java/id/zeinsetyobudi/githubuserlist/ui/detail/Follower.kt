package id.zeinsetyobudi.githubuserlist.ui.detail

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Follower(
    var username: String?,
    var avatar: String?
) : Parcelable