package ba.etf.rma21.projekat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ba.etf.rma21.projekat.data.models.PitanjeKviz

@Dao
interface PitanjeKvizDao {
    @Insert
    suspend fun dodajPitanjeKviz(pitanjeKviz:PitanjeKviz)
    @Query("DELETE FROM PitanjeKviz WHERE 1=1")
    suspend fun obrisiSve()

}