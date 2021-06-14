package ba.etf.rma21.projekat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ba.etf.rma21.projekat.data.models.Odgovor

@Dao
interface OdgovorDao {
    @Query("UPDATE Odgovor SET odgovoreno=:odgovor WHERE idPitanja=:idPitanja AND idKviza=:idKviza")
    suspend fun azurirajOdgovor(idPitanja:Int,idKviza:Int,odgovor:Int)
    @Insert
    suspend fun dodajOdgovor(odgovor:Odgovor)
    @Query("SELECT * FROM Odgovor WHERE idKviza=:id")
    suspend fun dajOdgovoreZaKviz(id:Int):List<Odgovor>
    @Query("DELETE FROM Odgovor WHERE 1=1")
    suspend fun obrisiSveOdgovore()
    @Query("SELECT COUNT(*) FROM ODGOVOR WHERE idKviza=:kvizID AND odgovoreno IS NOT NULL")
    suspend fun dajBrOdgovorenih(kvizID:Int):Int
    @Query("SELECT COUNT(*) FROM odgovor WHERE idKviza=:kvizID AND idPitanja=:idPitanja")
    suspend fun daLiJeOdgovoreno(kvizID: Int,idPitanja: Int):Int
}