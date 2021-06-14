package ba.etf.rma21.projekat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ba.etf.rma21.projekat.data.models.Pitanje

@Dao
interface PitanjeDao {
    @Query("DELETE FROM Pitanje WHERE 1=1")
    suspend fun obrisiSvaPitanja()
    @Insert
    suspend fun dodajSvaPitanja(grupe:List<Pitanje>)
    @Query("SELECT * FROM Pitanje")
    suspend fun dajSvaPitanja():List<Pitanje>
    @Query("SELECT * FROM pitanje WHERE id = :idPitanja")
    suspend fun dajPitanjeSaId(idPitanja:Int):Pitanje
    @Query("SELECT * FROM Pitanje p WHERE p.id IN (SELECT t1.idPitanja FROM PitanjeKviz t1 WHERE t1.idKviza =:kvizId)")
    suspend fun dajPitanjaZaKviz(kvizId:Int):List<Pitanje>
    @Query("SELECT COUNT(*) FROM PitanjeKviz WHERE idKviza=:kvizId")
    suspend fun dajBrPitanjaNaKvizu(kvizId:Int):Int
    @Query("SELECT COUNT(*) FROM Pitanje p, Odgovor o WHERE p.id = o.idPitanja AND p.tacan = o.odgovoreno AND o.idKviza=:kvizId")
    suspend fun dajBrTacnihNaKvizu(kvizId: Int):Int
}