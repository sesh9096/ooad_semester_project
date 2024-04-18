package definote.ooad

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface EntryDao {
    @Query("SELECT * FROM entry")
    fun getAll(): List<Entry>

    @Query("SELECT * FROM entry WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<Entry>

    @Query("SELECT * FROM entry WHERE name LIKE :first")
    fun findByName(first: String): List<Entry>

    @Insert
    fun insertAll(vararg entries: Entry)

    @Insert
    fun insert(entry: Entry)

    @Delete
    fun delete(entry: Entry)
}