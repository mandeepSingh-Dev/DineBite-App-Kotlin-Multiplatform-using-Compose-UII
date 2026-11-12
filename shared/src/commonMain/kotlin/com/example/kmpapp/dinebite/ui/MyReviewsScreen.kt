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
fun DineBiteMyReviewsScreen(backStack: NavBackStack<NavKey>) {
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
                Text("My Reviews", fontSize = 18.sp, fontWeight = FontWeight.Bold)
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
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Text("4.9", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Color(0xFFFF6B00))
                                Text("★", fontSize = 28.sp, color = Color(0xFFFF6B00))
                            }
                            Text("Based on 3 order reviews", fontSize = 12.sp, color = Color.Gray)
                            Spacer(modifier = Modifier.height(16.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceAround
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text("★ 5.0", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                    Text("Food Quality", fontSize = 11.sp, color = Color.Gray)
                                }
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text("★ 4.8", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                    Text("Packaging", fontSize = 11.sp, color = Color.Gray)
                                }
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text("★ 4.9", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                    Text("Delivery Speed", fontSize = 11.sp, color = Color.Gray)
                                }
                            }
                        }
                    }
                }

                item {
                    Text("Recent Feedback", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }

                items(3) { index ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier.size(40.dp).background(Color(0xFFFFECE5), RoundedCornerShape(8.dp)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text("🍔", fontSize = 18.sp)
                                    }
                                    Column {
                                        Text(if (index == 0) "Classic Smash Cheeseburger" else if (index == 1) "Truffle Mushroom Pasta" else "Margherita Pizza", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                        Text(if (index == 0) "02 Nov 2026 • ★★★★★" else if (index == 1) "24 Oct 2026 • ★★★★★" else "12 Oct 2026 • ★★★★☆", fontSize = 11.sp, color = Color(0xFFFFB300))
                                    }
                                }
                                Text("🗑️", fontSize = 14.sp)
                            }
                            Text(
                                if (index == 0) "\"The bun was incredibly soft and the double patty had an amazing crust! Packaging kept it super warm. Definitely ordering again.\""
                                else if (index == 1) "\"Creamy truffle aroma filled the entire room. Perfectly al dente pasta. 10/10 dining experience at home.\""
                                else "\"Authentic sourdough crust with fresh buffalo mozzarella. Slightly mild sauce, but fresh basil made up for it.\"",
                                fontSize = 12.sp,
                                color = Color.DarkGray
                            )
                        }
                    }
                }
            }
        }
    }
}
