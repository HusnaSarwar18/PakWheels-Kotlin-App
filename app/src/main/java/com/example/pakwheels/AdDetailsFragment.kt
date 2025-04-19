package com.example.pakwheels

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class AdDetailsFragment : Fragment() {

    private lateinit var adImageView: ImageView
    private lateinit var adNameTextView: TextView
    private lateinit var adModelTextView: TextView
    private lateinit var adPriceTextView: TextView
    private lateinit var adDescriptionTextView: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_ad_details, container, false)

        // Initialize the views
        adImageView = rootView.findViewById(R.id.adImageView)
        adNameTextView = rootView.findViewById(R.id.adNameTextView)
        adModelTextView = rootView.findViewById(R.id.adModelTextView)
        adPriceTextView = rootView.findViewById(R.id.adPriceTextView)
        adDescriptionTextView = rootView.findViewById(R.id.adDescriptionTextView)

        // Get the ad object from the arguments
        val ad = arguments?.getSerializable("ad") as Ad

        // Set the ad details
        adNameTextView.text = ad.name
        adModelTextView.text = ad.model
        adPriceTextView.text = "Price: ${ad.price}"
        adDescriptionTextView.text = ad.description

        // Load the image using Glide
        Glide.with(requireContext()).load(ad.imageUrl).into(adImageView)

        return rootView
    }
}