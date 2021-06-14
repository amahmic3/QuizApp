package ba.etf.rma21.projekat.data.repositories

import ba.etf.rma21.projekat.data.models.Pitanje
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL

class PitanjeKvizRepository {
    companion object {

        suspend fun getPitanja(idKviza: Int): List<Pitanje>? {
           return withContext(Dispatchers.IO){
               try {
                   val url = URL(ApiConfig.baseURL + "/kviz/$idKviza/pitanja")
                   val listaPitanja = mutableListOf<Pitanje>()
                   (url.openConnection() as? HttpURLConnection)?.run {
                       val rezultat = this.inputStream.bufferedReader().use { it.readText() }
                       val jsonNiz = JSONArray(rezultat)
                       for (i: Int in 0..jsonNiz.length() - 1) {
                           val jo = jsonNiz.getJSONObject(i)
                           val idPitanja = jo.getInt("id")
                           val naziv = jo.getString("naziv")
                           val tekstPitanja = jo.getString("tekstPitanja")
                           val tacan = jo.getInt("tacan")
                           var opcije: String= ""
                           val jsonOpcije = jo.getJSONArray("opcije")
                           for (j: Int in 0..jsonOpcije.length() - 1) {
                               opcije+=(jsonOpcije.getString(j))+','
                           }

                           listaPitanja.add(Pitanje(idPitanja, naziv, tekstPitanja, opcije.removeSuffix(","), tacan))
                       }
                   }
                   return@withContext listaPitanja
               }catch (t:Throwable){
                   return@withContext null
               }
            }
        }
    }

}