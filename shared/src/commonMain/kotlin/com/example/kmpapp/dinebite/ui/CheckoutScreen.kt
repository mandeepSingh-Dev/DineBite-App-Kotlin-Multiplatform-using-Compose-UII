package com.example.kmpapp.dinebite.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.example.kmpapp.dinebite.navigation.DineBiteScreens
import com.example.kmpapp.dinebite.util.NotificationHelper

@Composable
fun DineBiteCheckoutScreen(backStack: NavBackStack<NavKey>) {
    var selectedInstruction by remember { mutableStateOf("Leave at door") }
    var selectedPayment by remember { mutableStateOf("Apple Pay") }

    val totalAmount = DineBiteRepository.cartItems.sumOf { it.price * it.quantity } + 20 + 286

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAF7F2))
            .systemBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "‹ Back",
                    color = Color(0xFFFF6B00),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { backStack.removeLastOrNull() }
                )
                Spacer(modifier = Modifier.weight(1f))
                Text("Checkout", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                            Text("🏠", fontSize = 16.sp)
                            Text("Home Delivery", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                        }
                        Text("Change", color = Color(0xFFFF6B00), fontWeight = FontWeight.Bold, fontSize = 13.sp, modifier = Modifier.clickable { backStack.add(DineBiteScreens.MyAddresses) })
                    }
                    Text("B-120, Green Park, New Delhi - 110016", fontSize = 13.sp, color = Color.DarkGray)
                    Text("Landmark: Near Green Park Market", fontSize = 12.sp, color = Color.Gray)
                    Text("Phone: +91 98765 43210", fontSize = 12.sp, color = Color.Gray)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text("Delivery Instructions", fontWeight = FontWeight.Bold, fontSize = 15.sp)
            Spacer(modifier = Modifier.height(10.dp))

            val instructions = listOf("Leave at door", "Avoid ringing bell", "Call upon arrival", "Leave with guard", "Beware of pets", "Contactless delivery")
            instructions.forEach { inst ->
                val isSelected = selectedInstruction == inst
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable { selectedInstruction = inst },
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = if (isSelected) androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFFFF6B00)) else null
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(inst, fontWeight = FontWeight.Medium, fontSize = 14.sp)
                        RadioButton(
                            selected = isSelected,
                            onClick = { selectedInstruction = inst },
                            colors = RadioButtonDefaults.colors(selectedColor = Color(0xFFFF6B00))
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text("Payment Method", fontWeight = FontWeight.Bold, fontSize = 15.sp)
            Spacer(modifier = Modifier.height(10.dp))

            val payments = listOf("Apple Pay" to "Fast 1-touch payment", "Credit / Debit Card" to "Visa, Mastercard, RuPay", "UPI / QR Code" to "Google Pay, PhonePe, Paytm", "Cash on Delivery" to "Pay in cash at your doorstep")
            payments.forEach { (method, desc) ->
                val isSelected = selectedPayment == method
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable { selectedPayment = method },
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = if (isSelected) androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFFFF6B00)) else null
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(method, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Text(desc, fontSize = 11.sp, color = Color.Gray)
                        }
                        RadioButton(
                            selected = isSelected,
                            onClick = { selectedPayment = method },
                            colors = RadioButtonDefaults.colors(selectedColor = Color(0xFFFF6B00))
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = {
                    val itemCount = DineBiteRepository.cartItems.sumOf { it.quantity }
                    val orderId = "#DB${(10000..99999).random()}"
                    DineBiteRepository.orders.add(
                        0,
                        OrderItem(
                            orderId = orderId,
                            itemsSummary = "$itemCount Items • ₹$totalAmount",
                            totalPrice = totalAmount,
                            time = "Just now",
                            status = "Preparing"
                        )
                    )
                    DineBiteRepository.notifications.add(
                        0,
                        NotificationItem(
                            id = (DineBiteRepository.notifications.size + 1).toString(),
                            title = "Order Placed Successfully! 🎉",
                            message = "Your order $orderId has been placed and is preparing.",
                            time = "Just now",
                            isRead = false
                        )
                    )
                    try {
                        NotificationHelper().showNotification("Order Placed Successfully! 🎉", "Order $orderId is confirmed and preparing.")
                    } catch (e: Exception) {}
                    DineBiteRepository.cartItems.clear()
                    backStack.add(DineBiteScreens.OrderSuccess)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6B00))
            ) {
                Text(
                    text = "Place Order • ₹$totalAmount",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
