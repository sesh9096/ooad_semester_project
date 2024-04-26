package definote.ooad

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
import kotlinx.coroutines.flow.Flow

@Dao
interface EntryDao {
    @RawQuery(observedEntities = [Entry::class])
    fun getWithRawQuery(query: SupportSQLiteQuery): Flow<List<Entry>>

    @Query("SELECT * FROM entry")
    fun getAll(): Flow<List<Entry>>

    @Query("SELECT * FROM entry WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): Flow<List<Entry>>

    @Query("SELECT * FROM entry WHERE name LIKE '%' || :name || '%' ORDER BY LENGTH(name) LIMIT 100")
    fun searchByName(name: String): Flow<List<Entry>>

    // since the primary key is not name, there can be more than one entry with a given name
    @Query("SELECT * FROM entry WHERE name LIKE :name LIMIT 100")
    fun searchByNameExact(name: String): Flow<List<Entry>>

    @Query("SELECT * FROM entry WHERE uid == :id")
    suspend fun findByID(id:Int): Entry

    @Insert
    suspend fun insertAll(vararg entries: Entry)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entry: Entry)

    @Delete
    suspend fun delete(entry: Entry)

}