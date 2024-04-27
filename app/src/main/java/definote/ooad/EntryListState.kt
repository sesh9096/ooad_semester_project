package definote.ooad

data class EntryListState (
    val entries: List<Entry> = emptyList(),
    val searchText: String = "",
    val selectedStrategy: String = "",
    val strategies: List<String> = emptyList()
)