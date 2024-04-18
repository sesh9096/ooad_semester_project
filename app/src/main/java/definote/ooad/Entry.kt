package definote.ooad

@Entity
data class Entry {
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "first_name") val name: String?,
    @ColumnInfo(name = "last_name") val description: String?
}
