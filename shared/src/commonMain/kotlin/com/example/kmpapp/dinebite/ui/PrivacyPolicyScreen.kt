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

@Composable
fun DineBitePrivacyPolicyScreen(backStack: NavBackStack<NavKey>) {
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
                Text("Privacy Policy", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(20.dp))

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                val sections = listOf(
                    "1. Information We Collect" to "We collect personal information such as your name, contact phone number, delivery addresses, and payment references necessary to process orders efficiently.",
                    "2. How We Use Information" to "Your data is used solely to facilitate order deliveries, process transaction receipts, communicate live status updates, and recommend personalized food preferences.",
                    "3. Data Security" to "All sensitive network communications and payment requests are encrypted with industry-standard 256-bit SSL protocols. We do not store full credit/debit card numbers on our local databases.",
                    "4. Third-Party Sharing" to "Delivery details (address, driver contact, recipient name) are shared only with our trusted fleet drivers for direct order fulfillment."
                )

                items(sections.size) { i ->
                    val sec = sections[i]
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
                            Text(sec.first, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color(0xFFFF6B00))
                            Text(sec.second, fontSize = 12.sp, color = Color.DarkGray, lineHeight = 18.sp)
                        }
                    }
                }
            }
        }
    }
}
