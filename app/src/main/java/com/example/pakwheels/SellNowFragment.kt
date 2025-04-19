package com.example.pakwheels

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth

class SellNowFragment : Fragment() {

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sell_now, container, false)

        firebaseAuth = FirebaseAuth.getInstance()

        // Set up click listener for "Sell it Myself!" layout
        val sellMyselfLayout = view.findViewById<LinearLayout>(R.id.sellmyself)
        sellMyselfLayout.setOnClickListener {
            // Check if user is logged in
            if (firebaseAuth.currentUser == null) {
                showLoginDialog()
            } else {
                // Start AdForm activity when user is logged in
                val intent = Intent(requireContext(), AdForm::class.java)
                startActivity(intent)
            }
        }

        // Set up click listener for "Sell it for me!" layout
        val sellItForMeLayout = view.findViewById<LinearLayout>(R.id.sellitforme)
        sellItForMeLayout.setOnClickListener {
            // Check if user is logged in
            if (firebaseAuth.currentUser == null) {
                showLoginDialog()
            } else {
                // Start AdForm activity when user is logged in
                val intent = Intent(requireContext(), AdForm::class.java)
                startActivity(intent)
            }
        }

        return view
    }

    private fun showLoginDialog() {
        // Create an AlertDialog to prompt the user to log in or sign up
        val dialogBuilder = AlertDialog.Builder(requireContext())
        dialogBuilder.setTitle("Log in Required")
        dialogBuilder.setMessage("Please log in or sign up to sell items.")

        dialogBuilder.setPositiveButton("Log In") { dialog, _ ->
            // Redirect to login screen
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            dialog.dismiss()
        }

        dialogBuilder.setNegativeButton("Sign Up") { dialog, _ ->
            // Redirect to sign up screen
            val intent = Intent(requireContext(), SignupActivity::class.java)
            startActivity(intent)
            dialog.dismiss()
        }

        // Show the dialog
        dialogBuilder.create().show()
    }
}
