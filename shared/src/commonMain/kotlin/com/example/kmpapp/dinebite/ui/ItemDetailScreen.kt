package com.example.kmpapp.dinebite.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import coil3.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DineBiteItemDetailScreen(
    foodItem: FoodItem,
    onDismiss: () -> Unit,
    onAddToCart: (Int) -> Unit
) {
    var quantity by remember { mutableStateOf(1) }
    var selectedSize by remember { mutableStateOf("Medium") }
    var extraBacon by remember { mutableStateOf(false) }

    val sizePrice = when (selectedSize) {
        "Regular" -> 249
        "Medium" -> 336
        "Large" -> 410
        else -> foodItem.price
    }
    val addonPrice = if (extraBacon) 60 else 0
    val totalPrice = (sizePrice + addonPrice) * quantity

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = Color(0xFFFAF7F2),
        dragHandle = {
            Box(
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .width(40.dp)
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(Color.LightGray)
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0xFFFFECE5)),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = foodItem.imageUrl,
                    contentDescription = foodItem.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(foodItem.name, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                foodItem.description,
                fontSize = 12.sp,
                color = Color.Gray,
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text("Choose Size", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black)
            Spacer(modifier = Modifier.height(10.dp))

            listOf("Regular" to 249, "Medium" to 336, "Large" to 410).forEach { (size, price) ->
                val isSelected = selectedSize == size
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable { selectedSize = size },
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
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            RadioButton(
                                selected = isSelected,
                                onClick = { selectedSize = size },
                                colors = RadioButtonDefaults.colors(selectedColor = Color(0xFFFF6B00))
                            )
                            Text(size, fontWeight = FontWeight.Medium, fontSize = 14.sp)
                        }
                        Text("₹$price", fontWeight = FontWeight.Bold, color = if (isSelected) Color(0xFFFF6B00) else Color.Black, fontSize = 14.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Custom Add-Ons", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black)
            Spacer(modifier = Modifier.height(10.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { extraBacon = !extraBacon },
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Checkbox(
                            checked = extraBacon,
                            onCheckedChange = { extraBacon = it },
                            colors = CheckboxDefaults.colors(checkedColor = Color(0xFFFF6B00))
                        )
                        Text("Extra Smoked Bacon", fontWeight = FontWeight.Medium, fontSize = 14.sp)
                    }
                    Text("+₹60", fontWeight = FontWeight.Bold, color = Color(0xFFFF6B00), fontSize = 14.sp)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier
                        .background(Color(0xFFFFECE5), RoundedCornerShape(28.dp))
                        .padding(horizontal = 12.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "-",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFF6B00),
                        modifier = Modifier.clickable { if (quantity > 1) quantity-- }
                    )
                    Text("$quantity", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Text(
                        "+",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFF6B00),
                        modifier = Modifier.clickable { quantity++ }
                    )
                }

                Button(
                    onClick = {
                        val existing = DineBiteRepository.cartItems.find { it.name == foodItem.name && it.variant == selectedSize }
                        if (existing != null) {
                            existing.quantity += quantity
                        } else {
                            DineBiteRepository.cartItems.add(
                                CartItem(
                                    id = foodItem.id,
                                    name = foodItem.name,
                                    variant = selectedSize,
                                    price = sizePrice + if (extraBacon) 60 else 0,
                                    quantity = quantity,
                                    imageUrl = foodItem.imageUrl
                                )
                            )
                        }
                        onAddToCart(totalPrice)
                        onDismiss()
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp),
                    shape = RoundedCornerShape(28.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6B00))
                ) {
                    Text(
                        text = "Add to Cart • ₹$totalPrice",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}
