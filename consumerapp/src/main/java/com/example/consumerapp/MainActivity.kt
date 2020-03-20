package com.example.consumerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.consumerapp.Adapter.SectionsPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Glide.with(this)
            .load("https://image.winudf.com/v2/image/Y29tLlNwYWNlTGl2ZVdhbGxwYXBlcl9zY3JlZW5fNF8xNTMwMDkyMDg2XzA5NQ/screen-4.jpg?fakeurl=1&type=.jpg")
            .into(background)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        view_pager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(view_pager)
        supportActionBar?.elevation = 0f
    }


}
