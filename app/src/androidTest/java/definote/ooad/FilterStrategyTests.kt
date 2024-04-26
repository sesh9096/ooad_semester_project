package definote.ooad

import android.content.Context
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
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
    fun filtersWork(){
        val appContext = getApplicationContext() as Context
        val simpleStrategy = SimpleFilterStrategy(appContext)
        val exactStrategy = ExactFilterStrategy(appContext)
        val dao = AppDatabase.getInstance(appContext).entryDao()
        // random uid's, don't make something with these id's
        val entry1 = Entry(uid = 1232414243, name = "Abstract Factory", description = "Multiple factories (builders) to create different categories of objects. Make an abstract class to provide general abstract interface for several related factories. Provide concrete subclasses for the different builders.", part = "n.")
        val entry2 =Entry(uid = 1232414244, name = "Factory", description = "Implement a factory without making a separate class... merge the factory class into an existing class. Do this when factory class flexibility is not needed, when an appropriate class to merge with is obvious, and when factory methods are few. This pattern allows the factory method(s) to be inherited.", part = "n.")
        runBlocking {
            dao.insertAll( entry1,entry2 )
            val simpleResults = simpleStrategy.getEntries("Factory").first()
            val exactResults = exactStrategy.getEntries("Factory").first()
            assertTrue("Results should contain entry1 and entry2",simpleResults.contains(entry1) && simpleResults.contains(entry2))
            assertTrue("Results should not contain entry1 and should contain entry2", !exactResults.contains(entry1) && exactResults.contains(entry2))
            dao.delete(entry1)
            dao.delete(entry2)
        }
    }
}