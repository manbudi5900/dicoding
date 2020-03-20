package com.example.dicoding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.example.dicoding.Adapter.SectionsPagerAdapter
import com.example.dicoding.notification.setting.NotificationSettingActivity
import kotlinx.android.synthetic.main.activity_utama__nav.*

class Utama_Nav : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_utama__nav)
        Glide.with(this)
            .load("https://image.winudf.com/v2/image/Y29tLlNwYWNlTGl2ZVdhbGxwYXBlcl9zY3JlZW5fNF8xNTMwMDkyMDg2XzA5NQ/screen-4.jpg?fakeurl=1&type=.jpg")
            .into(background)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        view_pager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(view_pager)
        supportActionBar?.elevation = 0f
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_change_settings) {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }else{
            val mIntent = Intent(this.applicationContext,NotificationSettingActivity::class.java)
            startActivity(mIntent)
        }
        return super.onOptionsItemSelected(item)
    }
}
