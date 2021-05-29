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
                val listaZapocetihKvizova = getPocetiKvizovi()?.filter { kvizTaken -> kvizTaken.kvizID==idKviza }
                if(listaZapocetihKvizova!=null && listaZapocetihKvizova.size>0) return@withContext listaZapocetihKvizova[0]
                val url = URL(ApiConfig.baseURL + "/student/${AccountRepository.getHash()}/kviz/$idKviza")
                (url.openConnection() as? HttpURLConnection)?.run {
                    this.requestMethod="POST"
                    val rezultat =this.inputStream.bufferedReader().use { it.readText() }
                    val jo = JSONObject(rezultat)
                    if(jo.length()<2) return@withContext null
                    val datumRada = SimpleDateFormat("yyyy-MM-dd").parse(jo.getString("datumRada")) as Date
                    return@withContext KvizTaken(jo.getInt("id"),jo.getDouble("osvojeniBodovi"),datumRada,idKviza)
                }
            }
        }


        suspend fun getPocetiKvizovi(): List<KvizTaken>? {
           return withContext(Dispatchers.IO){
                val url=URL(ApiConfig.baseURL+"/student/${AccountRepository.getHash()}/kviztaken")
                lateinit var listaZapocetihKvizova:MutableList<KvizTaken>
                (url.openConnection() as? HttpURLConnection)?.run {
                    val rezultat = this.inputStream.bufferedReader().use { it.readText() }
                    val jsonNiz=JSONArray(rezultat)
                    if(jsonNiz.length()==0) return@withContext null
                    listaZapocetihKvizova = mutableListOf()
                    for(i:Int in 0..jsonNiz.length()-1){
                        val jo = jsonNiz.getJSONObject(i)
                        val datum: Date = SimpleDateFormat("yyyy-MM-dd").parse(jo.getString("datumRada")) as Date
                        listaZapocetihKvizova.add(KvizTaken(jo.getInt("id"),jo.getDouble("osvojeniBodovi"),datum,jo.getInt("KvizId")))
                    }
                }
                return@withContext listaZapocetihKvizova
            }
        }
    }

}
