package definote.ooad

interface FilterStrategy {
    fun getEntries(searchText: String): List<Entry>
}