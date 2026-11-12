package com.example.kmpapp.dinebite.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.example.kmpapp.dinebite.navigation.DineBiteScreens

@Composable
fun DineBiteProfileScreen(backStack: NavBackStack<NavKey>) {
    val profile = DineBiteRepository.userProfile
    var showLogoutDialog by remember { mutableStateOf(false) }

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
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFF6B00), RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
                    .padding(24.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(70.dp)
                            .clip(CircleShape)
                            .background(Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("👤", fontSize = 32.sp)
                    }
                    Column {
                        Text(profile.name, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        Text(profile.phone, fontSize = 13.sp, color = Color.White.copy(alpha = 0.9f))
                        Text(profile.email, fontSize = 12.sp, color = Color.White.copy(alpha = 0.8f))
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .clickable { backStack.add(DineBiteScreens.Orders) },
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text("🛍️", fontSize = 22.sp)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text("My Orders", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            }
                        }

                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .clickable { backStack.add(DineBiteScreens.MyBookings) },
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text("📅", fontSize = 22.sp)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text("My Bookings", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            }
                        }
                    }
                }

                val options = listOf(
                    Triple("👤", "My Profile", "View and edit your profile") to DineBiteScreens.EditProfile,
                    Triple("📍", "My Addresses", "Manage delivery addresses") to DineBiteScreens.MyAddresses,
                    Triple("⭐", "My Reviews", "Your food and restaurant reviews") to DineBiteScreens.MyReviews,
                    Triple("📄", "Terms & Conditions", "Read our terms of service") to DineBiteScreens.TermsConditions,
                    Triple("🔒", "Privacy Policy", "How we protect and use your data") to DineBiteScreens.PrivacyPolicy,
                    Triple("❓", "Help & Support", "Get help, chat or report an issue") to DineBiteScreens.HelpSupport
                )

                items(options) { (info, route) ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { backStack.add(route) },
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFFFFECE5)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(info.first, fontSize = 16.sp)
                                }
                                Column {
                                    Text(info.second, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                    Text(info.third, fontSize = 11.sp, color = Color.Gray)
                                }
                            }
                            Text("›", fontSize = 20.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                item {
                    OutlinedButton(
                        onClick = { showLogoutDialog = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(26.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFFF6B00)),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFF6B00))
                    ) {
                        Text("🚪 Logout", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(10.dp))
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
                    Text("🛒", fontSize = 18.sp)
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
                    Text("Orders", fontSize = 11.sp, color = Color.Gray)
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable { backStack.add(DineBiteScreens.Profile) }
                ) {
                    Text("👤", fontSize = 18.sp)
                    Text("Profile", fontSize = 11.sp, color = Color(0xFFFF6B00), fontWeight = FontWeight.Bold)
                }
            }
        }

        if (showLogoutDialog) {
            AlertDialog(
                onDismissRequest = { showLogoutDialog = false },
                title = { Text("Log Out", fontWeight = FontWeight.Bold) },
                text = { Text("Are you sure you want to log out of DineBite?") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            showLogoutDialog = false
                            DineBiteRepository.cartItems.clear()
                            backStack.clear()
                            backStack.add(DineBiteScreens.Login)
                        }
                    ) {
                        Text("Log Out", color = Color(0xFFFF3D00), fontWeight = FontWeight.Bold)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showLogoutDialog = false }) {
                        Text("Cancel", color = Color.Gray)
                    }
                },
                containerColor = Color.White,
                shape = RoundedCornerShape(20.dp)
            )
        }
    }
}
