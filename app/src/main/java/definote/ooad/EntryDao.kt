package definote.ooad

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery

@Dao
interface EntryDao {
    @RawQuery
    fun getWithRawQuery(query: SupportSQLiteQuery): List<Entry>

    @Query("SELECT * FROM entry")
    fun getAll(): List<Entry>

    @Query("SELECT * FROM entry WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<Entry>

    @Query("SELECT * FROM entry WHERE name LIKE '%' || :name || '%'")
    fun searchByName(name: String): List<Entry>

    // since the primary key is not name, there can be more than one entry with a given name
    @Query("SELECT * FROM entry WHERE name LIKE :name")
    fun searchByNameExact(name: String): List<Entry>

    @Query("SELECT * FROM entry WHERE uid == :id")
    fun findByID(id:Int): List<Entry>

    @Insert
    fun insertAll(vararg entries: Entry)

    @Insert
    fun insert(entry: Entry)

    @Delete
    fun delete(entry: Entry)
}