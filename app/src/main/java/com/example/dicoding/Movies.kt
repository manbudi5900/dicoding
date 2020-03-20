package com.example.dicoding


import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Movie
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.provider.UserDictionary.Words.APP_ID
import android.util.Log
import android.view.*
import android.view.textclassifier.TextLanguage
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicoding.Adapter.CustomAdapter
import com.example.dicoding.Model.Film
import kotlinx.android.synthetic.main.coba.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.dicoding.Model.MainViewModel


class Movies : Fragment() {
    private val handler = Handler()
    private lateinit var mainViewModel: MainViewModel
    private lateinit var movieAdapter: CustomAdapter




    val list1 = ArrayList<Film>()
    private val listMovie =  MutableLiveData<ArrayList<Film>>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.coba, container, false)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ShowProgressDialog(true)
        setHasOptionsMenu(true)
        movieAdapter = CustomAdapter()
        movieAdapter.notifyDataSetChanged()
        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)
        mainViewModel.setMovies(getString(R.string.language))
        list.setHasFixedSize(true)
        list.layoutManager = LinearLayoutManager(this.context)
        list.adapter = movieAdapter
        mainViewModel.getMovies().observe(this, Observer { movieItems ->
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


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search, menu)
        val searchItem : MenuItem = menu.findItem(R.id.searchMenu)
        val searchView : SearchView= searchItem.actionView as SearchView

        searchQuery(searchView)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun searchQuery(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query.isNullOrEmpty()) {
                    return false
                }else{
                    movieAdapter = CustomAdapter()
                    movieAdapter.notifyDataSetChanged()
                    mainViewModel = ViewModelProvider(this@Movies, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)
                    mainViewModel.setMovies(getString(R.string.language),query.toString())
                    list.setHasFixedSize(true)
                    list.layoutManager = LinearLayoutManager(context)
                    list.adapter = movieAdapter
                    mainViewModel.getMovies().observe(this@Movies, Observer { movieItems ->
                        if(movieItems!=null){
                            Log.d("kakakakaakkak","he")
                            movieAdapter.setData(movieItems)
                            movieAdapter.notifyDataSetChanged()
                        }
                    })
                }


                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                movieAdapter = CustomAdapter()
                movieAdapter.notifyDataSetChanged()
                mainViewModel = ViewModelProvider(this@Movies, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)
                if (newText != null) {
                    mainViewModel.setMovies(getString(R.string.language),newText.toString())
                    list.setHasFixedSize(true)
                    list.layoutManager = LinearLayoutManager(context)
                    list.adapter = movieAdapter
                    mainViewModel.getMovies().observe( this@Movies ,Observer { movieItems ->
                        if(movieItems!=null){
                            Log.d("kakakakaakkak","he")
                            movieAdapter.setData(movieItems)
                            movieAdapter.notifyDataSetChanged()
                            ShowProgressDialog(false)
                        }
                    })
                }else{
                    mainViewModel.setMovies(getString(R.string.language),"")
                    list.setHasFixedSize(true)
                    list.layoutManager = LinearLayoutManager(context)
                    list.adapter = movieAdapter
                    mainViewModel.getMovies().observe( this@Movies ,Observer { movieItems ->
                        if(movieItems!=null){
                            Log.d("kakakakaakkak","he")
                            movieAdapter.setData(movieItems)
                            movieAdapter.notifyDataSetChanged()
                            ShowProgressDialog(false)
                        }
                    })
                }
                return false
            }
        })
    }

}



