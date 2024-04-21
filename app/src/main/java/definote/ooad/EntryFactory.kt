package definote.ooad

class EntryFactory(private val entryDao: EntryDao) {

    // Method to generate and add entries to the database
    fun generateAndAddEntry(name: String, description: String) {
        // Create an Entry object with the provided name and description
        val entry = Entry(0, name, description)

        // Add the entry to the database using the EntryDao
        insertEntry(entry)
    }

    // Method to insert a single entry into the database
    private fun insertEntry(entry: Entry) {
        entryDao.insert(entry)
    }
}