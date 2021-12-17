package id.zeinsetyobudi.githubuserlist

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var id: Int?,
    var username: String?,
    var surename: String?,
    var avatar: String?,
    var company: String?,
    var location: String?,
    var repository: Int?,
    var follower: Int?,
    var following: Int?
) : Parcelable