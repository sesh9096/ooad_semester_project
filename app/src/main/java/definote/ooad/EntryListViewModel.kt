package definote.ooad

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EntryListViewModel(context: Context) : ViewModel () {
    private val strategies = listOf<FilterStrategy>(
        SimpleFilterStrategy(context = context),
        ExactFilterStrategy(context = context)
    )
    private var selectedStrategy : FilterStrategy = strategies[0]
    private val _state = MutableStateFlow(EntryListState(strategies = strategies.map { it.getDisplayName() }))
    private val _searchText = MutableStateFlow("")
    @OptIn(ExperimentalCoroutinesApi::class)
    private val _entries = _searchText.flatMapLatest {
        selectedStrategy.getEntries(it)
    }
    val state = combine(_state, _entries, _searchText){
        state,entries,searchText -> state.copy(
            entries = entries,
            selectedStrategy = selectedStrategy.getDisplayName(),
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
    fun setStrategy (strategyName:String) {
        selectedStrategy = strategies.filter { it.getDisplayName() == strategyName }[0]
    }
}