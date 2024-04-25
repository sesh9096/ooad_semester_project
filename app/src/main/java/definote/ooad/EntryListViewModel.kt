package definote.ooad

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EntryListViewModel(context: Context) : ViewModel () {
//    private var sortingStrategy = MutableStateFlow(SimpleFilterStrategy(context) as FilterStrategy)
    private val sortingStrategy : FilterStrategy = SimpleFilterStrategy(context = context)
    private val entryFlow = sortingStrategy.getEntries("")
    private val _state = MutableStateFlow(EntryListState())
    val state = combine(_state, entryFlow){
        state,entries -> state.copy(
            entries = entries
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), EntryListState())
    fun search(searchText:String){
        viewModelScope.launch {
            _state.update { it.copy(
                searchText = searchText
            ) }
        }
    }
}