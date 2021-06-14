package ba.etf.rma21.projekat.data.repositories

import android.content.Context
import ba.etf.rma21.projekat.data.AppDatabase
import ba.etf.rma21.projekat.data.models.KvizTaken
import ba.etf.rma21.projekat.data.models.Odgovor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL

class OdgovorRepository {
    companion object {
        private lateinit var context: Context
        fun setContext(_context: Context){
            context=_context
        }
        suspend fun getOdgovoriKviz(idKviza: Int): List<Odgovor>? {
            return withContext(Dispatchers.IO){
                try {
                    val listaOdgovora = mutableListOf<Odgovor>()
                    val pocetiKviz = TakeKvizRepository.getPocetiKvizovi()
                        ?.first { kvizTaken -> kvizTaken.KvizId == idKviza }
                    if (pocetiKviz == null) return@withContext listaOdgovora
                    val url =
                        URL(ApiConfig.baseURL + "/student/${AccountRepository.getHash()}/kviztaken/${pocetiKviz.id}/odgovori")
                    (url.openConnection() as? HttpURLConnection)?.run {
                        val rezultat = this.inputStream.bufferedReader().use { it.readText() }
                        val jsonNiz = JSONArray(rezultat)
                        for (i: Int in 0..jsonNiz.length() - 1) {
                            val jo = jsonNiz.getJSONObject(i)
                            listaOdgovora.add(
                                Odgovor(null,
                                    idPitanja = jo.getInt("PitanjeId"),
                                    idKviza = idKviza,
                                    odgovoreno = jo.getInt("odgovoreno")
                                )
                            )
                        }
                    }
                    return@withContext listaOdgovora
                }catch (t:Throwable){
                    return@withContext listOf<Odgovor>()
                }
            }
        }
        suspend fun postaviOdgovorKviz(idKvizTaken: Int, idPitanje: Int, odgovor: Int):Int? {
            return withContext(Dispatchers.IO){
              try {
                  val db = AppDatabase.getInstance(context)
                  val pitanjeDao = db.pitanjeDao()
                  val odgovorDao = db.odgovorDao()
                  var povratna: Int = -1
                  val kvizTaken: KvizTaken? = TakeKvizRepository.getPocetiKvizovi()?.first { kvizTaken -> kvizTaken.id == idKvizTaken }
                  if(odgovorDao.daLiJeOdgovoreno(kvizTaken!!.KvizId,idPitanje)==0)
                  odgovorDao.dodajOdgovor(Odgovor(null,idPitanje, kvizTaken.KvizId, odgovor))
                  povratna =
                      (pitanjeDao.dajBrTacnihNaKvizu(kvizTaken.KvizId) * 100) / pitanjeDao.dajBrPitanjaNaKvizu(
                          kvizTaken.KvizId
                      )
                  return@withContext povratna
              }catch (t:Throwable){
                  return@withContext -1
              }
            }
        }
        suspend fun predajOdgovore(idKviz:Int){
            return withContext(Dispatchers.IO){
                try{
                    val kvizTaken: KvizTaken? = TakeKvizRepository.getPocetiKvizovi()
                        ?.first { kvizTaken -> kvizTaken.KvizId == idKviz }
                    val db = AppDatabase.getInstance(context)
                    val odgovorDao = db.odgovorDao()
                    val odgovori = odgovorDao.dajOdgovoreZaKviz(idKviz)
                    for(odgovor in odgovori){
                        posaljiOdgovor(kvizTaken!!.id,odgovor.idPitanja,odgovor.odgovoreno)
                    }
                }catch(t:Throwable){

                }
            }
        }
        suspend fun posaljiOdgovor(idKvizTaken: Int, idPitanje: Int, odgovor: Int): Int? {
            return withContext(Dispatchers.IO){
                try {
                    var povratna: Int = -1
                    val url =
                        URL(ApiConfig.baseURL + "/student/${AccountRepository.getHash()}/kviztaken/$idKvizTaken/odgovor")
                    val kvizTaken: KvizTaken? = TakeKvizRepository.getPocetiKvizovi()
                        ?.first { kvizTaken -> kvizTaken.id == idKvizTaken }
                    val pitanja = PitanjeKvizRepository.getPitanja(kvizTaken!!.KvizId)
                    val postavljenoPitanje = pitanja!!.first { pitanje -> pitanje.id == idPitanje }
                    val osvojeniBodovi: Float =
                        if (odgovor == postavljenoPitanje.tacan) kvizTaken.osvojeniBodovi + 100F / pitanja.size else kvizTaken.osvojeniBodovi
                    povratna = osvojeniBodovi.toInt()
                    val requestBody =
                        "{\"odgovor\":$odgovor,\"pitanje\":$idPitanje,\"bodovi\":${osvojeniBodovi}}"
                    (url.openConnection() as? HttpURLConnection)?.run {
                        this.doOutput = true
                        this.doInput = true
                        this.requestMethod = "POST"
                        this.setRequestProperty("accept", "application/json")
                        this.setRequestProperty("Content-Type", "application/json")
                        val os = this.outputStream
                        os.write(requestBody.toByteArray())
                        val rezultat: String =
                            this.inputStream.bufferedReader().use { it.readText() }
                    }
                    return@withContext povratna
                }catch (t:Throwable){
                    return@withContext -1
                }
            }
        }
    }


}