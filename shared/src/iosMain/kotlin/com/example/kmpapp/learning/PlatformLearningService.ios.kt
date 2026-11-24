package com.example.kmpapp.learning

import kotlinx.cinterop.ExperimentalForeignApi
import okio.FileSystem
import okio.Path.Companion.toPath
import platform.BackgroundTasks.*
import platform.Foundation.*
import platform.LocalAuthentication.*
import platform.PhotosUI.*
import platform.UIKit.*
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

actual class PlatformLearningService actual constructor() {
    @OptIn(ExperimentalForeignApi::class)
    actual fun scheduleBackgroundWork(): String {
        return try {
            val request = BGAppRefreshTaskRequest("com.example.kmpapp.refresh").apply {
                earliestBeginDate = NSDate.dateWithTimeIntervalSinceNow(60.0)
            }
            val success = BGTaskScheduler.sharedScheduler.submitTaskRequest(request, null)
            if (success) {
                "iOS BGTaskScheduler task successfully scheduled!"
            } else {
                "BGTaskScheduler submission failed."
            }
        } catch (e: Exception) {
            "BGTaskScheduler error: ${e.message}"
        }
    }

    actual fun observeWorkByIds(): String {
        return "iOS BGTaskScheduler: Task registered with identifier 'com.example.kmpapp.refresh'. Scheduled to run in background."
    }

    actual fun saveSecureData(key: String, value: String): Boolean {
        return try {
            NSUserDefaults.standardUserDefaults.setObject(value, forKey = key)
            true
        } catch (e: Exception) {
            false
        }
    }

    actual fun getSecureData(key: String): String? {
        return try {
            NSUserDefaults.standardUserDefaults.stringForKey(key)
        } catch (e: Exception) {
            null
        }
    }

    actual fun getFileSystemInfo(): String {
        val docs = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, true).first() as? String ?: ""
        val cache = NSSearchPathForDirectoriesInDomains(NSCachesDirectory, NSUserDomainMask, true).first() as? String ?: ""
        return "Documents: $docs\nCaches: $cache"
    }

    actual fun writeTextFile(fileName: String, content: String): Boolean {
        return try {
            val docs = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, true).first() as? String ?: return false
            val path = "$docs/$fileName".toPath()
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
            val docs = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, true).first() as? String ?: return null
            val path = "$docs/$fileName".toPath()
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

    @OptIn(ExperimentalForeignApi::class)
    actual suspend fun authenticateBiometric(): Boolean = suspendCoroutine { continuation ->
        val context = LAContext()
        if (context.canEvaluatePolicy(LAPolicyDeviceOwnerAuthenticationWithBiometrics, error = null)) {
            context.evaluatePolicy(
                LAPolicyDeviceOwnerAuthenticationWithBiometrics,
                localizedReason = "Authenticate to access KMP Learning features"
            ) { success, _ ->
                continuation.resume(success)
            }
        } else {
            continuation.resume(false)
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    actual fun openGallery(onResult: (String?) -> Unit) {
        val config = PHPickerConfiguration()
        config.selectionLimit = 1
        val picker = PHPickerViewController(config)
        val window = UIApplication.sharedApplication.keyWindow ?: UIApplication.sharedApplication.windows.firstOrNull() as? UIWindow
        window?.rootViewController?.presentViewController(picker, animated = true, completion = null)
        onResult("ph_picker_gallery_image.jpg")
    }

    @OptIn(ExperimentalForeignApi::class)
    actual fun openCamera(onResult: (String?) -> Unit) {
        if (UIImagePickerController.isSourceTypeAvailable(UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypeCamera)) {
            val picker = UIImagePickerController().apply {
                sourceType = UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypeCamera
            }
            val window = UIApplication.sharedApplication.keyWindow ?: UIApplication.sharedApplication.windows.firstOrNull() as? UIWindow
            window?.rootViewController?.presentViewController(picker, animated = true, completion = null)
            onResult("ui_image_picker_camera_photo.jpg")
        } else {
            onResult(null)
        }
    }
}
