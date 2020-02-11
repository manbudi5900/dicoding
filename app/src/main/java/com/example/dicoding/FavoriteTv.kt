package com.example.dicoding

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicoding.Adapter.CustomAdapter
import com.example.dicoding.Adapter.CustomAdapterTv
import com.example.dicoding.Model.MainViewModel
import com.example.dicoding.Model.Tv
import com.example.dicoding.db.DatabaseTV
import kotlinx.android.synthetic.main.coba.*

class FavoriteTv : Fragment() {
    private lateinit var movieAdapter: CustomAdapterTv
    private lateinit var mainViewModel: MainViewModel
    private lateinit var  TvArrayList: ArrayList<Tv>
    private lateinit var databaseHelper: DatabaseTV

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.coba, container, false)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        movieAdapter = CustomAdapterTv()
        movieAdapter.notifyDataSetChanged()
        databaseHelper = this.context?.let { DatabaseTV(it) }!!
        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)
        TvArrayList = databaseHelper!!.getAllTv
        mainViewModel.setTv(TvArrayList)
        list.setHasFixedSize(true)
        list.layoutManager = LinearLayoutManager(this.context)
        list.adapter = movieAdapter
        mainViewModel.getTVShows().observe(this, Observer { movieItems ->
            if(movieItems!=null){
                Log.d("kakakakaakkak","he")
                movieAdapter.setData(movieItems)
                movieAdapter.notifyDataSetChanged()
                ShowProgressDialog(false)
            }
        })
    }
    fun ShowProgressDialog(state: Boolean){
        if(state){
            progress_bar.visibility= View.VISIBLE
        }
        else{
            progress_bar.visibility= View.GONE
        }
    }
}
