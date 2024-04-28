package definote.ooad

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EntryListViewModel(context: Context) : ViewModel () {
    private val entryDao = AppDatabase.getInstance(context).entryDao()
    private val strategies = listOf(
        SimpleFilterStrategy(context = context),
        ExactFilterStrategy(context = context),
        FuzzyFilterStrategy(context = context),
    )
    private var selectedStrategy : FilterStrategy = strategies[0]
    private val _state = MutableStateFlow(EntryListState(strategies = strategies.map { it.getDisplayName() }))
    private val _searchText = MutableStateFlow("")
    @OptIn(ExperimentalCoroutinesApi::class)
    private val _entries = _searchText.flatMapLatest {
        if(it == ""){
            // This could also be implemented in strategies
            flowOf(emptyList())
        }else{
            selectedStrategy.getEntries(it)
        }
    }
    val state = combine(_state, _entries, _searchText){
        state,entries,searchText -> state.copy(
            entries = entries,
            selectedStrategy = selectedStrategy.getDisplayName(),
            searchText = searchText
        )
    }.stateIn(viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        EntryListState(
            strategies = strategies.map { it.getDisplayName() },
            selectedStrategy = strategies[0].getDisplayName() ))
    fun search(searchText:String){
        Log.d("searching for", searchText)
        viewModelScope.launch {
            _searchText.update {
                searchText
            }
        }
    }
    fun setStrategy (strategyName:String) {
        selectedStrategy = strategies.filter { it.getDisplayName() == strategyName }[0]
        viewModelScope.launch {
            // Bit of a hacky solution because we need to update the search text to a different value to get this updated
            val searchText = state.value.searchText
            _searchText.update { "" }
//            Log.d("EntryListViewModel:", "delaying so state gets updated")
            delay(10)
            _searchText.update {
                Log.d("EntryListViewModel:", "Searching for $searchText using strategy $strategyName")
                searchText
            }
        }
    }
    fun deleteEntry(entry: Entry){
        viewModelScope.launch {
            entryDao.delete(entry)
        }
    }
}
