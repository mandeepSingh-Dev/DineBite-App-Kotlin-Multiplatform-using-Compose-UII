package com.example.kmpapp.feature.auth.user.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.example.kmpapp.core.common.utils.enums.UserType
import com.example.kmpapp.core.navigation.HostScreens
import com.example.kmpapp.core.navigation.UserScreens
import com.example.kmpapp.core.ui.components.BoxCommon
import com.example.kmpapp.core.ui.components.GradientButton
import com.example.kmpapp.core.ui.theme.HAFFER_TRIAL_MEDIUM
import com.example.kmpapp.core.ui.theme.Shape_15
import com.example.kmpapp.core.ui.theme.Shape_20
import com.example.kmpapp.core.ui.theme.brush2
import com.example.kmpapp.core.ui.theme.theme_color_2
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import kmpapp.shared.generated.resources.Res
import kmpapp.shared.generated.resources._18plusicon18_shieldicon
import kmpapp.shared.generated.resources.check
import kmpapp.shared.generated.resources.logo_1image1

@Composable
fun AgeConfirmationScreen(
    backStack: NavBackStack<NavKey>,
    selectedUserType: Int
) {
    val coroutineScope = rememberCoroutineScope()
    var is18PlusConfirmed by remember { mutableStateOf(false) }

    val confirm18PlusConsentScale by animateFloatAsState(if (is18PlusConfirmed) 1f else 0f)

    BoxCommon(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Image(
                    painter = painterResource(Res.drawable.logo_1image1),
                    contentDescription = null,
                    modifier = Modifier
                )
                Text("Talk . Connect Enjoy")
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(vertical = 30.dp)
                        .fillMaxWidth()
                        .border(width = 1.dp, brush = brush2(), shape = Shape_20)
                        .background(Color.White, Shape_20)
                        .padding(20.dp)
                ) {
                    Image(
                        painter = painterResource(Res.drawable._18plusicon18_shieldicon),
                        contentDescription = null,
                    )
                    Text(
                        "Age Confirmation",
                        modifier = Modifier.padding(10.dp),
                        fontSize = 25.sp,
                        fontFamily = HAFFER_TRIAL_MEDIUM
                    )
                    Text(
                        "You must be 18 years or older to use Kahoji",
                        textAlign = TextAlign.Center,
                        fontSize = 13.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(top = 10.dp),
                        fontFamily = HAFFER_TRIAL_MEDIUM,
                    )
                    Text(
                        "Please confirm that you are 18 years or older to proceed",
                        textAlign = TextAlign.Center,
                        fontSize = 13.sp,
                        color = Color.Black.copy(alpha = 0.7f),
                        modifier = Modifier.padding(top = 15.dp, bottom = 15.dp),
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(Shape_15)
                            .background(Color(0xFFF9F9F9), Shape_15)
                            .border(width = 1.dp, color = Color(0xFFE0E0E0), shape = Shape_15)
                            .clickable {
                                is18PlusConfirmed = !is18PlusConfirmed
                            }
                            .padding(15.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(if (is18PlusConfirmed) Color.White else Color.Transparent)
                                .border(
                                    width = 1.dp,
                                    color = if (is18PlusConfirmed) theme_color_2 else Color.Gray,
                                    shape = CircleShape
                                )
                                .padding(2.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(Res.drawable.check),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .scale(confirm18PlusConsentScale)
                            )
                        }
                        Text(
                            text = "I confirm that I am 18 years of age or older",
                            fontSize = 13.sp,
                            fontFamily = HAFFER_TRIAL_MEDIUM,
                            color = Color.Black,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    GradientButton(
                        onClick = {
                            coroutineScope.launch {
                                if (is18PlusConfirmed) {
                                    val loginScreen = if (selectedUserType == UserType.USER.value) {
                                        UserScreens.AuthScreens.LoginScreen(dob = null)
                                    } else {
                                        HostScreens.AuthHostScreens.LoginHostScreen
                                    }
                                    backStack.add(loginScreen)
                                }
                            }
                        },
                        text = "Continue",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 25.dp)
                    )
                }

                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}
