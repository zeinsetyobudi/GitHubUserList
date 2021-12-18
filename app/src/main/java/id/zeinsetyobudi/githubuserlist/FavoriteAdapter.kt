package id.zeinsetyobudi.githubuserlist

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.zeinsetyobudi.githubuserlist.database.Favorite
import id.zeinsetyobudi.githubuserlist.databinding.ItemFavoriteBinding
import id.zeinsetyobudi.githubuserlist.helper.FavoriteDiffCallback
import id.zeinsetyobudi.githubuserlist.ui.DetailActivity

class FavoriteAdapter: RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private val listFavorite = ArrayList<Favorite>()

    fun setListFavorite(listFavorite: List<Favorite>) {
        val diffCallback = FavoriteDiffCallback(this.listFavorite, listFavorite)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listFavorite.clear()
        this.listFavorite.addAll(listFavorite)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(listFavorite[position])
    }

    override fun getItemCount(): Int {
        return listFavorite.size
    }

    inner class FavoriteViewHolder(private val binding: ItemFavoriteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(favorite: Favorite) {
            with(binding) {
                Glide.with(cvItemFavorite.context).load(favorite.avatar).circleCrop().into(imgItemPhoto)

                tvItemName.text = favorite.username
                tvItemLocation.text = favorite.location
                tvItemRepository.text = favorite.repository.toString()
                cvItemFavorite.setOnClickListener {
                    val intent = Intent(it.context, DetailActivity::class.java)
                    val user = User(
                        0,
                        favorite.username,
                        favorite.surename,
                        favorite.avatar,
                        favorite.company,
                        favorite.location,
                        favorite.repository,
                        favorite.follower,
                        favorite.following
                    )
                    intent.putExtra(EXTRA_USER, user)
                    it.context.startActivity(intent)
                }
            }
        }
    }

    companion object {
        const val EXTRA_USER = "extra_user"
    }
}