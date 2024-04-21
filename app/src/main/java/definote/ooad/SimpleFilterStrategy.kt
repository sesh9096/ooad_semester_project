package definote.ooad

class SimpleFilterStrategy : FilterStrategy{
    val entries:List<Entry> = listOf()
    override fun getEntries(searchText:String):List<Entry>{
        return entries.filter { entry: Entry -> entry.name.contains(searchText) }
    }
}