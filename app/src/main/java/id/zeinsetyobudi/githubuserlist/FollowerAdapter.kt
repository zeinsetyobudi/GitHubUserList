package id.zeinsetyobudi.githubuserlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class FollowerAdapter(private val listFollower: ArrayList<Follower>) : RecyclerView.Adapter<FollowerAdapter.ListViewHolder>() {

    class ListViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var imgPhoto: ImageView = view.findViewById(R.id.img_item_photoFollow)
        var username: TextView = view.findViewById(R.id.tv_item_username)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_row_follow, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (username, avatar) = listFollower[position]
        val context = holder.itemView.context

        Glide.with(context).load(avatar).circleCrop().into(holder.imgPhoto)
        holder.username.text = username
    }

    override fun getItemCount(): Int {
        return listFollower.size
    }
}