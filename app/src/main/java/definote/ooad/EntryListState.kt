package definote.ooad

data class EntryListState (
    val entries: List<Entry> = emptyList(),
    val searchText: String = "",
)