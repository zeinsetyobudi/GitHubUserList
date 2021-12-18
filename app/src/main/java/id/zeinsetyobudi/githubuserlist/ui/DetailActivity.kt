package id.zeinsetyobudi.githubuserlist.ui

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import id.zeinsetyobudi.githubuserlist.R
import id.zeinsetyobudi.githubuserlist.SectionsPagerAdapter
import id.zeinsetyobudi.githubuserlist.User
import id.zeinsetyobudi.githubuserlist.database.Favorite
import id.zeinsetyobudi.githubuserlist.helper.ViewModelFactory
import id.zeinsetyobudi.githubuserlist.databinding.ActivityDetailBinding
import id.zeinsetyobudi.githubuserlist.insert.FavoriteAddDeleteViewModel

class DetailActivity : AppCompatActivity() {

    private lateinit var favoriteAddDeleteViewModel: FavoriteAddDeleteViewModel
    private lateinit var actionDetailBinding: ActivityDetailBinding
    private var username: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionDetailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(actionDetailBinding.root)

        val user = intent.getParcelableExtra<User>(EXTRA_USER) as User
        username = user.username

        val favorite = Favorite()
        favorite.username = user.username
        favorite.surename = user.surename
        favorite.avatar = user.avatar
        favorite.company = user.company
        favorite.location = user.location
        favorite.repository = user.repository
        favorite.follower = user.follower
        favorite.following = user.following

        actionDetailBinding?.apply {
            Glide.with(this@DetailActivity)
                .load(user.avatar)
                .placeholder(ColorDrawable(Color.GRAY))
                .error(R.drawable.nopic)
                .circleCrop()
                .into(actionDetailBinding.ivAvatar)

            tvName.text = user.surename
            tvUsername.text = user.username
            tvLocation.text = user.location
            tvCompany.text      = user.company
            tvRepository.text   = user.repository.toString()
            tvFollower.text     = user.follower.toString()
            tvFollowing.text    = user.following.toString()
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        favoriteAddDeleteViewModel = obtainViewModel(this@DetailActivity)
        actionDetailBinding.fabFavorite.setOnClickListener {
            favoriteAddDeleteViewModel.insert(favorite)
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
        return when(item.itemId){
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

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteAddDeleteViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteAddDeleteViewModel::class.java)
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