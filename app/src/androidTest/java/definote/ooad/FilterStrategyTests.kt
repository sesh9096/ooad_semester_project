package definote.ooad

import android.content.Context
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class FilterStrategyTests {
    @Test
    fun filtersNamed() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val simpleStrategy = SimpleFilterStrategy(appContext)
        val exactStrategy = ExactFilterStrategy(appContext)
        val fuzzyStrategy = FuzzyFilterStrategy(appContext)
        assertEquals("simple", simpleStrategy.getDisplayName())
        assertEquals("exact", exactStrategy.getDisplayName())
        assertEquals("fuzzy", fuzzyStrategy.getDisplayName())
    }
    @Test
    fun filtersWork(){
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val simpleStrategy = SimpleFilterStrategy(appContext)
        val exactStrategy = ExactFilterStrategy(appContext)
        val fuzzyStrategy = FuzzyFilterStrategy(appContext)
        val dao = AppDatabase.getInstance(appContext).entryDao()
        // random uid's, don't make something with these id's
        val entry1 = Entry(uid = 1232414243, name = "Abstract Factory", description = "Multiple factories (builders) to create different categories of objects. Make an abstract class to provide general abstract interface for several related factories. Provide concrete subclasses for the different builders.", part = "n.")
        val entry2 = Entry(uid = 1232414244, name = "Factory", description = "Implement a factory without making a separate class... merge the factory class into an existing class. Do this when factory class flexibility is not needed, when an appropriate class to merge with is obvious, and when factory methods are few. This pattern allows the factory method(s) to be inherited.", part = "n.")
        runBlocking {
            dao.insert( entry1, entry2)
            val simpleResults = simpleStrategy.getEntries("Factory").first()
            val exactResults = exactStrategy.getEntries("Factory").first()
            val fuzzyResults = fuzzyStrategy.getEntries("AbstrctFactry").first()
            assertTrue("simple results should contain entry1 and entry2",simpleResults.contains(entry1) && simpleResults.contains(entry2))
            assertTrue("exact results should not contain entry1 and should contain entry2", !exactResults.contains(entry1) && exactResults.contains(entry2))
            assertEquals("fuzzy results should contain entry 1 and not entry 2", listOf(entry1), fuzzyResults)
            dao.delete(entry1)
            dao.delete(entry2)
        }
    }
}