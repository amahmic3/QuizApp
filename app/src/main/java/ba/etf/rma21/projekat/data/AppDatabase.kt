package ba.etf.rma21.projekat.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ba.etf.rma21.projekat.data.dao.*
import ba.etf.rma21.projekat.data.models.*

@Database(entities = arrayOf(Kviz::class, Pitanje::class,Odgovor::class, Grupa::class,Predmet::class,Account::class,PitanjeKviz::class), version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun accountDao():AccountDao
    abstract fun predmetDao():PredmetDao
    abstract fun grupaDao():GrupaDao
    abstract fun kvizDao():KvizDao
    abstract fun pitanjeDao():PitanjeDao
    abstract fun odgovorDao():OdgovorDao
    abstract fun pitanjeKvizDao():PitanjeKvizDao
    companion object {
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = buildRoomDB(context)
                }
            }
            return INSTANCE!!
        }
        fun setInstance(appdb:AppDatabase):Unit{
            INSTANCE=appdb
        }
        private fun buildRoomDB(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "RMA21DB"
            ).build()

    }
}