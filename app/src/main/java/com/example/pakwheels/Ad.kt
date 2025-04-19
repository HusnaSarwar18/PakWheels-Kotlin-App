package com.example.pakwheels

import java.io.Serializable

// Common Ad class used by both fragments
data class Ad(
    val name: String = "",
    val model: String = "",
    val vehicleType: String = "",
    val color: String = "",
    val description: String = "",
    val price: String = "",
    val imageUrl: String? = null,
    val userId: String = "",
    val timestamp: Long = 0
) : Serializable
