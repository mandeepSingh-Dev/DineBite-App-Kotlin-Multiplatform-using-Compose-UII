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
fun DineBiteTermsConditionsScreen(backStack: NavBackStack<NavKey>) {
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
                Text("Terms & Conditions", fontSize = 18.sp, fontWeight = FontWeight.Bold)
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
                    "1. Acceptance of Terms" to "By accessing or using the DineBite application, you agree to be bound by these Terms and Conditions and all applicable laws and regulations.",
                    "2. Orders and Billing" to "All orders placed via DineBite are subject to item availability. Prices are stated in INR and inclusive of applicable GST unless explicitly specified. Cancellation is permitted only until the kitchen begins food preparation.",
                    "3. Delivery and Pickup" to "Estimated delivery times are indicative and may vary based on weather conditions and peak hour road traffic. DineBite is committed to maintaining food freshness and temperature standards.",
                    "4. User Conduct" to "Users must provide accurate delivery and contact credentials. Any fraudulent use of promotional vouchers or harassment of delivery partners will result in immediate account termination."
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
