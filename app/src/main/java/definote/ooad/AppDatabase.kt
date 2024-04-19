package definote.ooad

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Singleton class
@Database(entities = [Entry::class], version = 1)
abstract class AppDatabase: RoomDatabase(){
    companion object{
        @Volatile
        private var instance : AppDatabase? = null

        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "EntryData"
                ).build().also { instance = it }
            }
    }
    abstract fun entryDao(): EntryDao

}