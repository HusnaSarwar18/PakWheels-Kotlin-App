package com.example.pakwheels

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HomeFragment : Fragment() {

    private lateinit var firebaseauth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        firebaseauth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        val usernameTextView: TextView = view.findViewById(R.id.usernameTextView)
        val homeBikeButton: Button = view.findViewById(R.id.homeBike)
        val homeAllButton: Button = view.findViewById(R.id.homeAll)
        val homeCarButton: Button = view.findViewById(R.id.homeCars)
        val homeTyreButton: Button = view.findViewById(R.id.homeTyres)

        // Check if the user is logged in
        val user = firebaseauth.currentUser
        if (user != null) {
            // User is logged in, fetch their name from Firestore
            firestore.collection("users")
                .document(user.uid)
                .get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val userName = document.getString("name") ?: "No Name"
                        usernameTextView.text = "Hi, $userName"
                    } else {
                        usernameTextView.text = "User not found"
                    }
                }
                .addOnFailureListener { e ->
                    usernameTextView.text = "Error fetching user data: ${e.message}"
                }
        } else {
            // User is not logged in, show "Guest"
            usernameTextView.text = "Hi, Guest"
        }

        // Button click listeners (same as your existing code)
        homeBikeButton.setOnClickListener {
            Log.d("HomeFragment", "Bike Button Clicked")
            val bikeFragment = BikeFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_container, bikeFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        homeAllButton.setOnClickListener {
            Log.d("HomeFragment", "All Button Clicked")
            val allFragment = HomeFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_container, allFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        homeCarButton.setOnClickListener {
            Log.d("HomeFragment", "Car Button Clicked")
            val carFragment = CarFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_container, carFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        homeTyreButton.setOnClickListener {
            Log.d("HomeFragment", "Tyre Button Clicked")
            val tyreFragment = TyreFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_container, tyreFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        return view
    }
}