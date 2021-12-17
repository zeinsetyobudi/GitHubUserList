package id.zeinsetyobudi.githubuserlist

import android.os.Bundle
import android.view.WindowManager
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import id.zeinsetyobudi.githubuserlist.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var actionDetailBinding: ActivityDetailBinding
    private var username: String? = ""

    companion object {
        const val EXTRA_USER = "extra_user"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.follower,
            R.string.following
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionDetailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(actionDetailBinding.root)

        val user = intent.getParcelableExtra<User>(EXTRA_USER) as User
        username = user.username

        Glide.with(this).load(user.avatar).circleCrop().into(actionDetailBinding.ivAvatar)
        actionDetailBinding.tvName.text         = user.surename
        actionDetailBinding.tvUsername.text     = user.username
        actionDetailBinding.tvLocation.text     = user.location
        actionDetailBinding.tvCompany.text      = user.company
        actionDetailBinding.tvRepository.text   = user.repository.toString()
        actionDetailBinding.tvFollower.text     = user.follower.toString()
        actionDetailBinding.tvFollowing.text    = user.following.toString()

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
    }
}