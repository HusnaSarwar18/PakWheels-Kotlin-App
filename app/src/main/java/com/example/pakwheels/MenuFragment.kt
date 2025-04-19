package com.example.pakwheels

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth

class MenuFragment : Fragment() {

    private lateinit var firebaseAuth: FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_menu, container, false)

        // Initialize Firebase Authentication
        firebaseAuth = FirebaseAuth.getInstance()

        // Find the buttons in the layout
        val loginButton: Button = view.findViewById(R.id.menuloginButton)
        val logoutButton: Button = view.findViewById(R.id.menulogoutButton)

        // Check user authentication status
        if (firebaseAuth.currentUser != null) {
            // User is logged in, show logout button and hide login/signup button
            loginButton.visibility = View.GONE
            logoutButton.visibility = View.VISIBLE

            // Set logout button click listener
            logoutButton.setOnClickListener {
                firebaseAuth.signOut()  // Sign out the user
                Toast.makeText(requireContext(), "You have logged out", Toast.LENGTH_SHORT).show()  // Show logout message
                updateUI() // Update UI after logout
            }
        } else {
            // User is not logged in, show login/signup button and hide logout button
            loginButton.visibility = View.VISIBLE
            logoutButton.visibility = View.GONE

            // Set login/signup button click listener
            loginButton.setOnClickListener {
                navigateToLoginActivity()
            }
        }

        // Set up spinners with options
        setupSpinners(view)

        return view
    }

    private fun navigateToLoginActivity() {
        // Log message to ensure the method is called
        Log.d("MenuFragment", "Navigating to SignupActivity")
        val intent = Intent(requireActivity(), SignupActivity::class.java)
        startActivity(intent)
    }

    private fun updateUI() {
        // Refresh the fragment UI by toggling button visibility directly
        val view = view ?: return // Ensure the view is not null

        val loginButton: Button = view.findViewById(R.id.menuloginButton)
        val logoutButton: Button = view.findViewById(R.id.menulogoutButton)

        if (firebaseAuth.currentUser != null) {
            // User is logged in, show logout button and hide login/signup button
            loginButton.visibility = View.GONE
            logoutButton.visibility = View.VISIBLE
        } else {
            // User is not logged in, show login/signup button and hide logout button
            loginButton.visibility = View.VISIBLE
            logoutButton.visibility = View.GONE
        }
    }

    private fun setupSpinners(view: View) {
        val personalSpinner: Spinner = view.findViewById(R.id.personal)
        val productSpinner: Spinner = view.findViewById(R.id.prodcut)
        val servicesSpinner: Spinner = view.findViewById(R.id.services)
        val exploreSpinner: Spinner = view.findViewById(R.id.explore)

        // Create adapters for each spinner
        val personalAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.personal_options,
            android.R.layout.simple_spinner_item
        )
        personalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        personalSpinner.adapter = personalAdapter

        val productAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.product_options,
            android.R.layout.simple_spinner_item
        )
        productAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        productSpinner.adapter = productAdapter

        val servicesAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.services_options,
            android.R.layout.simple_spinner_item
        )
        servicesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        servicesSpinner.adapter = servicesAdapter

        val exploreAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.explore_options,
            android.R.layout.simple_spinner_item
        )
        exploreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        exploreSpinner.adapter = exploreAdapter
    }
}
