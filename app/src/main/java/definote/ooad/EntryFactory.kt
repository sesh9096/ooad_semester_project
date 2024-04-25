package definote.ooad

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.random.Random

class EntryFactory(private val entryDao:EntryDao) {
    // Method to generate and add entries to the database
    fun generateAndAddEntry(name: String, description: String, part:String) {
        // Create an Entry object with the provided name and description
        val entry = Entry(Random.nextInt(), name, description, part)

        // Add the entry to the database using the EntryDao
        insertEntry(entry)
    }

    // Method to insert a single entry into the database
    private fun insertEntry(entry: Entry) {
        GlobalScope.launch(Dispatchers.IO) {
            entryDao.insert(entry)
        }
    }
}