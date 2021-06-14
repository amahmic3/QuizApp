package ba.etf.rma21.projekat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ba.etf.rma21.projekat.data.models.Grupa

@Dao
interface GrupaDao {
    @Query("DELETE FROM Grupa WHERE 1=1")
    suspend fun obrisiSveGrupe()
    @Insert
    suspend fun dodajSveGrupe(grupe:List<Grupa>)
    @Query("SELECT * FROM Grupa")
    suspend fun dajSveGrupe():List<Grupa>
}