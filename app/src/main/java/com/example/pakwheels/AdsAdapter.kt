package com.example.pakwheels

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class AdsAdapter(
    private val adsList: MutableList<Ad>,  // Use the shared Ad class
    private val onAdClick: (Ad) -> Unit // Use the shared Ad class here too
) : RecyclerView.Adapter<AdsAdapter.AdViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ad_tile, parent, false)
        return AdViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdViewHolder, position: Int) {
        val ad = adsList[position]
        holder.bind(ad)
    }

    override fun getItemCount(): Int {
        return adsList.size
    }

    inner class AdViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val adImage: ImageView = view.findViewById(R.id.adImage)
        private val adNameModel: TextView = view.findViewById(R.id.adNameModel)
        private val adPrice: TextView = view.findViewById(R.id.adPrice)

        fun bind(ad: Ad) {
            adNameModel.text = "${ad.name} ${ad.model}"
            adPrice.text = "Price: ${ad.price}"

            // Load image using Glide
            Glide.with(adImage.context).load(ad.imageUrl).into(adImage)

            // Set click listener to navigate to the details screen
            itemView.setOnClickListener {
                onAdClick(ad)
            }
        }
    }
}