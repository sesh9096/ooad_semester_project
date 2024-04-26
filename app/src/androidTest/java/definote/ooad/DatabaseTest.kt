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
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class DatabaseTest {
    @Test
    fun databaseIsSingleton() {
        // Context of the app under test.
        val appContext = getApplicationContext() as Context
        val db1 = AppDatabase.getInstance(appContext)
        val db2 = AppDatabase.getInstance(appContext)
        assertEquals("Database is not a singleton", db1, db2)
        assertNotNull("Database should not be null", db1)
    }
    @Test
    fun addAddGetRemove() {
        // Context of the app under test.
        val appContext = getApplicationContext() as Context
        val dao = AppDatabase.getInstance(appContext).entryDao()
        val name = "asdfghjkl"
        val description = "blah blah blah"
        val part = "part"
        val uid =  999999999
        val entry = Entry(uid = uid, name = name, description = description, part = part)
        runBlocking {
            dao.insert(entry)
            val entryRet = dao.searchByNameExact(name).first()[0]
            assertNotNull("Entry should not be null", entryRet)
            // these may not have the same id because that is taken care of by db
            assertEquals("Entry names should be the same", entry.name, entryRet.name)
            assertEquals("Entry descriptions should be the same", entry.description, entryRet.description)
            dao.delete(entryRet)
            val entryRet2 = dao.searchByNameExact(name).first()
            assertEquals("Entry should have been deleted", emptyList<Entry>(), entryRet2)
        }
    }
}