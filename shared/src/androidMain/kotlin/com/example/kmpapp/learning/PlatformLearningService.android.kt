package com.example.kmpapp.learning

import android.content.Context
import android.content.Intent
import android.provider.MediaStore
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.kmpapp.dinebite.util.AndroidContextProvider
import kotlinx.coroutines.delay
import okio.FileSystem
import okio.Path.Companion.toPath
import java.util.UUID
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.time.Duration.Companion.milliseconds

actual class PlatformLearningService actual constructor() {
    private val context: Context
        get() = AndroidContextProvider.appContext ?: throw IllegalStateException("Context not initialized")

    private var lastWorkId: UUID? = null

    actual fun scheduleBackgroundWork(): String {
        return try {
            val workRequest = OneTimeWorkRequestBuilder<DemoWorker>().build()
            lastWorkId = workRequest.id
            WorkManager.getInstance(context).enqueue(workRequest)
            "WorkManager task enqueued! ID: ${workRequest.id}"
        } catch (e: Exception) {
            "WorkManager failed: ${e.message}"
        }
    }

    actual fun observeWorkByIds(): String {
        val id = lastWorkId ?: return "No background work scheduled yet."
        return try {
            val workInfo = WorkManager.getInstance(context).getWorkInfoById(id).get()
            if (workInfo != null) {
                val state = workInfo.state.name
                val output = workInfo.outputData.getString("Staus") ?: "Pending / Running (4s delay)..."
                "Work ID: $id\nState: $state\nOutput Status: $output"
            } else {
                "Work info not found."
            }
        } catch (e: Exception) {
            "Failed to observe work: ${e.message}"
        }
    }

    actual fun saveSecureData(key: String, value: String): Boolean {
        return try {
            val masterKey = MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()
            val sharedPreferences = EncryptedSharedPreferences.create(
                context,
                "secure_prefs",
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
            sharedPreferences.edit().putString(key, value).apply()
            true
        } catch (e: Exception) {
            false
        }
    }

    actual fun getSecureData(key: String): String? {
        return try {
            val masterKey = MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()
            val sharedPreferences = EncryptedSharedPreferences.create(
                context,
                "secure_prefs",
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
            sharedPreferences.getString(key, null)
        } catch (e: Exception) {
            null
        }
    }

    actual fun getFileSystemInfo(): String {
        val filesDir = context.filesDir.absolutePath
        val cacheDir = context.cacheDir.absolutePath
        return "FilesDir: $filesDir\nCacheDir: $cacheDir"
    }

    actual fun writeTextFile(fileName: String, content: String): Boolean {
        return try {
            val fileDir = context.filesDir
            val path = "${fileDir.absolutePath}/$fileName".toPath()
            FileSystem.SYSTEM.write(path) {
                writeUtf8(content)
            }
            true
        } catch (e: Exception) {
            false
        }
    }

    actual fun readTextFile(fileName: String): String? {
        return try {
            val fileDir = context.filesDir
            val path = "${fileDir.absolutePath}/$fileName".toPath()
            if (FileSystem.SYSTEM.exists(path)) {
                FileSystem.SYSTEM.read(path) {
                    readUtf8()
                }
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    actual suspend fun authenticateBiometric(): Boolean = suspendCoroutine { continuation ->
        val activity = context as? FragmentActivity
        if (activity == null) {
            continuation.resume(false)
            return@suspendCoroutine
        }
        val executor = ContextCompat.getMainExecutor(activity)
        val biometricPrompt = BiometricPrompt(activity, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    continuation.resume(true)
                }
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    continuation.resume(false)
                }
                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                }
            })

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric Login for KMP Learning")
            .setSubtitle("Confirm your identity")
            .setNegativeButtonText("Cancel")
            .build()

        biometricPrompt.authenticate(promptInfo)
    }

    actual fun openGallery(onResult: (String?) -> Unit) {
        try {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "image/*"
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            onResult("content://media/external/images/media/picked_image.jpg")
        } catch (e: Exception) {
            onResult(null)
        }
    }

    actual fun openCamera(onResult: (String?) -> Unit) {
        try {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            onResult("content://media/external/images/media/captured_camera.jpg")
        } catch (e: Exception) {
            onResult(null)
        }
    }
}

class DemoWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        delay(4000.milliseconds)
        val outputData = workDataOf("Staus" to "Success")
        return Result.success(outputData)
    }
}
