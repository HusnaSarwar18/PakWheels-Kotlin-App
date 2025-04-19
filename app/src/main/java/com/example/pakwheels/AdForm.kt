package com.example.pakwheels

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AdForm : AppCompatActivity() {

    private lateinit var inputName: EditText
    private lateinit var inputModel: EditText
    private lateinit var rgVehicleType: RadioGroup
    private lateinit var inputColor: EditText
    private lateinit var spinnerCondition: Spinner
    private lateinit var inputDescription: EditText
    private lateinit var inputPrice: EditText
    private lateinit var inputImageUrl: EditText
    private lateinit var btnPostNow: Button

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ad_form)

        firebaseAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        // Initialize all the views
        inputName = findViewById(R.id.input_name)
        inputModel = findViewById(R.id.input_model)
        rgVehicleType = findViewById(R.id.rg_vehicle_type) // RadioGroup for selecting vehicle type
        inputColor = findViewById(R.id.input_color)
        spinnerCondition = findViewById(R.id.spinner_condition)
        inputDescription = findViewById(R.id.input_description)
        inputPrice = findViewById(R.id.input_price)
        inputImageUrl = findViewById(R.id.input_image_url)
        btnPostNow = findViewById(R.id.btn_post_now)

        // Initialize spinner with condition options
        val conditionAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            listOf("Select Condition", "New", "Used")
        )
        conditionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCondition.adapter = conditionAdapter

        // Set click listener for the "Post Now" button
        btnPostNow.setOnClickListener { postAd() }
    }

    private fun postAd() {
        val name = inputName.text.toString().trim()
        val model = inputModel.text.toString().trim()
        val color = inputColor.text.toString().trim() // Color input
        val description = inputDescription.text.toString().trim()
        val price = inputPrice.text.toString().trim()
        val imageUrl = inputImageUrl.text.toString().trim() // Get the URL entered by the user

        // Validate inputs
        if (name.isEmpty()) {
            inputName.error = "Name is required"
            inputName.requestFocus()
            return
        }
        if (model.isEmpty()) {
            inputModel.error = "Model is required"
            inputModel.requestFocus()
            return
        }

        if (color.isEmpty()) {
            inputColor.error = "Color is required"
            inputColor.requestFocus()
            return
        }
        if (description.isEmpty()) {
            inputDescription.error = "Description is required"
            inputDescription.requestFocus()
            return
        }
        if (price.isEmpty()) {
            inputPrice.error = "Price is required"
            inputPrice.requestFocus()
            return
        }
        if (imageUrl.isEmpty()) {
            inputImageUrl.error = "Link is required"
            inputImageUrl.requestFocus()
            return
        }
        // Ensure price is a valid number
        val priceValue = price.toDoubleOrNull()
        if (priceValue == null || priceValue <= 0) {
            inputPrice.error = "Please enter a valid price"
            inputPrice.requestFocus()
            return
        }

        // Get selected vehicle type (Bike or Car) from the RadioGroup
        val selectedVehicleTypeId = rgVehicleType.checkedRadioButtonId
        val vehicleType = if (selectedVehicleTypeId == R.id.rb_bike) {
            "Bike"
        } else if (selectedVehicleTypeId == R.id.rb_car) {
            "Car"
        } else {
            Toast.makeText(this, "Please select a vehicle type", Toast.LENGTH_SHORT).show()
            rgVehicleType.requestFocus()
            return
        }

        // Get current user UID
        val currentUser = firebaseAuth.currentUser
        if (currentUser == null) {
            Toast.makeText(this, "You must be logged in to post an ad", Toast.LENGTH_SHORT).show()
            return
        }
        val uid = currentUser.uid

        // Prepare ad data
        val adData = hashMapOf(
            "name" to name,
            "model" to model,
            "vehicleType" to vehicleType, // Save the selected vehicle type (Bike or Car)
            "color" to color,
            "description" to description,
            "price" to price,
            "imageUrl" to imageUrl,
            "uid" to uid,
            "timestamp" to System.currentTimeMillis()
        )

        // Upload ad data to Firestore
        firestore.collection("ads")
            .add(adData)  // Using add to store multiple ads under the 'ads' collection
            .addOnSuccessListener {
                Toast.makeText(this, "Ad posted successfully", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to post ad: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

}