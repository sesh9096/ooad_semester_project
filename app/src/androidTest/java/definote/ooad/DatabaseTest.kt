package definote.ooad

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class DatabaseTest {
    @Test
    fun databaseIsSingleton() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val db1 = AppDatabase.getInstance(appContext)
        val db2 = AppDatabase.getInstance(appContext)
        assertEquals("Database is not a singleton", db1, db2)
        assertNotNull("Database should not be null", db1)
    }
}