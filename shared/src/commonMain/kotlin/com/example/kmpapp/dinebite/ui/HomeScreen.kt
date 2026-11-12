package com.example.kmpapp.dinebite.ui

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import coil3.compose.AsyncImage
import com.example.kmpapp.dinebite.navigation.DineBiteScreens
import com.example.kmpapp.dinebite.util.LocationService

fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

@Composable
fun DineBiteHomeScreen(backStack: NavBackStack<NavKey>) {
    var selectedTab by remember { mutableStateOf("Delivery") }
    var selectedCategory by remember { mutableStateOf("All") }
    var selectedFoodItem by remember { mutableStateOf<FoodItem?>(null) }
    var currentAddress by remember { mutableStateOf("Home (B-120, Green Park, New De...)") }

    val context = LocalContext.current
    val activity = remember(context) { context.findActivity() }
    val locationService = remember { LocationService() }



    LaunchedEffect(Unit) {
        activity?.let { act ->
            val permissions = mutableListOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                permissions.add(Manifest.permission.POST_NOTIFICATIONS)
            }
            ActivityCompat.requestPermissions(act, permissions.toTypedArray(), 100)
        }

        locationService.requestLocationAndFetch { loc ->
            if (loc.isNotEmpty()) {
                currentAddress = loc
            }
        }
    }

    val cartItems = DineBiteRepository.cartItems
    val totalCartItems = cartItems.sumOf { it.quantity }
    val cartTotalPrice = cartItems.sumOf { it.price * it.quantity }

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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { backStack.add(DineBiteScreens.MyAddresses) }
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("Deliver to", fontSize = 11.sp, color = Color.Gray)
                    Text(currentAddress, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                }
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(36.dp).clip(CircleShape).background(Color.White), contentAlignment = Alignment.Center) {
                        Text("🔔", fontSize = 14.sp)
                    }
                    Box(modifier = Modifier.size(36.dp).clip(CircleShape).background(Color.Red), contentAlignment = Alignment.Center) {
                        Text("P", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }
                }
            }

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Text("Hi, Priya 👋", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                    Text("Craving delicious gourmet food today?", fontSize = 12.sp, color = Color.Gray)

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        placeholder = { Text("Search pasta, burger, truffle, shakes...", fontSize = 12.sp) },
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        shape = RoundedCornerShape(24.dp),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFF0EBE1), RoundedCornerShape(24.dp))
                            .padding(4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        listOf("Delivery", "Dine-In", "Pickup").forEach { tab ->
                            val isSelected = selectedTab == tab
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(if (isSelected) Color.White else Color.Transparent)
                                    .clickable { selectedTab = tab }
                                    .padding(vertical = 8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(tab, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal, color = if (isSelected) Color(0xFFFF6B00) else Color.Gray, fontSize = 13.sp)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    val banners = listOf(
                        Triple("Chef's Weekend Carnival 🍕", "Flat 40% OFF • Code: GOUR...", Color(0xFFFF6B00)),
                        Triple("Artisan Shakes & Desserts 🍨", "Flat 25% OFF on Sweets", Color(0xFF7B1FA2)),
                        Triple("Gourmet Burger Fest 🍔", "Buy 1 Get 1 Free on Burgers", Color(0xFF00897B))
                    )
                    val pagerState = rememberPagerState(pageCount = { banners.size })

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        HorizontalPager(
                            state = pagerState,
                            modifier = Modifier.fillMaxWidth()
                        ) { page ->
                            val banner = banners[page]
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(containerColor = banner.third)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            banner.first,
                                            color = Color.White,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 17.sp
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            banner.second,
                                            color = Color.White.copy(alpha = 0.85f),
                                            fontSize = 12.sp
                                        )
                                        Spacer(modifier = Modifier.height(10.dp))
                                        Button(
                                            onClick = {},
                                            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                                            shape = RoundedCornerShape(16.dp),
                                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                                            modifier = Modifier.height(30.dp)
                                        ) {
                                            Text("Explore Menu →", color = Color.Black, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                        }
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            repeat(banners.size) { index ->
                                Box(
                                    modifier = Modifier
                                        .size(if (pagerState.currentPage == index) 16.dp else 6.dp, 6.dp)
                                        .clip(CircleShape)
                                        .background(
                                            if (pagerState.currentPage == index) Color(0xFFFF6B00)
                                            else Color.LightGray
                                        )
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text("Explore Categories", fontSize = 17.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(12.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        items(categoriesList) { cat ->
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.clickable { selectedCategory = cat.name }
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(64.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFFFFECE5)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    AsyncImage(
                                        model = cat.imageUrl,
                                        contentDescription = cat.name,
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    cat.name,
                                    fontSize = 12.sp,
                                    fontWeight = if (selectedCategory == cat.name) FontWeight.Bold else FontWeight.Normal,
                                    color = if (selectedCategory == cat.name) Color(0xFFFF6B00) else Color.DarkGray
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Popular Right Now ✨", fontSize = 17.sp, fontWeight = FontWeight.Bold)
                        Text("View All →", color = Color(0xFFFF6B00), fontSize = 13.sp, fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        items(sampleFoodItems.take(3)) { item ->
                            Card(
                                modifier = Modifier
                                    .width(180.dp)
                                    .clickable { selectedFoodItem = item },
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(110.dp)
                                            .clip(RoundedCornerShape(12.dp))
                                            .background(Color(0xFFFFECE5))
                                    ) {
                                        AsyncImage(
                                            model = item.imageUrl,
                                            contentDescription = item.name,
                                            modifier = Modifier.fillMaxSize(),
                                            contentScale = ContentScale.Crop
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(item.name, fontWeight = FontWeight.Bold, fontSize = 13.sp, maxLines = 1)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text("⭐ ${item.rating} (${item.reviews})", fontSize = 11.sp, color = Color(0xFFFFA000))
                                    }
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text("₹${item.price}", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                        Button(
                                            onClick = { selectedFoodItem = item },
                                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6B00)),
                                            shape = CircleShape,
                                            contentPadding = PaddingValues(0.dp),
                                            modifier = Modifier.size(28.dp)
                                        ) {
                                            Text("+", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                                        }
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { backStack.add(DineBiteScreens.TableReservation) },
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text("Planning to Dine In? 🍷", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                                Text("Skip the wait queue. Reserve your prime table.", fontSize = 12.sp, color = Color.Gray)
                            }
                            Button(
                                onClick = { backStack.add(DineBiteScreens.TableReservation) },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6B00)),
                                shape = RoundedCornerShape(16.dp),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Text("Book Table →", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text("⚡ FLASH DEAL • ENDS IN 2H 15M", color = Color(0xFFFFB300), fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text("Free Truffle Fries with Any Large Pizza", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                            }
                            Box(
                                modifier = Modifier
                                    .size(60.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(Color.Gray)
                            ) {
                                AsyncImage(
                                    model = sharedImageUrls[0],
                                    contentDescription = "Truffle Fries",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Text("Recommended for You 🧑‍🍳", fontSize = 17.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(12.dp))
                }

                items(sampleFoodItems) { item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedFoodItem = item },
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
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
                                    .size(80.dp)
                                    .clip(RoundedCornerShape(12.dp))
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
                                Text(item.name, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                Spacer(modifier = Modifier.height(2.dp))
                                Text("⭐ ${item.rating}  •  ⏱️ ${item.time}  •  🔥 ${item.kcal}", fontSize = 10.sp, color = Color.Gray)
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(item.description, fontSize = 11.sp, color = Color.Gray, maxLines = 1)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text("₹${item.price}", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFFFF6B00))
                            }
                            Button(
                                onClick = { selectedFoodItem = item },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFECE5)),
                                shape = RoundedCornerShape(16.dp),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
                            ) {
                                Text("Customize +", color = Color(0xFFFF6B00), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }

        if (totalCartItems > 0) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(bottom = 60.dp, start = 20.dp, end = 20.dp)
            ) {
                Button(
                    onClick = { backStack.add(DineBiteScreens.Cart) },
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
                        Text("$totalCartItems items • ₹$cartTotalPrice", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                        Text("View Cart →", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp)
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
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("🏠", fontSize = 18.sp)
                    Text("Home", fontSize = 11.sp, color = Color(0xFFFF6B00), fontWeight = FontWeight.Bold)
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
                        if (totalCartItems > 0) {
                            Badge(
                                containerColor = Color(0xFFFF6B00),
                                modifier = Modifier.align(Alignment.TopEnd)
                            ) {
                                Text("$totalCartItems", fontSize = 9.sp, color = Color.White)
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

        selectedFoodItem?.let { item ->
            DineBiteItemDetailScreen(
                foodItem = item,
                onDismiss = { selectedFoodItem = null },
                onAddToCart = { price -> }
            )
        }
    }
}
