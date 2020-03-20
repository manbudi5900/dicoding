package com.example.dicoding.notification.setting


import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.content.SharedPreferences
import android.util.Log

import androidx.fragment.app.FragmentActivity
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.example.dicoding.R


import com.example.dicoding.notification.NotificationReceiver



class NotificationSettingFragment :
    PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {
    private lateinit var notificationReceiver: NotificationReceiver

    private var applicationContext: Context? = null
    private lateinit var DAILY: String
    private lateinit var RELEASE: String
    private lateinit var dailySwitch: SwitchPreference
    private lateinit var releaseSwitch: SwitchPreference

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is Activity) {
            applicationContext = context as FragmentActivity
            notificationReceiver = NotificationReceiver(applicationContext)
        }
    }

    override fun onDetach() {
        super.onDetach()
        this.applicationContext = null
    }

    override fun onCreatePreferences(bundle: Bundle?, s: String?) {
        addPreferencesFromResource(R.xml.notification_setting)
        init()
        setSummaries()
    }

    private fun init() {
        DAILY = resources.getString(R.string.daily_switch)
        RELEASE = resources.getString(R.string.release_switch)

        dailySwitch = findPreference<SwitchPreference>(DAILY) as SwitchPreference
        Log.d("",dailySwitch.toString())
        releaseSwitch = findPreference<SwitchPreference>(RELEASE) as SwitchPreference
    }

    private fun setSummaries() {
        val sh = preferenceManager.sharedPreferences
        dailySwitch.isChecked = sh.getBoolean(DAILY, false)
        releaseSwitch.isChecked = sh.getBoolean(RELEASE, false)
        listenSwitchChanged()
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        if (key == DAILY) {
            dailySwitch.isChecked = sharedPreferences.getBoolean(DAILY, false)
        }
        if (key == RELEASE) {
            releaseSwitch.isChecked = sharedPreferences.getBoolean(RELEASE, false)
        }
        listenSwitchChanged()
    }

    private fun listenSwitchChanged() {
        if (dailySwitch.isChecked) {
            notificationReceiver.setDailyReminder()
        } else {
            notificationReceiver.cancelDailyReminder(applicationContext)
        }
        if (releaseSwitch.isChecked) {
            notificationReceiver.setReleaseTodayReminder()
        } else {
            notificationReceiver.cancelReleaseToday(applicationContext)
        }
    }
}
