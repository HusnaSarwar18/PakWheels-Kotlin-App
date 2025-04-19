package com.example.pakwheels

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity() {
    private lateinit var firebaseauth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        firebaseauth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        val emailInput: EditText = findViewById(R.id.Lemailinput)
        val passInput: EditText = findViewById(R.id.Lpassinput)
        val loginButton: Button = findViewById(R.id.LloginButton)
        val signupButton: TextView = findViewById(R.id.LsignupButton)

        // Login button functionality
        loginButton.setOnClickListener {
            val email = emailInput.text.toString()
            val pass = passInput.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty()) {
                firebaseauth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = firebaseauth.currentUser

                        // Fetch user data from Firestore
                        if (user != null) {
                            firestore.collection("users")
                                .document(user.uid)
                                .get()
                                .addOnSuccessListener { document ->
                                    if (document.exists()) {
                                        val userName = document.getString("name")
                                        Toast.makeText(this, "Welcome back, $userName!", Toast.LENGTH_SHORT).show()

                                        // Navigate to MainActivity after login
                                        val intent = Intent(this, MainActivity::class.java)
                                        startActivity(intent)
                                        finish()
                                    } else {
                                        Toast.makeText(this, "User data not found.", Toast.LENGTH_SHORT).show()
                                    }
                                }
                                .addOnFailureListener { e ->
                                    Toast.makeText(this, "Error fetching user data: ${e.message}", Toast.LENGTH_SHORT).show()
                                }
                        }
                    } else {
                        Toast.makeText(this, "Login Unsuccessful: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Please enter all fields.", Toast.LENGTH_SHORT).show()
            }
        }

        // Signup button functionality
        signupButton.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }
}