package com.example.kmpapp.dinebite.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
fun DineBiteCartScreen(backStack: NavBackStack<NavKey>) {
    val cartItems = DineBiteRepository.cartItems

    val totalItems = cartItems.sumOf { it.quantity }
    val itemTotal = cartItems.sumOf { it.price * it.quantity }
    val deliveryFee = if (totalItems > 0) 20 else 0
    val taxes = if (totalItems > 0) 286 else 0
    val totalAmount = itemTotal + deliveryFee + taxes

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAF7F2))
            .systemBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 120.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("Your Cart", fontSize = 26.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                    Spacer(modifier = Modifier.height(2.dp))
                    Text("$totalItems items in your cart", fontSize = 13.sp, color = Color.Gray)
                }
                if (totalItems > 0) {
                    OutlinedButton(
                        onClick = { cartItems.clear() },
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Gray),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                        modifier = Modifier.height(34.dp)
                    ) {
                        Text("🗑️ Clear Cart", fontSize = 12.sp, color = Color.Gray)
                    }
                }
            }

            if (cartItems.isEmpty()) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("🛒", fontSize = 48.sp)
                        Spacer(modifier = Modifier.height(12.dp))
                        Text("Your cart is empty", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Add appetizing gourmet dishes from the menu!", fontSize = 12.sp, color = Color.Gray)
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(cartItems, key = { it.id + it.variant }) { item ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(72.dp)
                                        .clip(RoundedCornerShape(14.dp))
                                        .background(Color(0xFFFFECE5))
                                ) {
                                    AsyncImage(
                                        model = item.imageUrl,
                                        contentDescription = item.name,
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                                Column(modifier = Modifier.weight(1f)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.Top
                                    ) {
                                        Text(item.name, fontWeight = FontWeight.Bold, fontSize = 14.sp, maxLines = 1, modifier = Modifier.weight(1f))
                                        Text(
                                            "🗑️",
                                            fontSize = 14.sp,
                                            modifier = Modifier.clickable {
                                                cartItems.remove(item)
                                            }
                                        )
                                    }
                                    Text(item.variant, fontSize = 11.sp, color = Color.Gray)
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text("₹${item.price * item.quantity}", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color.Black)
                                        Row(
                                            modifier = Modifier
                                                .background(Color(0xFFFFECE5), RoundedCornerShape(20.dp))
                                                .padding(horizontal = 8.dp, vertical = 4.dp),
                                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                "-",
                                                fontWeight = FontWeight.Bold,
                                                color = Color(0xFFFF6B00),
                                                fontSize = 14.sp,
                                                modifier = Modifier.clickable {
                                                    val index = cartItems.indexOf(item)
                                                    if (index >= 0) {
                                                        if (item.quantity > 1) {
                                                            cartItems[index] = item.copy(quantity = item.quantity - 1)
                                                        } else {
                                                            cartItems.removeAt(index)
                                                        }
                                                    }
                                                }
                                            )
                                            Text("${item.quantity}", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                            Text(
                                                "+",
                                                fontWeight = FontWeight.Bold,
                                                color = Color(0xFFFF6B00),
                                                fontSize = 14.sp,
                                                modifier = Modifier.clickable {
                                                    val index = cartItems.indexOf(item)
                                                    if (index >= 0) {
                                                        cartItems[index] = item.copy(quantity = item.quantity + 1)
                                                    }
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth().clickable {},
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("🏷️ Apply Coupon", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFFFF6B00))
                                Text("›", fontSize = 20.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                            }
                        }
                    }

                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Text("Order Summary", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text("Item Total ($totalItems Items)", fontSize = 13.sp, color = Color.Gray)
                                    Text("₹$itemTotal", fontSize = 13.sp, fontWeight = FontWeight.Medium)
                                }
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text("Delivery Fee ⓘ", fontSize = 13.sp, color = Color.Gray)
                                    Text("₹$deliveryFee", fontSize = 13.sp, fontWeight = FontWeight.Medium)
                                }
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text("Taxes & Charges ⓘ", fontSize = 13.sp, color = Color.Gray)
                                    Text("₹$taxes", fontSize = 13.sp, fontWeight = FontWeight.Medium)
                                }
                                HorizontalDivider(color = Color(0xFFF0EBE1))
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text("Total Amount", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                    Text("₹$totalAmount", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFFFF6B00))
                                }
                            }
                        }
                    }
                }
            }
        }

        if (cartItems.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(bottom = 60.dp, start = 20.dp, end = 20.dp)
            ) {
                Button(
                    onClick = { backStack.add(DineBiteScreens.Checkout) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(27.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6B00))
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Proceed to Checkout • ₹$totalAmount →", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp)
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
                        if (totalItems > 0) {
                            Badge(
                                containerColor = Color(0xFFFF6B00),
                                modifier = Modifier.align(Alignment.TopEnd)
                            ) {
                                Text("$totalItems", fontSize = 9.sp, color = Color.White)
                            }
                        }
                    }
                    Text("Cart", fontSize = 11.sp, color = Color(0xFFFF6B00), fontWeight = FontWeight.Bold)
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
