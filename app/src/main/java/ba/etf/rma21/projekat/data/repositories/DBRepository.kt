package ba.etf.rma21.projekat.data.repositories

import android.content.Context
import ba.etf.rma21.projekat.data.AppDatabase
import ba.etf.rma21.projekat.data.models.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class DBRepository {
    companion object{
        private lateinit var context:Context
        fun setContext(_context: Context){
            context=_context
        }
        suspend fun updateNow():Boolean{
          return withContext(Dispatchers.IO) {
              var trebaPromjenu= false
                try {
                    val dbInstance = AppDatabase.getInstance(context)
                    val account = dbInstance.accountDao().getAllAccounts().first()
                    val link = "https://rma21-etf.herokuapp.com/account/${AccountRepository.getHash()}/lastUpdate?date=${account.lastUpdate}"
                    val url = URL(link)
                    (url.openConnection() as? HttpURLConnection)?.run {
                        val rezultat = this.inputStream.bufferedReader().use { it.readText() }
                        val jo = JSONObject(rezultat)
                        trebaPromjenu = jo.getBoolean("changed")
                        if(trebaPromjenu){
                           forceUpdate()
                        }
                    }
                }catch (t:Throwable){
                    return@withContext false
                }
              return@withContext trebaPromjenu
            }
        }
        suspend fun cistka(){
            val predmetDao = AppDatabase.getInstance(context).predmetDao()
           // val odgovorDao = AppDatabase.getInstance(context).odgovorDao()
            val grupaDao= AppDatabase.getInstance(context).grupaDao()
            val kvizDao = AppDatabase.getInstance(context).kvizDao()
            val pitanjaDao = AppDatabase.getInstance(context).pitanjeDao()
            val pitanjeKvizDao = AppDatabase.getInstance(context).pitanjeKvizDao()
           // odgovorDao.obrisiSveOdgovore()
            grupaDao.obrisiSveGrupe()
            predmetDao.obrisiSve()
            kvizDao.obrisiSveKvizove()
            pitanjaDao.obrisiSvaPitanja()
            pitanjeKvizDao.obrisiSve()
        }
        suspend fun forceUpdate():Boolean{
           return  withContext(Dispatchers.IO){
                try {
                    cistka()
                    AppDatabase.getInstance(context).accountDao().azurirajAccount(Account(AccountRepository.getHash(),Datum.dajTrenutniDatum()))
                    azurirajPredmete()
                    azurirajGrupe()
                    azurirajKvizove()

                }catch (t:Throwable){
                    return@withContext false
                }
                return@withContext true
            }
        }
        private suspend fun azurirajOdgovore(kvizovi:List<Kviz>){
            val odgovorDao = AppDatabase.getInstance(context).odgovorDao()
            for(kviz in kvizovi){
                val odgovoriNaKviz = OdgovorRepository.getOdgovoriKviz(kviz.id)!!
                for(odgovor in odgovoriNaKviz){
                   if(odgovorDao.daLiJeOdgovoreno(odgovor.idKviza,odgovor.idPitanja)==0) odgovorDao.dodajOdgovor(odgovor)
                }
            }

        }
        private suspend fun azurirajPitanja(kvizovi:List<Kviz>){
            val pitanjeDao = AppDatabase.getInstance(context).pitanjeDao()
            val pitanjeKvizDao = AppDatabase.getInstance(context).pitanjeKvizDao()
            for(kviz in kvizovi){
                val pitanja =PitanjeKvizRepository.getPitanja(kviz.id)!!
                pitanjeDao.dodajSvaPitanja(pitanja)
                for(pitanje in pitanja){
                    pitanjeKvizDao.dodajPitanjeKviz(PitanjeKviz(null,kviz.id,pitanje.id))
                }
            }
            azurirajOdgovore(kvizovi = kvizovi)
        }
        private suspend fun azurirajKvizove(){
            val kvizDao = AppDatabase.getInstance(context).kvizDao()
            val kvizovi =KvizRepository.getUpisani()!!
            kvizDao.dodajSveKvizove(kvizovi)
            azurirajPitanja(kvizovi)
        }
        private suspend fun azurirajPredmete(){
            val predmetDao = AppDatabase.getInstance(context).predmetDao()
            predmetDao.dodajPredmete(PredmetIGrupaRepository.dajUpisanePredmete()!!)
        }
        private suspend fun azurirajGrupe(){
            val grupaDao = AppDatabase.getInstance(context).grupaDao()
            grupaDao.dodajSveGrupe(PredmetIGrupaRepository.getUpisaneGrupe()!!)
        }
    }

}