package ba.etf.rma21.projekat.data.repositories

import ba.etf.rma21.projekat.data.models.KvizTaken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*


class TakeKvizRepository {
    companion object {

        suspend fun zapocniKviz(idKviza: Int): KvizTaken? {
            return withContext(Dispatchers.IO){
                try {
                    val listaZapocetihKvizova =
                        getPocetiKvizovi()?.firstOrNull { kvizTaken -> kvizTaken.KvizId == idKviza }
                    if (listaZapocetihKvizova != null) return@withContext listaZapocetihKvizova
                    val url =
                        URL(ApiConfig.baseURL + "/student/${AccountRepository.getHash()}/kviz/$idKviza")
                    (url.openConnection() as? HttpURLConnection)?.run {
                        this.requestMethod = "POST"
                        val rezultat = this.inputStream.bufferedReader().use { it.readText() }
                        val jo = JSONObject(rezultat)
                        if (jo.length() < 2) return@withContext null
                        val datumRada =
                            SimpleDateFormat("yyyy-MM-dd").parse(jo.getString("datumRada")) as Date
                        val osvojeniBodovi: Float = jo.get("osvojeniBodovi").toString().toFloat()
                        return@withContext KvizTaken(
                            jo.getInt("id"),
                            jo.getString("student"),
                            osvojeniBodovi,
                            datumRada,
                            idKviza
                        )
                    }
                }catch (t:Throwable){
                    return@withContext null
                }
            }
        }


        suspend fun getPocetiKvizovi(): List<KvizTaken>? {
           return withContext(Dispatchers.IO){
               try {
                   val url =
                       URL(ApiConfig.baseURL + "/student/${AccountRepository.getHash()}/kviztaken")
                   lateinit var listaZapocetihKvizova: MutableList<KvizTaken>
                   (url.openConnection() as? HttpURLConnection)?.run {
                       val rezultat = this.inputStream.bufferedReader().use { it.readText() }
                       val jsonNiz = JSONArray(rezultat)
                       if (jsonNiz.length() == 0) return@withContext null
                       listaZapocetihKvizova = mutableListOf()
                       for (i: Int in 0..jsonNiz.length() - 1) {
                           val jo = jsonNiz.getJSONObject(i)
                           val datum: Date =
                               SimpleDateFormat("yyyy-MM-dd").parse(jo.getString("datumRada")) as Date
                           val osvojeniBodovi: Float = jo.get("osvojeniBodovi").toString().toFloat()
                           listaZapocetihKvizova.add(
                               KvizTaken(
                                   jo.getInt("id"),
                                   jo.getString("student"),
                                   osvojeniBodovi,
                                   datum,
                                   jo.getInt("KvizId")
                               )
                           )
                       }
                   }
                   return@withContext listaZapocetihKvizova
               }catch (t:Throwable){
                   return@withContext null
               }
            }
        }
    }

}
