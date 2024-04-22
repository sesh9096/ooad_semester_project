package definote.ooad

import kotlinx.coroutines.flow.Flow

interface FilterStrategy {
    fun getEntries(searchText: String): Flow<List<Entry>>
}