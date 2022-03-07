package id.zeinsetyobudi.githubuserlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.zeinsetyobudi.githubuserlist.ui.setting.SettingPreferences
import id.zeinsetyobudi.githubuserlist.ui.setting.SettingsViewModel

class ViewModelFactory(private val pref: SettingPreferences) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            return SettingsViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}