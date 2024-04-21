package definote.ooad

import android.content.Context
class SimpleFilterStrategy(context: Context) : FilterStrategy{
    private val entryDao: EntryDao = AppDatabase.getInstance(context).entryDao()
    override fun getEntries(searchText:String) = entryDao.searchByName(searchText)
}