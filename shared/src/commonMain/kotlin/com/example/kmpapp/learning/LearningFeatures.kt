package com.example.kmpapp.learning

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import kotlinx.coroutines.launch

expect class PlatformLearningService() {
    fun scheduleBackgroundWork(): String
    fun observeWorkByIds(): String
    fun saveSecureData(key: String, value: String): Boolean
    fun getSecureData(key: String): String?
    fun getFileSystemInfo(): String
    fun writeTextFile(fileName: String, content: String): Boolean
    fun readTextFile(fileName: String): String?
    suspend fun authenticateBiometric(): Boolean
    fun openGallery(onResult: (String?) -> Unit)
    fun openCamera(onResult: (String?) -> Unit)
}

@Composable
fun LearningHubScreen(backStack: NavBackStack<NavKey>) {
    val platformService = remember { PlatformLearningService() }
    val coroutineScope = rememberCoroutineScope()
    var statusMessage by remember { mutableStateOf("Select a learning module below to test platform capabilities.") }
    var secureKey by remember { mutableStateOf("user_token") }
    var secureValue by remember { mutableStateOf("secret_abc_123") }
    var retrievedSecureValue by remember { mutableStateOf("") }
    var fileName by remember { mutableStateOf("demo_file.txt") }
    var fileContent by remember { mutableStateOf("Hello Okio & FileSystem!") }
    var fileReadResult by remember { mutableStateOf("") }

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopAppBar(
                title = { Text("KMP Advanced Learning Hub") },
                navigationIcon = {
                    Button(onClick = { backStack.removeLastOrNull() }) {
                        Text("Back")
                    }
                }
            )
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Text("🚀 Platform Features Learning & Flow", style = MaterialTheme.typography.headlineSmall)
                    Spacer(modifier = Modifier.height(8.dp))
                    Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) {
                        Text(
                            text = "Status: $statusMessage",
                            modifier = Modifier.padding(16.dp),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                // 1. Background Tasks
                item {
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("1. Background Tasks", style = MaterialTheme.typography.titleLarge)
                            Text("• Android: WorkManager (CoroutineWorker + 4s delay + OutputData)\n• iOS: BGTaskScheduler (BGAppRefreshTask)", style = MaterialTheme.typography.bodySmall)
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Button(onClick = {
                                    statusMessage = platformService.scheduleBackgroundWork()
                                }) {
                                    Text("Schedule Work")
                                }
                                Button(onClick = {
                                    statusMessage = platformService.observeWorkByIds()
                                }) {
                                    Text("Observe Status")
                                }
                            }
                        }
                    }
                }

                // 2. Storage & Security
                item {
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("2. Secure Storage & Keychain", style = MaterialTheme.typography.titleLarge)
                            Text("• Android: EncryptedSharedPreferences\n• iOS: Keychain (SecItemAdd / SecItemCopyMatching)", style = MaterialTheme.typography.bodySmall)
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(value = secureKey, onValueChange = { secureKey = it }, label = { Text("Key") }, modifier = Modifier.fillMaxWidth())
                            Spacer(modifier = Modifier.height(4.dp))
                            OutlinedTextField(value = secureValue, onValueChange = { secureValue = it }, label = { Text("Value") }, modifier = Modifier.fillMaxWidth())
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Button(onClick = {
                                    val success = platformService.saveSecureData(secureKey, secureValue)
                                    statusMessage = if (success) "Saved securely!" else "Failed to save secure data."
                                }) {
                                    Text("Save Secure")
                                }
                                Button(onClick = {
                                    retrievedSecureValue = platformService.getSecureData(secureKey) ?: "Not Found"
                                    statusMessage = "Retrieved secure value for $secureKey"
                                }) {
                                    Text("Read Secure")
                                }
                            }
                            if (retrievedSecureValue.isNotEmpty()) {
                                Text("Result: $retrievedSecureValue", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(top = 4.dp))
                            }
                        }
                    }
                }

                // 3. FileSystem & Okio
                item {
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("3. FileSystem & Okio", style = MaterialTheme.typography.titleLarge)
                            Text("• Android: filesDir, cacheDir, MediaStore\n• iOS: NSDocumentDirectory, NSCacheDirectory + Okio", style = MaterialTheme.typography.bodySmall)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(platformService.getFileSystemInfo(), style = MaterialTheme.typography.bodySmall)
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(value = fileName, onValueChange = { fileName = it }, label = { Text("File Name") }, modifier = Modifier.fillMaxWidth())
                            Spacer(modifier = Modifier.height(4.dp))
                            OutlinedTextField(value = fileContent, onValueChange = { fileContent = it }, label = { Text("File Content") }, modifier = Modifier.fillMaxWidth())
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Button(onClick = {
                                    val ok = platformService.writeTextFile(fileName, fileContent)
                                    statusMessage = if (ok) "Wrote file via Okio successfully!" else "File write failed."
                                }) {
                                    Text("Write File")
                                }
                                Button(onClick = {
                                    fileReadResult = platformService.readTextFile(fileName) ?: "File empty or missing"
                                    statusMessage = "Read file $fileName"
                                }) {
                                    Text("Read File")
                                }
                            }
                            if (fileReadResult.isNotEmpty()) {
                                Text("File Content: $fileReadResult", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(top = 4.dp))
                            }
                        }
                    }
                }

                // 4. Hardware & Biometrics
                item {
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("4. Hardware Sensors & Biometric", style = MaterialTheme.typography.titleLarge)
                            Text("• Android: BiometricPrompt / BiometricManager\n• iOS: LAContext (LocalAuthentication)", style = MaterialTheme.typography.bodySmall)
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(onClick = {
                                coroutineScope.launch {
                                    statusMessage = "Authenticating..."
                                    val authenticated = platformService.authenticateBiometric()
                                    statusMessage = if (authenticated) "Biometric Authentication Successful! ✅" else "Authentication Failed or Cancelled. ❌"
                                }
                            }) {
                                Text("Authenticate with Biometrics")
                            }
                        }
                    }
                }

                // 5. Camera & Gallery Picker
                item {
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("5. Camera & Gallery Picker", style = MaterialTheme.typography.titleLarge)
                            Text("• Android: Intent.ACTION_OPEN_DOCUMENT (Gallery) & MediaStore (Camera)\n• iOS: PHPickerViewController & UIImagePickerController", style = MaterialTheme.typography.bodySmall)
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Button(onClick = {
                                    platformService.openGallery { uri ->
                                        statusMessage = if (uri != null) "Gallery Image Selected: $uri" else "Gallery cancelled"
                                    }
                                }) {
                                    Text("Open Gallery")
                                }
                                Button(onClick = {
                                    platformService.openCamera { uri ->
                                        statusMessage = if (uri != null) "Camera Photo Captured: $uri" else "Camera cancelled"
                                    }
                                }) {
                                    Text("Open Camera")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
