// AllAdsFragment.kt
package com.example.pakwheels

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore


class AllAdsFragment : Fragment() {

    private lateinit var adsRecyclerView: RecyclerView
    private lateinit var adsAdapter: AdsAdapter
    private val adsList = mutableListOf<Ad>() // List to hold ads data

    private val firestore = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_all_ads, container, false)

        adsRecyclerView = rootView.findViewById(R.id.adsRecyclerView)
        adsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        adsAdapter = AdsAdapter(adsList) { ad -> onAdClicked(ad) }
        adsRecyclerView.adapter = adsAdapter

        fetchAds()  // Fetch the ads from Firestore

        return rootView
    }

    private fun fetchAds() {
        firestore.collection("ads")
            .get()
            .addOnSuccessListener { result ->
                if (result.isEmpty) {
                    Toast.makeText(requireContext(), "No ads found", Toast.LENGTH_SHORT).show()
                } else {
                    adsList.clear()
                    for (document in result) {
                        val ad = document.toObject(Ad::class.java)
                        adsList.add(ad)
                    }
                    adsAdapter.notifyDataSetChanged()
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(requireContext(), "Failed to fetch ads: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun onAdClicked(ad: Ad) {
        val bundle = Bundle().apply {
            putSerializable("ad", ad) // Pass the Ad object
        }

        // Create the AdDetailFragment instance and pass the bundle
        val adDetailFragment = AdDetailsFragment()
        adDetailFragment.arguments = bundle

        // Replace the current fragment with the AdDetailFragment
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.frame_container, adDetailFragment) // Use the container ID for your fragments
            .addToBackStack(null) // Optional: Adds this transaction to the back stack
            .commit()
    }
}