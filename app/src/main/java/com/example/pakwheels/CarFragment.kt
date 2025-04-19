package com.example.pakwheels

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.FragmentTransaction
import com.example.pakwheels.BikeFragment

class CarFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_car, container, false)
        val homeBikeButton: Button = view.findViewById(R.id.homeBike)
        val homeAllButton: Button = view.findViewById(R.id.homeAll)
        val homeCarButton: Button = view.findViewById(R.id.homeCars)
        val homeTyreButton: Button = view.findViewById(R.id.homeTyres)

        homeBikeButton.setOnClickListener {
            Log.d("HomeFragment", "Bike Button Clicked")
            val bikeFragment = BikeFragment()
            // Use FragmentTransaction to navigate to the BikeFragment
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_container, bikeFragment) // Replace with container view ID
            transaction.addToBackStack(null)  // Allow backward navigation
            transaction.commit()
        }

        homeAllButton.setOnClickListener {
            Log.d("HomeFragment", "All Button Clicked")
            val allFragment = HomeFragment()  // Replace with the correct fragment
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_container, allFragment) // Replace with container view ID
            transaction.addToBackStack(null)
            transaction.commit()
        }

        homeCarButton.setOnClickListener {
            Log.d("HomeFragment", "Car Button Clicked")
            val carFragment = CarFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_container, carFragment) // Replace with container view ID
            transaction.addToBackStack(null)
            transaction.commit()
        }

        homeTyreButton.setOnClickListener {
            Log.d("HomeFragment", "Tyre Button Clicked")
            val tyreFragment = TyreFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_container, tyreFragment) // Replace with container view ID
            transaction.addToBackStack(null)
            transaction.commit()
        }

        return view
    }

}