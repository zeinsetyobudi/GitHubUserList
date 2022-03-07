package id.zeinsetyobudi.githubuserlist.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.zeinsetyobudi.githubuserlist.R
import id.zeinsetyobudi.githubuserlist.User
import id.zeinsetyobudi.githubuserlist.databinding.ItemRowUserBinding
import java.util.Collections.emptyList

class SearchAdapter : ListAdapter<User, SearchAdapter.ListViewHolder>(DiffCallback()) {

    private lateinit var binding: ItemRowUserBinding
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        onBindViewHolder(holder, position, emptyList())
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int, payload: List<Any>) {
        val user = getItem(position)
        holder.bind(user)
    }

    override fun getItemCount(): Int {
        return currentList.size
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

    private class DiffCallback : DiffUtil.ItemCallback<User>() {

        override fun areItemsTheSame(oldItem: User, newItem: User) =
            oldItem.surename == newItem.surename

        override fun areContentsTheSame(oldItem: User, newItem: User) =
            oldItem == newItem

        override fun getChangePayload(oldItem: User, newItem: User): Any? {
            if (oldItem.surename == newItem.surename) {
                return if (oldItem.surename == newItem.surename) {
                    super.getChangePayload(oldItem, newItem)
                } else {
                    val diff = Bundle()
                    diff.putBoolean("arg.done", true)
                    diff
                }
            }

            return super.getChangePayload(oldItem, newItem)
        }
    }
}