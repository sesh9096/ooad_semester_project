package definote.ooad

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EntryListViewModel(context: Context) : ViewModel () {
//    private var sortingStrategy = MutableStateFlow(SimpleFilterStrategy(context) as FilterStrategy)
    private val sortingStrategy : FilterStrategy = SimpleFilterStrategy(context = context)
    private val _state = MutableStateFlow(EntryListState())
    private val _searchText = MutableStateFlow("")
    @OptIn(ExperimentalCoroutinesApi::class)
    private val _entries = _searchText.flatMapLatest { sortingStrategy.getEntries(it) }
    val state = combine(_state, _entries, _searchText){
        state,entries,searchText -> state.copy(
            entries = entries,
            searchText = searchText
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), EntryListState())
    fun search(searchText:String){
        viewModelScope.launch {
//            _state.update {
//                Log.d("searching for", searchText)
//                it.copy(
//                searchText = searchText,
//            ) }
            _searchText.update {
                searchText
            }
        }
    }
}