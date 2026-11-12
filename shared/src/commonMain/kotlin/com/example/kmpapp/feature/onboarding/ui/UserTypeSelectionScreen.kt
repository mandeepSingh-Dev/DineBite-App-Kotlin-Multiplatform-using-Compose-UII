package com.example.kmpapp.feature.onboarding.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.example.kmpapp.core.navigation.UserScreens

@Composable
fun UserTypeSelectionScreen(backStack: NavBackStack<NavKey>) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("User Type Selection Screen (User vs Host)")
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                backStack.add(UserScreens.AuthScreens.AgeConfirmationScreen(selectedUserType = 1))
            }) {
                Text("Select User (Go to Age Confirmation)")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = {
                backStack.add(com.example.kmpapp.core.navigation.HostScreens.AuthHostScreens.LoginHostScreen)
            }) {
                Text("Select Host (Go to Host Login)")
            }
        }
    }
}
