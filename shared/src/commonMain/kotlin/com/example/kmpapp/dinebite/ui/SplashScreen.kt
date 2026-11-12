package com.example.kmpapp.dinebite.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.example.kmpapp.dinebite.navigation.DineBiteScreens
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import kmpapp.shared.generated.resources.Res
import kmpapp.shared.generated.resources._1__splash_1__1_
import kmpapp.shared.generated.resources.kaho_ji_logoimage1

@Composable
fun DineBiteSplashScreen(backStack: NavBackStack<NavKey>) {
    LaunchedEffect(Unit) {
        delay(1500L)
        backStack.removeLastOrNull()
        if (DineBiteRepository.isLoggedIn) {
            backStack.add(DineBiteScreens.Home)
        } else {
            backStack.add(DineBiteScreens.Welcome)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFFFF8C00), Color(0xFFFF4500))
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(120.dp))
            Image(
                painter = painterResource(Res.drawable.kaho_ji_logoimage1),
                contentDescription = "Logo",
                modifier = Modifier.size(120.dp)
            )
            Text(
                text = "DineBite",
                color = Color.White,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Restaurant",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.weight(1f))

            Image(
                painter = painterResource(Res.drawable._1__splash_1__1_),
                contentDescription = "Splash Food",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp),
                contentScale = ContentScale.Crop
            )
        }
    }
}
