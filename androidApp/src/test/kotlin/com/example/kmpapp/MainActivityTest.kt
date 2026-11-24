package com.example.kmpapp

import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class MainActivityTest {

    private val activity = MainActivity()

    @Test
    fun testOperationThrowsExceptionWhenCountLessThan3() = runTest {
        assertFailsWith<RuntimeException> {
            activity.operation(0)
        }
        assertFailsWith<RuntimeException> {
            activity.operation(1)
        }
        assertFailsWith<RuntimeException> {
            activity.operation(2)
        }
    }

    @Test
    fun testOperationSucceedsWhenCountAtLeast3() = runTest {
        assertEquals("Result: 3", activity.operation(3))
        assertEquals("Result: 4", activity.operation(4))
        assertEquals("Result: 10", activity.operation(10))
    }

    @Test
    fun testManualRetryWithMechanismSucceeds() = runTest {
        activity.manualRetryWithMechanism<String>()
    }
}
