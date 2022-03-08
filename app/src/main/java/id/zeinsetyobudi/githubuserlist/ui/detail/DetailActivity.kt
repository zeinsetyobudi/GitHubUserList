package id.zeinsetyobudi.githubuserlist.ui.detail

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import id.zeinsetyobudi.githubuserlist.*
import id.zeinsetyobudi.githubuserlist.database.Favorite
import id.zeinsetyobudi.githubuserlist.databinding.ActivityDetailBinding
import id.zeinsetyobudi.githubuserlist.helper.ViewModelFactory
import id.zeinsetyobudi.githubuserlist.model.User
import id.zeinsetyobudi.githubuserlist.ui.setting.SettingsActivity
import id.zeinsetyobudi.githubuserlist.ui.favorite.FavoriteViewModel

class DetailActivity : AppCompatActivity() {

    private lateinit var detailViewModel: DetailViewModel
    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var actionDetailBinding: ActivityDetailBinding
    private var flag: Boolean = false
    private var favorite = Favorite()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionDetailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(actionDetailBinding.root)

        val userIntent = intent.getParcelableExtra<User>(EXTRA_USER) as User
        var userDetail = userIntent
        setUserDetailText(userDetail)
        setUserDetailFavorite(userDetail)

        detailViewModel = ViewModelProvider(this)[DetailViewModel::class.java]
        detailViewModel.detailUser(userIntent.username.toString())
        detailViewModel.userDetail.observe(this) { user ->
            userDetail = user
            setUserDetailText(userDetail)
            setUserDetailFavorite(userDetail)
        }

        favoriteViewModel = obtainViewModel(this@DetailActivity)
        favoriteViewModel.select(userDetail.username.toString()).observe(this) { resultRow ->
            if (resultRow > 0) {
                actionDetailBinding.fabFavorite.backgroundTintList =
                    ColorStateList.valueOf(Color.parseColor("#BC1E1B"))
                flag = true
            }
        }

        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = SectionsPagerAdapter(this)
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        actionDetailBinding.fabFavorite.setOnClickListener {
            if (!flag) {
                //actionDetailBinding.fabFavorite.setBackgroundColor(Color.parseColor("#BC1E1B"))
                actionDetailBinding.fabFavorite.backgroundTintList =
                    ColorStateList.valueOf(Color.parseColor("#BC1E1B"))
                favoriteViewModel.insert(favorite)
                flag = true
                Log.i("Favorite", "Add")
            } else {
                //actionDetailBinding.fabFavorite.setBackgroundColor(Color.parseColor("#545d68"))
                actionDetailBinding.fabFavorite.backgroundTintList =
                    ColorStateList.valueOf(Color.parseColor("#545d68"))
                favoriteViewModel.delete(favorite.username.toString())
                flag = false
                Log.i("Favorite", "Delete")
            }
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.elevation = 0f
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu_setting, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_settings -> {
                val intentSettings = Intent(this, SettingsActivity::class.java)
                startActivity(intentSettings)
                true
            }
            else -> false
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavoriteViewModel::class.java]
    }

    private fun setUserDetailText(user: User){
        actionDetailBinding.apply {
            Glide.with(this@DetailActivity)
                .load(user.avatar)
                .placeholder(ColorDrawable(Color.GRAY))
                .error(R.drawable.nopic)
                .circleCrop()
                .into(actionDetailBinding.ivAvatar)

            tvName.text = user.surename
            tvUsername.text = user.username
            tvLocation.text = user.location
            tvCompany.text = user.company
            tvRepository.text = user.repository.toString()
            tvFollower.text = user.follower.toString()
            tvFollowing.text = user.following.toString()
        }
    }

    private fun setUserDetailFavorite(user : User){
        favorite.username = user.username
        favorite.surename = user.surename
        favorite.avatar = user.avatar
        favorite.company = user.company
        favorite.location = user.location
        favorite.repository = user.repository
        favorite.follower = user.follower
        favorite.following = user.following
    }

    companion object {
        const val EXTRA_USER = "extra_user"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.follower,
            R.string.following
        )
    }
}