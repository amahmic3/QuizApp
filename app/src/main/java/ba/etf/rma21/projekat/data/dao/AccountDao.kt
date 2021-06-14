package ba.etf.rma21.projekat.data.dao

import androidx.room.*
import ba.etf.rma21.projekat.data.models.Account

@Dao
interface AccountDao {
    @Query("SELECT COUNT(*) FROM Account")
    suspend fun getCount(): Int
    @Delete
    suspend fun obrisiAccount(account: Account)
    @Insert
    suspend fun dodajAccount(account: Account)
    @Query("SELECT * FROM Account")
    suspend fun getAllAccounts():List<Account>
    @Update
    suspend fun azurirajAccount(account: Account)
    @Query("DELETE FROM Account WHERE 1=1")
    suspend fun obrisiSve()
    @Query("SELECT COUNT(*) FROM Account WHERE acHash=:hash")
    suspend fun imaLiAccounta(hash:String):Int

}