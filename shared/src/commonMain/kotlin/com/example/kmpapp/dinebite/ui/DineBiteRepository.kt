package com.example.kmpapp.dinebite.ui

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

data class UserProfile(
    var name: String = "Priya Sharma",
    var email: String = "priya.sharma@example.com",
    var phone: String = "+91 98765 43210",
    var gender: String = "Female"
)

data class AddressItem(
    val id: String,
    val type: String,
    val address: String,
    val landmark: String,
    val phone: String,
    val isDefault: Boolean
)

data class BookingItem(
    val date: String,
    val time: String,
    val guests: Int,
    val seating: String,
    val note: String
)

data class CartItem(
    val id: String,
    val name: String,
    val variant: String,
    val price: Int,
    var quantity: Int,
    val imageUrl: String
)

data class OrderItem(
    val orderId: String,
    val itemsSummary: String,
    val totalPrice: Int,
    val time: String,
    val status: String
)

data class NotificationItem(
    val id: String,
    val title: String,
    val message: String,
    val time: String,
    var isRead: Boolean = false
)

object DineBiteRepository {
    var isLoggedIn by mutableStateOf(false)
    var userProfile by mutableStateOf(UserProfile())

    val addresses = mutableStateListOf(
        AddressItem("1", "Home", "B-120, Green Park, New Delhi - 110016", "Near Green Park Market", "+91 98765 43210", true),
        AddressItem("2", "Work", "Building 4B, Cyber City, DLF Phase 2, Gurugram - 122002", "Opposite Rapid Metro Station", "+91 98765 43210", false),
        AddressItem("3", "Other", "Flat 402, Sunshine Residency, Noida Sector 62 - 201309", "Near Fortis Hospital", "+91 98111 22334", false)
    )

    val bookings = mutableStateListOf(
        BookingItem("Wed, 11 Nov 2026", "8:00 PM", 4, "Rooftop", "Window seat preferred"),
        BookingItem("Thu, 19 Nov 2026", "7:00 PM", 4, "Indoor", "No special requests")
    )

    val cartItems = mutableStateListOf<CartItem>()

    val orders = mutableStateListOf(
        OrderItem("#DB95929", "10 Items • ₹3757", 3757, "Just now", "Preparing"),
        OrderItem("#DB98321", "3 Items • ₹1000", 1000, "Today, 12:30 PM", "Preparing"),
        OrderItem("#DB60355", "3 Items • ₹1020", 1020, "Yesterday, 8:15 PM", "Delivered")
    )

    val notifications = mutableStateListOf(
        NotificationItem("1", "Welcome to DineBite! 🎉", "Enjoy gourmet food delivered hot to your doorstep.", "10:00 AM", true),
        NotificationItem("2", "Special 40% OFF Weekend Deal", "Use code GOURMET40 on your next large pizza order.", "Yesterday", false)
    )
}
