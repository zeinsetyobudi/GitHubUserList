package id.zeinsetyobudi.githubuserlist.ui.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import id.zeinsetyobudi.githubuserlist.*
import id.zeinsetyobudi.githubuserlist.databinding.ActivityMainBinding
import id.zeinsetyobudi.githubuserlist.ui.detail.DetailActivity
import id.zeinsetyobudi.githubuserlist.ui.detail.SearchAdapter
import id.zeinsetyobudi.githubuserlist.ui.favorite.FavoriteActivity
import id.zeinsetyobudi.githubuserlist.ui.setting.SettingPreferences
import id.zeinsetyobudi.githubuserlist.ui.setting.SettingsActivity
import id.zeinsetyobudi.githubuserlist.ui.setting.SettingsViewModel

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {

    private lateinit var actionMainBinding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var searchAdapter: SearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(actionMainBinding.root)

        val pref = SettingPreferences.getInstance(dataStore)
        val settingsViewModel = ViewModelProvider(this, ViewModelFactory(pref))[SettingsViewModel::class.java]

        settingsViewModel.getThemeSettings().observe(this
        ) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        searchAdapter = SearchAdapter()
        searchAdapter.setOnItemClickCallback(object : SearchAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                showSelectedUser(data)
            }
        })

        actionMainBinding.rvUsers.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = searchAdapter
        }

        mainViewModel.listUsers.observe(this) { listUsers ->
            setUserData(listUsers)
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        mainViewModel.notification.observe(this) {
            it.getContentIfNotHandled()?.let { notification ->
                Toast.makeText(this, notification, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu_main, menu)

        val searchManager = getSystemService(SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                mainViewModel.searchUser(p0)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_settings -> {
                val intentSettings = Intent(this, SettingsActivity::class.java)
                startActivity(intentSettings)
                true
            }
            R.id.menu_favorite -> {
                val intentFavorite = Intent(this, FavoriteActivity::class.java)
                startActivity(intentFavorite)
                true
            }
            else -> false
        }
    }

    private fun showLoading(isLoading: Boolean) {
        actionMainBinding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setUserData(listUsers: List<User>) {
        searchAdapter.notifyDataSetChanged()
        (actionMainBinding.rvUsers.adapter as SearchAdapter).submitList(listUsers)
    }

    private fun showSelectedUser(user: User) {
        val detailIntent = Intent(this@MainActivity, DetailActivity::class.java)
        detailIntent.putExtra(DetailActivity.EXTRA_USER, user)
        startActivity(detailIntent)
    }
}