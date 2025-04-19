package com.example.pakwheels

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignupActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        firebaseAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        val nameInput: EditText = findViewById(R.id.nameinput)
        val emailInput: EditText = findViewById(R.id.emailinput)
        val passInput: EditText = findViewById(R.id.passinput)
        val signupButton: Button = findViewById(R.id.signupButton)

        signupButton.setOnClickListener {
            val name = nameInput.text.toString()
            val email = emailInput.text.toString()
            val pass = passInput.text.toString()

            if (name.isNotEmpty() && email.isNotEmpty() && pass.isNotEmpty()) {
                firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Get the signed-in user's Firebase User
                        val user = firebaseAuth.currentUser

                        // Create a HashMap of user data to store in Firestore
                        val userData = hashMapOf(
                            "name" to name,
                            "email" to email,
                            "uid" to user?.uid,
                            "pass" to pass,  // Timestamp when the user signed up
                        )

                        // Store the user data in Firestore under the "users" collection
                        firestore.collection("users")
                            .document(user?.uid ?: "")
                            .set(userData)
                            .addOnSuccessListener {
                                Toast.makeText(this, "Signup Successful", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this, LoginActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(this, "Error saving data: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                    } else {
                        Toast.makeText(this, "Signup Unsuccessful: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }

        val loginButton: TextView = findViewById(R.id.loginButton)
        loginButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}