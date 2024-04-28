package definote.ooad

import android.content.Context
import kotlinx.coroutines.flow.Flow


class FuzzyFilterStrategy(context: Context) : FilterStrategy{
    private val name = "fuzzy"
    private val entryDao: EntryDao = AppDatabase.getInstance(context).entryDao()
    override fun getEntries(searchText:String): Flow<List<Entry>> {
        val builder = StringBuilder()
        builder.append('%')
        searchText.forEach {
            builder.append(it)
            builder.append('%')
        }
        return entryDao.searchByNameExact(builder.toString() )
    }
    override fun getDisplayName(): String {
        return name
    }
}