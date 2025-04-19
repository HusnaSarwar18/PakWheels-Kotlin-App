package com.example.pakwheels

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MyAdsFragment : Fragment() {

    private lateinit var adsRecyclerView: RecyclerView
    private lateinit var adsAdapter: AdsAdapter
    private val adsList = mutableListOf<Ad>() // Now using the shared Ad class

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_my_ads, container, false)

        adsRecyclerView = rootView.findViewById(R.id.myadsRecyclerView)
        adsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Use the common Ad class in the adapter
        adsAdapter = AdsAdapter(adsList) { ad -> onAdClicked(ad) }
        adsRecyclerView.adapter = adsAdapter

        fetchMyAds()  // Fetch the ads from Firestore

        return rootView
    }

    private fun fetchMyAds() {
        val user = firebaseAuth.currentUser
        if (user != null) {
            // Fetch ads specifically for the logged-in user
            firestore.collection("ads")
                .whereEqualTo("uid", user.uid)  // Filter by current user's UID
                .get()
                .addOnSuccessListener { result ->
                    if (result.isEmpty) {
                        Toast.makeText(requireContext(), "No ads found", Toast.LENGTH_SHORT).show()
                    } else {
                        adsList.clear()
                        for (document in result) {
                            val ad = document.toObject(Ad::class.java) // Use the shared Ad class
                            adsList.add(ad)
                        }
                        adsAdapter.notifyDataSetChanged()
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(requireContext(), "Failed to fetch ads: ${e.message}", Toast.LENGTH_SHORT).show()
                }
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