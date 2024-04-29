package definote.ooad

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class EditEntryTest {
    @Test
    fun testEntryDao(){
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val simpleStrategy = SimpleFilterStrategy(appContext)
        val dao = AppDatabase.getInstance(appContext).entryDao()
        val entry1 = Entry(uid = 1232414247, name = "Abstract Factory", description = "Multiple factories (builders) to create different categories of objects. Make an abstract class to provide general abstract interface for several related factories. Provide concrete subclasses for the different builders.", part = "n.")
        val entry2 = Entry(uid = 1232414248, name = "Factory", description = "Implement a factory without making a separate class... merge the factory class into an existing class. Do this when factory class flexibility is not needed, when an appropriate class to merge with is obvious, and when factory methods are few. This pattern allows the factory method(s) to be inherited.", part = "n.")
        runBlocking {
            dao.insert( entry1, entry2)
            val simpleResults = simpleStrategy.getEntries("Factory").first()
            Assert.assertTrue("simple results should contain entry1 and entry2",simpleResults.contains(entry1) && simpleResults.contains(entry2))
            dao.delete(entry1)
            dao.delete(entry2)
        }
    }

    @Test
    fun testEditEntry(){

    }
}