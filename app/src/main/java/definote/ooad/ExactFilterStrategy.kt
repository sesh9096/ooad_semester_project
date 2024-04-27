package definote.ooad

import android.content.Context


class ExactFilterStrategy(context: Context) : FilterStrategy{
    private val name = "exact"
    private val entryDao: EntryDao = AppDatabase.getInstance(context).entryDao()
    override fun getEntries(searchText:String) = entryDao.searchByNameExact(searchText)
    override fun getDisplayName(): String {
        return name
    }
}