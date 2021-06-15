package ba.etf.rma21.projekat.data.repositories

import android.content.Context
import ba.etf.rma21.projekat.data.AppDatabase
import ba.etf.rma21.projekat.data.models.Account
import ba.etf.rma21.projekat.data.models.Datum
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class AccountRepository(var acHash:String) {

    companion object {
        var acHash: String = "b993f14e-5b8a-4cf6-b88d-05f200894c4e"
        private lateinit var context: Context
        fun setContext(_context: Context){
            context=_context
        }
        suspend fun postaviHash(acHash:String):Boolean{

                return withContext(Dispatchers.IO) {
                    try{
                        this@Companion.acHash = acHash
                        val dao = AppDatabase.getInstance(context).accountDao()
                        if(dao.getCount()==0){
                            dao.dodajAccount(Account(acHash, Datum.dajTrenutniDatum()))
                            DBRepository.forceUpdate()
                        }else if(dao.imaLiAccounta(acHash)==0){
                            dao.obrisiSve()
                            dao.dodajAccount(Account(acHash, Datum.dajTrenutniDatum()))
                            DBRepository.forceUpdate()
                        }
                        return@withContext true
                    } catch (t:Throwable){
                        return@withContext false
                    }
                }

        }

        fun getHash(): String {
            return acHash
        }


    }

}

