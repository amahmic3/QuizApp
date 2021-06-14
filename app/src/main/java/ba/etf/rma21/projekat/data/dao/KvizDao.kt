package ba.etf.rma21.projekat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import ba.etf.rma21.projekat.data.models.Kviz

@Dao
interface KvizDao {
    @Query("DELETE FROM Kviz WHERE 1=1")
    suspend fun obrisiSveKvizove()
    @Insert
    suspend fun dodajSveKvizove(kvizovi:List<Kviz>)
    @Query("SELECt * FROm Kviz")
    suspend fun dajSveKvizove():List<Kviz>
    @Update
    suspend fun azurirajKviz(kviz:Kviz)
}