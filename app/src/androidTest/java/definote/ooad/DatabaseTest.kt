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
    @Test
    fun addAddGetRemove() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val dao = AppDatabase.getInstance(appContext).entryDao()
        val name = "asdfghjkl"
        val description = "blah blah blah"
        val entry = Entry(name = name, description = description)
        dao.insert(entry)
        val entryRet = dao.searchByNameExact(name)[0]
        assertNotNull("Entry should not be null", entryRet)
        // these may not have the same id because that is taken care of by db
        assertEquals("Entry names should be the same", entry.name, entryRet.name)
        assertEquals("Entry descriptions should be the same", entry.description, entryRet.description)
        dao.delete(entryRet)
        val entryRet2 = dao.searchByNameExact(name);
        assertNull("Entry should have been deleted", entryRet2)
    }
}