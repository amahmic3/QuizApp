package ba.etf.rma21.projekat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ba.etf.rma21.projekat.data.models.Predmet

@Dao
interface PredmetDao {
    @Query("DELETE FROM Predmet WHERE 1=1")
    suspend fun obrisiSve()
    @Insert
    suspend fun dodajPredmete(predmeti:List<Predmet>)
    @Query("SELECT * FROM PREDMET")
    suspend fun dajSvePredmete():List<Predmet>



}