package com.example.pakwheels

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class ChatFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Find the button in the layout
        val startExploringButton: Button = view.findViewById(R.id.start_exploring_button)

        // Set click listener on the button
        startExploringButton.setOnClickListener {
            // Navigate to HomeFragment
            val homeFragment = HomeFragment() // Make sure HomeFragment exists in your project
            parentFragmentManager.beginTransaction()
                .replace(R.id.frame_container, homeFragment) // Replace 'fragment_container' with the ID of your container in the Activity layout
                .addToBackStack(null) // Optional: add this transaction to the back stack
                .commit()
        }
    }
}
