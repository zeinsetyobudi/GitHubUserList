package id.zeinsetyobudi.githubuserlist.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.zeinsetyobudi.githubuserlist.R
import id.zeinsetyobudi.githubuserlist.model.User
import id.zeinsetyobudi.githubuserlist.databinding.ItemRowUserBinding
import id.zeinsetyobudi.githubuserlist.helper.SearchDiffCallback
import java.util.Collections.emptyList

class SearchAdapter: RecyclerView.Adapter<SearchAdapter.ListViewHolder>() {

    private val listUsers = ArrayList<User>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setUserList(listUser: List<User>){
        val diffCallback = SearchDiffCallback(this.listUsers, listUser)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listUsers.clear()
        this.listUsers.addAll(listUser)
        diffResult.dispatchUpdatesTo(this)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        onBindViewHolder(holder, position, emptyList())
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int, payload: List<Any>) {
        holder.bind(listUsers[position])
    }

    override fun getItemCount(): Int {
        return listUsers.size
    }

    inner class ListViewHolder(private val userBinding: ItemRowUserBinding) :
        RecyclerView.ViewHolder(userBinding.root) {

        fun bind(user: User) {
            Glide.with(userBinding.cardView.context).load(user.avatar).circleCrop()
                .into(userBinding.imgItemPhoto)

            val nama = user.surename ?: ""
            val username = user.username
            userBinding.tvItemName.text =
                userBinding.cardView.context.getString(R.string.tx, nama, username)
            userBinding.tvItemLocation.text = user.location
            userBinding.tvItemRepository.text =
                userBinding.cardView.context.getString(R.string.txRepo, user.repository.toString())

            userBinding.cardView.setOnClickListener { onItemClickCallback.onItemClicked(user) }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }
}