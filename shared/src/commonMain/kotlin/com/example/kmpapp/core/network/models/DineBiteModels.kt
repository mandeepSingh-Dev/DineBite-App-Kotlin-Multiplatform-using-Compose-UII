package com.example.kmpapp.core.network.models

import kotlinx.serialization.Serializable

/**
 * Request body for user registration.
 */
@Serializable
data class RegisterRequestBody(
    val name: String? = null,
    val email: String? = null,
    val phone: String? = null,
    val password: String? = null
)

/**
 * Request body for user login.
 */
@Serializable
data class LoginRequestBody(
    val email: String? = null,
    val phone: String? = null,
    val password: String? = null
)

/**
 * Request body for OTP verification.
 */
@Serializable
data class VerifyOtpRequestBody(
    val email: String? = null,
    val otp: String? = null
)

/**
 * Request body for forgot password.
 */
@Serializable
data class ForgotPasswordRequestBody(
    val email: String? = null
)

/**
 * Request body for password reset.
 */
@Serializable
data class ResetPasswordRequestBody(
    val email: String? = null,
    val password: String? = null
)

/**
 * Request body for updating user profile.
 */
@Serializable
data class UpdateProfileRequestBody(
    val name: String? = null,
    val phone: String? = null
)

/**
 * Request body for applying a discount coupon.
 */
@Serializable
data class ApplyCouponRequestBody(
    val code: String? = null,
    val itemTotal: Int? = null
)

/**
 * Order item detail model for creating an order.
 */
@Serializable
data class OrderItemRequestBody(
    val menuItemId: String? = null,
    val quantity: Int? = null
)

/**
 * Request body for creating an order.
 */
@Serializable
data class CreateOrderRequestBody(
    val restaurantId: String? = null,
    val items: List<OrderItemRequestBody>? = null,
    val couponCode: String? = null,
    val paymentMethod: String? = null,
    val address: String? = null
)

/**
 * Request body for creating a table booking / reservation.
 */
@Serializable
data class CreateBookingRequestBody(
    val restaurantId: String? = null,
    val guests: Int? = null,
    val date: String? = null,
    val slot: String? = null,
    val area: String? = null,
    val request: String? = null
)

/**
 * Request body for creating a delivery address.
 */
@Serializable
data class CreateAddressRequestBody(
    val tag: String? = null,
    val line: String? = null,
    val phone: String? = null,
    val isDefault: Boolean? = null
)

/**
 * Request body for submitting a review.
 */
@Serializable
data class ReviewRequestBody(
    val callId: String? = null,
    val comment: String? = null,
    val partnerId: String? = null,
    val rating: Int? = null
)

/**
 * Restaurant model representing restaurant listings.
 */
@Serializable
data class RestaurantItem(
    val id: String? = null,
    val name: String? = null,
    val description: String? = null,
    val imageUrl: String? = null,
    val rating: Double? = null,
    val reviewsCount: Int? = null,
    val dineIn: Boolean? = null,
    val address: String? = null
)

/**
 * Menu item model representing dishes.
 */
@Serializable
data class MenuItem(
    val id: String? = null,
    val name: String? = null,
    val description: String? = null,
    val price: Int? = null,
    val imageUrl: String? = null,
    val category: String? = null,
    val rating: Double? = null
)

/**
 * Restaurant detail response model including restaurant info and menu.
 */
@Serializable
data class RestaurantDetailResponse(
    val restaurant: RestaurantItem? = null,
    val menu: List<MenuItem>? = null
)

/**
 * Review item model.
 */
@Serializable
data class ReviewItem(
    val id: String? = null,
    val userName: String? = null,
    val rating: Int? = null,
    val comment: String? = null,
    val date: String? = null
)

/**
 * Coupon model.
 */
@Serializable
data class CouponItem(
    val id: String? = null,
    val code: String? = null,
    val discountPercentage: Int? = null,
    val minOrderAmount: Int? = null,
    val description: String? = null
)

/**
 * Coupon apply response model.
 */
@Serializable
data class CouponApplyResponse(
    val valid: Boolean? = null,
    val discountAmount: Int? = null,
    val finalAmount: Int? = null,
    val message: String? = null
)

/**
 * Order response model.
 */
@Serializable
data class OrderItemModel(
    val id: String? = null,
    val orderId: String? = null,
    val restaurantId: String? = null,
    val items: List<OrderItemRequestBody>? = null,
    val totalPrice: Int? = null,
    val status: String? = null,
    val createdAt: String? = null
)

/**
 * Booking response model.
 */
@Serializable
data class BookingItemModel(
    val id: String? = null,
    val restaurantId: String? = null,
    val guests: Int? = null,
    val date: String? = null,
    val slot: String? = null,
    val area: String? = null,
    val status: String? = null
)

/**
 * Address response model.
 */
@Serializable
data class AddressItemModel(
    val id: String? = null,
    val tag: String? = null,
    val line: String? = null,
    val phone: String? = null,
    val isDefault: Boolean? = null
)

/**
 * Category model.
 */
@Serializable
data class CategoryItemModel(
    val id: String? = null,
    val name: String? = null,
    val imageUrl: String? = null
)

/**
 * Dish search item model.
 */
@Serializable
data class DishItemModel(
    val id: String? = null,
    val name: String? = null,
    val price: Int? = null,
    val restaurantName: String? = null,
    val imageUrl: String? = null
)

/**
 * Generic API wrapper response model.
 */
@Serializable
data class ApiResponse<T>(
    val status: Boolean? = null,
    val success: Boolean? = null,
    val message: String? = null,
    val data: T? = null
)

@Serializable
data class UserData(
    val id: String? = null,
    val name: String? = null,
    val email: String? = null,
    val phone: String? = null,
    val token: String? = null
)
