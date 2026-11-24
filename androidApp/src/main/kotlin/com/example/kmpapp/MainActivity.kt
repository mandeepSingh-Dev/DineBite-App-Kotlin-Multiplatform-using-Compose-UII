package com.example.kmpapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.lifecycle.lifecycleScope
import com.example.kmpapp.dinebite.util.AndroidContextProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.cancellation.CancellationException
import kotlin.random.Random
import kotlin.time.Duration.Companion.milliseconds

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        AndroidContextProvider.appContext = applicationContext

        setContent {
            MaterialTheme {
                App()
            }
        }

        lifecycleScope.launch {

         Log.d("Result_Wating",   manualRetryWithMechanism<String>() ?: "")
        }
    }

    suspend fun <T> manualRetryWithMechanism(): T? = coroutineScope {
        val maxRetries = 5
        var count = 0
        var result: T? = null

        while (count < maxRetries) {
            try {
                @Suppress("UNCHECKED_CAST")
                result = operation(count) as? T

                Log.d("retry_mech", result.toString())
                break
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                count++
                if(count < maxRetries){
                    Log.d("retry_mech", "Retrying")
                }
                result = null
            }
        }

        return@coroutineScope result
    }

    suspend fun operation(count: Int): String? {
        delay(1000.milliseconds)
        return if (count < 3) {
            throw RuntimeException("Count is lower than 6 so throwing error.")
        } else {
            "Result: $count"
        }
    }



}
