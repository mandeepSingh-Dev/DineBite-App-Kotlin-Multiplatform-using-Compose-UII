package com.example.kmpapp.dinebite.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
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

@Composable
fun DineBiteHelpSupportScreen(backStack: NavBackStack<NavKey>) {
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
                Text("Help & Support", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(20.dp))

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Text("Direct Support Channels", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Card(
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text("📞", fontSize = 20.sp)
                                Spacer(modifier = Modifier.height(8.dp))
                                Text("Toll Free Call", fontSize = 11.sp, color = Color.Gray)
                                Text("1800 233 4455", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            }
                        }
                        Card(
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text("✉️", fontSize = 20.sp)
                                Spacer(modifier = Modifier.height(8.dp))
                                Text("Support Email", fontSize = 11.sp, color = Color.Gray)
                                Text("support@dinebite.com", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            }
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Frequently Asked Questions", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                }

                val faqs = listOf(
                    "How do I track my active order?" to "You can track your live order status from the 'Orders' tab in the bottom bar. Once the kitchen confirms your meal, real-time driver updates will appear there.",
                    "What is your order cancellation policy?" to "Orders can be cancelled free of charge within 2 minutes of placement before kitchen preparation begins.",
                    "How do I apply promotional coupons?" to "In the Cart screen, tap on 'Apply Coupon' to select and apply active promotional discounts.",
                    "Can I schedule a table reservation in advance?" to "Yes! Use the 'Reserve a Table' feature on the Home screen to pick any date and time slot.",
                    "What payment methods are supported?" to "We support UPI, Credit/Debit cards, Netbanking, and Cash on Delivery."
                )

                items(faqs.size) { i ->
                    val faq = faqs[i]
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(faq.first, fontWeight = FontWeight.Bold, fontSize = 13.sp, modifier = Modifier.weight(1f))
                                Text("⌄", fontSize = 16.sp, color = Color.Gray)
                            }
                            if (i == 0) {
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(faq.second, fontSize = 12.sp, color = Color.DarkGray, lineHeight = 16.sp)
                            }
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        onClick = { backStack.add(DineBiteScreens.ContactUs) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(26.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6B00))
                    ) {
                        Text("✉️ Contact Us (Send Message)", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }
            }
        }
    }
}
