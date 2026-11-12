package com.example.kmpapp.core.network.apis

import com.example.kmpapp.core.network.NetworkHelper
import com.example.kmpapp.core.network.NetworkResult
import com.example.kmpapp.core.network.handleUseCaseException
import com.example.kmpapp.core.network.models.*
import io.ktor.client.HttpClient
import io.ktor.client.statement.HttpResponse
import io.ktor.client.request.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * DineBite API Service class handling all endpoints defined in the Postman collection.
 * Uses NetworkHelper for robust Flow-based execution, error handling, and retries with native Ktor HttpResponse.
 */
class DineBiteApi(
    private val httpClient: HttpClient,
    private val networkHelper: NetworkHelper
) {

    /**
     * Registers a new user account.
     */
    fun register(body: RegisterRequestBody): Flow<NetworkResult<ApiResponse<UserData>>> {
        return try {
            networkHelper.executeWithFlow(
                call = {
                    httpClient.post("auth/register") {
                        setBody(body)
                    }
                },
                onSuccess = { _ -> null },
                onError = { _ -> }
            )
        } catch (e: Exception) {
            flow {
                emit(NetworkResult.Error(handleUseCaseException(e)))
            }
        }
    }

    /**
     * Authenticates an existing user and returns an auth token.
     */
    fun login(body: LoginRequestBody): Flow<NetworkResult<ApiResponse<UserData>>> {
        return try {
            networkHelper.executeWithFlow(
                call = {
                    httpClient.post("auth/login") {
                        setBody(body)
                    }
                },
                onSuccess = { _ -> null },
                onError = { _ -> }
            )
        } catch (e: Exception) {
            flow {
                emit(NetworkResult.Error(handleUseCaseException(e)))
            }
        }
    }

    /**
     * Verifies OTP sent to user email.
     */
    fun verifyOtp(body: VerifyOtpRequestBody): Flow<NetworkResult<ApiResponse<Any?>>> {
        return try {
            networkHelper.executeWithFlow(
                call = {
                    httpClient.post("auth/verify-otp") {
                        setBody(body)
                    }
                },
                onSuccess = { _ -> null },
                onError = { _ -> }
            )
        } catch (e: Exception) {
            flow {
                emit(NetworkResult.Error(handleUseCaseException(e)))
            }
        }
    }

    /**
     * Requests a password reset email.
     */
    fun forgotPassword(body: ForgotPasswordRequestBody): Flow<NetworkResult<ApiResponse<Any?>>> {
        return try {
            networkHelper.executeWithFlow(
                call = {
                    httpClient.post("auth/forgot-password") {
                        setBody(body)
                    }
                },
                onSuccess = { _ -> null },
                onError = { _ -> }
            )
        } catch (e: Exception) {
            flow {
                emit(NetworkResult.Error(handleUseCaseException(e)))
            }
        }
    }

    /**
     * Resets user password using token / email.
     */
    fun resetPassword(body: ResetPasswordRequestBody): Flow<NetworkResult<ApiResponse<Any?>>> {
        return try {
            networkHelper.executeWithFlow(
                call = {
                    httpClient.post("auth/reset-password") {
                        setBody(body)
                    }
                },
                onSuccess = { _ -> null },
                onError = { _ -> }
            )
        } catch (e: Exception) {
            flow {
                emit(NetworkResult.Error(handleUseCaseException(e)))
            }
        }
    }

    /**
     * Fetches current authenticated user profile.
     */
    fun getMe(): Flow<NetworkResult<ApiResponse<UserData>>> {
        return try {
            networkHelper.executeWithFlow(
                call = {
                    httpClient.get("auth/me")
                },
                onSuccess = { _ -> null },
                onError = { _ -> }
            )
        } catch (e: Exception) {
            flow {
                emit(NetworkResult.Error(handleUseCaseException(e)))
            }
        }
    }

    /**
     * Updates user profile details.
     */
    fun updateProfile(body: UpdateProfileRequestBody): Flow<NetworkResult<ApiResponse<UserData>>> {
        return try {
            networkHelper.executeWithFlow(
                call = {
                    httpClient.patch("auth/me") {
                        setBody(body)
                    }
                },
                onSuccess = { _ -> null },
                onError = { _ -> }
            )
        } catch (e: Exception) {
            flow {
                emit(NetworkResult.Error(handleUseCaseException(e)))
            }
        }
    }

    /**
     * Fetches list of all restaurants, with optional query parameters (e.g. dineIn, search).
     */
    fun getRestaurants(dineIn: Boolean? = null, search: String? = null): Flow<NetworkResult<ApiResponse<List<RestaurantItem>>>> {
        return try {
            networkHelper.executeWithFlow(
                call = {
                    httpClient.get("restaurants") {
                        dineIn?.let { parameter("dineIn", it) }
                        search?.let { parameter("search", it) }
                    }
                },
                onSuccess = { _ -> null },
                onError = { _ -> }
            )
        } catch (e: Exception) {
            flow {
                emit(NetworkResult.Error(handleUseCaseException(e)))
            }
        }
    }

    /**
     * Fetches restaurant details and full menu by restaurant ID.
     */
    fun getRestaurantDetail(restaurantId: String): Flow<NetworkResult<ApiResponse<RestaurantDetailResponse>>> {
        return try {
            networkHelper.executeWithFlow(
                call = {
                    httpClient.get("restaurants/$restaurantId")
                },
                onSuccess = { _ -> null },
                onError = { _ -> }
            )
        } catch (e: Exception) {
            flow {
                emit(NetworkResult.Error(handleUseCaseException(e)))
            }
        }
    }

    /**
     * Fetches restaurant menu items.
     */
    fun getRestaurantMenu(restaurantId: String): Flow<NetworkResult<ApiResponse<List<MenuItem>>>> {
        return try {
            networkHelper.executeWithFlow(
                call = {
                    httpClient.get("restaurants/$restaurantId/menu")
                },
                onSuccess = { _ -> null },
                onError = { _ -> }
            )
        } catch (e: Exception) {
            flow {
                emit(NetworkResult.Error(handleUseCaseException(e)))
            }
        }
    }

    /**
     * Fetches restaurant reviews.
     */
    fun getRestaurantReviews(restaurantId: String): Flow<NetworkResult<ApiResponse<List<ReviewItem>>>> {
        return try {
            networkHelper.executeWithFlow(
                call = {
                    httpClient.get("restaurants/$restaurantId/reviews")
                },
                onSuccess = { _ -> null },
                onError = { _ -> }
            )
        } catch (e: Exception) {
            flow {
                emit(NetworkResult.Error(handleUseCaseException(e)))
            }
        }
    }

    /**
     * Lists available promotional coupons.
     */
    fun getCoupons(): Flow<NetworkResult<ApiResponse<List<CouponItem>>>> {
        return try {
            networkHelper.executeWithFlow(
                call = {
                    httpClient.get("coupons")
                },
                onSuccess = { _ -> null },
                onError = { _ -> }
            )
        } catch (e: Exception) {
            flow {
                emit(NetworkResult.Error(handleUseCaseException(e)))
            }
        }
    }

    /**
     * Applies a coupon code and calculates discount.
     */
    fun applyCoupon(body: ApplyCouponRequestBody): Flow<NetworkResult<ApiResponse<CouponApplyResponse>>> {
        return try {
            networkHelper.executeWithFlow(
                call = {
                    httpClient.post("coupons/apply") {
                        setBody(body)
                    }
                },
                onSuccess = { _ -> null },
                onError = { _ -> }
            )
        } catch (e: Exception) {
            flow {
                emit(NetworkResult.Error(handleUseCaseException(e)))
            }
        }
    }

    /**
     * Lists user orders.
     */
    fun getOrders(): Flow<NetworkResult<ApiResponse<List<OrderItemModel>>>> {
        return try {
            networkHelper.executeWithFlow(
                call = {
                    httpClient.get("orders")
                },
                onSuccess = { _ -> null },
                onError = { _ -> }
            )
        } catch (e: Exception) {
            flow {
                emit(NetworkResult.Error(handleUseCaseException(e)))
            }
        }
    }

    /**
     * Creates a new food order.
     */
    fun createOrder(body: CreateOrderRequestBody): Flow<NetworkResult<ApiResponse<OrderItemModel>>> {
        return try {
            networkHelper.executeWithFlow(
                call = {
                    httpClient.post("orders") {
                        setBody(body)
                    }
                },
                onSuccess = { _ -> null },
                onError = { _ -> }
            )
        } catch (e: Exception) {
            flow {
                emit(NetworkResult.Error(handleUseCaseException(e)))
            }
        }
    }

    /**
     * Cancels an existing order by ID.
     */
    fun cancelOrder(orderId: String): Flow<NetworkResult<ApiResponse<OrderItemModel>>> {
        return try {
            networkHelper.executeWithFlow(
                call = {
                    httpClient.patch("orders/$orderId/cancel")
                },
                onSuccess = { _ -> null },
                onError = { _ -> }
            )
        } catch (e: Exception) {
            flow {
                emit(NetworkResult.Error(handleUseCaseException(e)))
            }
        }
    }

    /**
     * Lists table reservations / bookings.
     */
    fun getBookings(): Flow<NetworkResult<ApiResponse<List<BookingItemModel>>>> {
        return try {
            networkHelper.executeWithFlow(
                call = {
                    httpClient.get("bookings")
                },
                onSuccess = { _ -> null },
                onError = { _ -> }
            )
        } catch (e: Exception) {
            flow {
                emit(NetworkResult.Error(handleUseCaseException(e)))
            }
        }
    }

    /**
     * Creates a new table booking / reservation.
     */
    fun createBooking(body: CreateBookingRequestBody): Flow<NetworkResult<ApiResponse<BookingItemModel>>> {
        return try {
            networkHelper.executeWithFlow(
                call = {
                    httpClient.post("bookings") {
                        setBody(body)
                    }
                },
                onSuccess = { _ -> null },
                onError = { _ -> }
            )
        } catch (e: Exception) {
            flow {
                emit(NetworkResult.Error(handleUseCaseException(e)))
            }
        }
    }

    /**
     * Lists saved delivery addresses.
     */
    fun getAddresses(): Flow<NetworkResult<ApiResponse<List<AddressItemModel>>>> {
        return try {
            networkHelper.executeWithFlow(
                call = {
                    httpClient.get("addresses")
                },
                onSuccess = { _ -> null },
                onError = { _ -> }
            )
        } catch (e: Exception) {
            flow {
                emit(NetworkResult.Error(handleUseCaseException(e)))
            }
        }
    }

    /**
     * Creates a new delivery address.
     */
    fun createAddress(body: CreateAddressRequestBody): Flow<NetworkResult<ApiResponse<AddressItemModel>>> {
        return try {
            networkHelper.executeWithFlow(
                call = {
                    httpClient.post("addresses") {
                        setBody(body)
                    }
                },
                onSuccess = { _ -> null },
                onError = { _ -> }
            )
        } catch (e: Exception) {
            flow {
                emit(NetworkResult.Error(handleUseCaseException(e)))
            }
        }
    }

    /**
     * Searches for dishes across restaurants.
     */
    fun searchDishes(query: String): Flow<NetworkResult<ApiResponse<List<DishItemModel>>>> {
        return try {
            networkHelper.executeWithFlow(
                call = {
                    httpClient.get("dishes/search") {
                        parameter("q", query)
                    }
                },
                onSuccess = { _ -> null },
                onError = { _ -> }
            )
        } catch (e: Exception) {
            flow {
                emit(NetworkResult.Error(handleUseCaseException(e)))
            }
        }
    }
}
