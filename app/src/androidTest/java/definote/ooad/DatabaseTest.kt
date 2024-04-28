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
    fun canAddGetRemove() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val dao = AppDatabase.getInstance(appContext).entryDao()
        // The stupid names are because the database is a singleton which may contain other entries.
        val name1 = "asdfghjkl"
        val name2 = "asdfghjklqwertyuiop"
        val description = "blah blah blah"
        val part = "part"
        val uid1 =  999999998
        val uid2 =  999999999
        val entry1 = Entry(uid = uid1, name = name1, description = description, part = part)
        val entry2 = Entry(uid = uid2, name = name2, description = description, part = part)
        runBlocking {
            dao.insert(entry1)
            dao.insert(entry2)
            val entryRetListExact = dao.searchByNameExact(name1).first()
            val entryRetList = dao.searchByName(name1).first()
            val entry1Ret = entryRetListExact[0]
            assertEquals("There should be 1 exact match",1, entryRetListExact.size)
            assertEquals("There should be 2 similar matches",2, entryRetList.size)
            // these may not have the same id because that is taken care of by db
            assertEquals("Entries should be the same", entry1, entry1Ret)
            assertEquals("Entry with the same ID should have been returned", entry1, dao.findByID(uid1))
            dao.delete(entry1Ret)
            dao.delete(entry2)
            val entryRetAfter = dao.searchByNameExact(name1).first()
            assertEquals("Entry should have been deleted", emptyList<Entry>(), entryRetAfter)
        }
    }
}