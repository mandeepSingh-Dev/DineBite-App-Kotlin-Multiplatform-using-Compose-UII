package com.example.kmpapp.dinebite.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import coil3.compose.AsyncImage
import com.example.kmpapp.dinebite.navigation.DineBiteScreens

@Composable
fun DineBiteOrdersScreen(backStack: NavBackStack<NavKey>) {
    var selectedTab by remember { mutableStateOf("All Orders") }
    val orders = DineBiteRepository.orders
    val activeCount = orders.count { it.status == "Preparing" }

    val filteredOrders = when {
        selectedTab.startsWith("Active") -> orders.filter { it.status == "Preparing" }
        selectedTab == "Completed" -> orders.filter { it.status == "Delivered" }
        selectedTab == "Cancelled" -> orders.filter { it.status == "Cancelled" }
        else -> orders
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAF7F2))
            .systemBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 65.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp)
            ) {
                Text("My Orders", fontSize = 26.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                Spacer(modifier = Modifier.height(2.dp))
                Text("Track, manage and view your orders", fontSize = 13.sp, color = Color.Gray)

                Spacer(modifier = Modifier.height(16.dp))

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    val tabs = listOf("All Orders", "Active ($activeCount)", "Completed", "Cancelled")
                    items(tabs) { tab ->
                        val isSelected = selectedTab == tab
                        Button(
                            onClick = { selectedTab = tab },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isSelected) Color(0xFFFF6B00) else Color.White
                            ),
                            shape = RoundedCornerShape(20.dp),
                            contentPadding = PaddingValues(horizontal = 18.dp, vertical = 8.dp),
                            elevation = ButtonDefaults.buttonElevation(defaultElevation = if (isSelected) 2.dp else 0.dp)
                        ) {
                            Text(
                                text = tab,
                                color = if (isSelected) Color.White else Color.Black,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                fontSize = 13.sp
                            )
                        }
                    }
                }
            }

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                items(filteredOrders) { order ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .size(56.dp)
                                            .clip(RoundedCornerShape(12.dp))
                                            .background(Color(0xFFFFECE5))
                                    ) {
                                        AsyncImage(
                                            model = sharedImageUrls[1],
                                            contentDescription = "Order Item",
                                            modifier = Modifier.fillMaxSize(),
                                            contentScale = ContentScale.Crop
                                        )
                                    }
                                    Column {
                                        Text("Order ${order.orderId}", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                                        Text(order.itemsSummary, fontSize = 12.sp, color = Color.Gray)
                                        Text(order.time, fontSize = 11.sp, color = Color.LightGray)
                                    }
                                }
                                Surface(
                                    color = if (order.status == "Delivered") Color(0xFFE8F5E9) else Color(0xFFFFECE5),
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Text(
                                        if (order.status == "Delivered") "✓ Delivered" else "🟢 Preparing",
                                        color = if (order.status == "Delivered") Color(0xFF2E7D32) else Color(0xFFFF6B00),
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    )
                                }
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("✓ Confirmed", fontSize = 11.sp, color = Color(0xFFFF6B00), fontWeight = FontWeight.Medium)
                                Text("✓ Preparing", fontSize = 11.sp, color = Color(0xFFFF6B00), fontWeight = FontWeight.Medium)
                                Text("○ On the Way", fontSize = 11.sp, color = Color.Gray)
                                Text("○ Delivered", fontSize = 11.sp, color = Color.Gray)
                            }

                            HorizontalDivider(color = Color(0xFFF0EBE1))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                OutlinedButton(
                                    onClick = {},
                                    modifier = Modifier.weight(1f).height(40.dp),
                                    shape = RoundedCornerShape(20.dp)
                                ) {
                                    Text("View Details", fontSize = 12.sp, color = Color.Black, fontWeight = FontWeight.Bold)
                                }
                                Button(
                                    onClick = { backStack.add(DineBiteScreens.TrackOrder) },
                                    modifier = Modifier.weight(1f).height(40.dp),
                                    shape = RoundedCornerShape(20.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6B00))
                                ) {
                                    Text(if (order.status == "Delivered") "↻ Reorder" else "📍 Track Order", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
                                }
                            }
                        }
                    }
                }
            }
        }

        Surface(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .navigationBarsPadding(),
            color = Color.White,
            shadowElevation = 8.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable {
                        backStack.removeLastOrNull()
                        backStack.add(DineBiteScreens.Home)
                    }
                ) {
                    Text("🏠", fontSize = 18.sp)
                    Text("Home", fontSize = 11.sp, color = Color.Gray)
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable {
                        backStack.removeLastOrNull()
                        backStack.add(DineBiteScreens.Menu)
                    }
                ) {
                    Text("🍽️", fontSize = 18.sp)
                    Text("Menu", fontSize = 11.sp, color = Color.Gray)
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable { backStack.add(DineBiteScreens.Cart) }
                ) {
                    Box {
                        Text("🛒", fontSize = 18.sp)
                        val cartCount = DineBiteRepository.cartItems.sumOf { it.quantity }
                        if (cartCount > 0) {
                            Badge(
                                containerColor = Color(0xFFFF6B00),
                                modifier = Modifier.align(Alignment.TopEnd)
                            ) {
                                Text("$cartCount", fontSize = 9.sp, color = Color.White)
                            }
                        }
                    }
                    Text("Cart", fontSize = 11.sp, color = Color.Gray)
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable {
                        backStack.removeLastOrNull()
                        backStack.add(DineBiteScreens.Orders)
                    }
                ) {
                    Text("📦", fontSize = 18.sp)
                    Text("Orders", fontSize = 11.sp, color = Color(0xFFFF6B00), fontWeight = FontWeight.Bold)
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable {
                        backStack.removeLastOrNull()
                        backStack.add(DineBiteScreens.Profile)
                    }
                ) {
                    Text("👤", fontSize = 18.sp)
                    Text("Profile", fontSize = 11.sp, color = Color.Gray)
                }
            }
        }
    }
}
