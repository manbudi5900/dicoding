package com.example.dicoding.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dicoding.DetailTV
import com.example.dicoding.Model.Tv
import com.example.dicoding.R
import kotlinx.android.synthetic.main.item.view.*

class CustomAdapterTv(): RecyclerView.Adapter<CustomAdapterTv.Holder>() {
    private val film = ArrayList<Tv>()
    var film1 : Tv?= null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.item,parent,false))
    }
    fun setData(items: ArrayList<Tv>){
        film.clear()
        film.addAll(items)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = film?.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.view.name.text = film?.get(position).original_title
        holder.view.desc.text = film?.get(position).overview
        Glide.with(holder.view.context)
            .load("https://image.tmdb.org/t/p/original"+film.get(position).poster_path)
            .placeholder(R.color.colorPrimary)
            .error(R.color.colorPrimary)
            .into(holder.view.imgView)


        film.get(position).vote_average?.toFloat()?.let { holder.view.rt.setRating(it) }
        holder.view.setOnClickListener{
            film1 = Tv(
                film.get(position).id,
                film.get(position).original_title,
                film.get(position).poster_path,
                film.get(position).overview,
                film.get(position).release_date,
                film.get(position).backdrop_path,
                film.get(position).vote_average
            )
            val moveWithObjectIntent = Intent(holder.view.context, DetailTV::class.java)
            moveWithObjectIntent.putExtra(DetailTV.OBJECT_TVSHOW, film1)
            holder.view.context.startActivity(moveWithObjectIntent)
        }

    }


    class Holder(val view: View) : RecyclerView.ViewHolder(view)


}