package com.example.kmpapp.dinebite.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.example.kmpapp.core.datastore.AppDatastore
import com.example.kmpapp.dinebite.navigation.DineBiteScreens
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import kmpapp.shared.generated.resources.Res
import kmpapp.shared.generated.resources.onboarding_1
import kmpapp.shared.generated.resources.onboarding_2
import kmpapp.shared.generated.resources.onboarding_3

data class OnboardingStepItem(
    val titleTop: String,
    val titleHighlight: String,
    val description: String,
    val drawableResource: DrawableResource
)

@Composable
fun DineBiteOnboardingScreen(backStack: NavBackStack<NavKey>) {
    val appDatastore: AppDatastore = koinInject()
    val items = listOf(
        OnboardingStepItem(
            titleTop = "Discover",
            titleHighlight = "Restaurant",
            description = "Find your favourite restaurant, menu and best dishes in one place.",
            drawableResource = Res.drawable.onboarding_1
        ),
        OnboardingStepItem(
            titleTop = "Explore",
            titleHighlight = "Delicious Food",
            description = "Browse a wide variety of dishes from our delicious menu.",
            drawableResource = Res.drawable.onboarding_2
        ),
        OnboardingStepItem(
            titleTop = "Easy",
            titleHighlight = "Online Ordering",
            description = "Order your favourite food anytime, anywhere with just a few taps.",
            drawableResource = Res.drawable.onboarding_3
        )
    )

    val pagerState = rememberPagerState(pageCount = { items.size })
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAF7F2))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = {
                    coroutineScope.launch {
                        appDatastore.saveOnboardingCompleted(true)
                        backStack.removeLastOrNull()
                        backStack.add(DineBiteScreens.Login)
                    }
                }) {
                    Text("Skip", color = Color.Gray, fontSize = 15.sp, fontWeight = FontWeight.Medium)
                }
            }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) { page ->
                val item = items[page]
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(300.dp)
                            .clip(RoundedCornerShape(24.dp))
                            .background(Color(0xFFFFECE5)),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(item.drawableResource),
                            contentDescription = item.titleTop,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }

                    Spacer(modifier = Modifier.height(40.dp))

                    Text(
                        text = item.titleTop,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        text = item.titleHighlight,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFF6B00)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = item.description,
                        fontSize = 14.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 20.dp),
                        lineHeight = 20.sp
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    repeat(items.size) { index ->
                        Box(
                            modifier = Modifier
                                .size(if (pagerState.currentPage == index) 20.dp else 8.dp, 8.dp)
                                .clip(CircleShape)
                                .background(
                                    if (pagerState.currentPage == index) Color(0xFFFF6B00)
                                    else Color.LightGray
                                )
                        )
                    }
                }

                if (pagerState.currentPage < items.size - 1) {
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        },
                        modifier = Modifier.size(56.dp),
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6B00)),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text("→", fontSize = 24.sp, color = Color.White, fontWeight = FontWeight.Bold)
                    }
                } else {
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                appDatastore.saveOnboardingCompleted(true)
                                backStack.removeLastOrNull()
                                backStack.add(DineBiteScreens.Login)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .padding(start = 24.dp),
                        shape = RoundedCornerShape(28.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6B00))
                    ) {
                        Text(
                            text = "Get Started",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}
