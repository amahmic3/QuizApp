package ba.etf.rma21.projekat.data.repositories

import ba.etf.rma21.projekat.data.models.Datum
import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.models.Kviz
import kotlinx.coroutines.*
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashSet

class KvizRepository {

    companion object {

        suspend fun getDone(): List<Kviz> {
            return withContext(Dispatchers.IO){
                val mojiKvizovi = getUpisani()
                return@withContext mojiKvizovi?.filter { kviz -> kviz.osvojeniBodovi!=null }!!
            }

        }

        suspend fun getFuture(): List<Kviz> {
           return withContext(Dispatchers.IO) {
                val mojiKvizovi = getUpisani()
                return@withContext mojiKvizovi?.filter { kviz -> kviz.osvojeniBodovi==null && kviz.datumKraj!!.after(Calendar.getInstance().time) }!!
            }
        }

        suspend fun getNotTaken(): List<Kviz> {
           return withContext(Dispatchers.IO) {
                val mojiKvizovi = getUpisani()
                return@withContext mojiKvizovi?.filter { kviz -> kviz.osvojeniBodovi==null && kviz.datumKraj!!.before(Calendar.getInstance().time)}!!
            }
        }

        suspend fun getAll(): List<Kviz>? {
            return withContext(Dispatchers.IO){
                val listaKvizova = mutableListOf<Kviz>()
                val url = URL(ApiConfig.baseURL+"/kviz")
                (url.openConnection() as? HttpURLConnection)?.run {
                    val rezultat = this.inputStream.bufferedReader().use { it.readText() }
                    val jsonNiz = JSONArray(rezultat)
                    for(i:Int in 0..jsonNiz.length()-1){
                        val jo = jsonNiz.getJSONObject(i)
                        listaKvizova.add(sklepajObjekat(jo))
                    }
                }
                return@withContext listaKvizova
            }
        }
        suspend fun getById(id: Int): Kviz? {
            return withContext(Dispatchers.IO){
                val url = URL(ApiConfig.baseURL+"/kviz/$id")
                (url.openConnection() as? HttpURLConnection)?.run {
                    val rezultat = this.inputStream.bufferedReader().use { it.readText() }
                    val jo = JSONObject(rezultat)
                    if(jo.length()<2) return@withContext null
                    return@withContext sklepajObjekat(jo)
                }
            }
        }
        private suspend fun sklepajObjekat(jo:JSONObject):Kviz{
            val idKviza = jo.getInt("id")
            val nazivKviza = jo.getString("naziv")
            val datumPocetka = jo.getString("datumPocetak")
            val datumKraja = jo.getString("datumKraj")
            val trajanje = jo.getInt("trajanje")
            val urlGrupe = URL(ApiConfig.baseURL+"/kviz/$idKviza/grupa")
            val listaPredmeta = HashSet<String>()
            (urlGrupe.openConnection() as? HttpURLConnection)?.run {
                val rez = this.inputStream.bufferedReader().use { it.readText() }
                val jsonNizGrupa = JSONArray(rez)
                for(j:Int in 0..jsonNizGrupa.length()-1){
                    val grupa = jsonNizGrupa.getJSONObject(j)
                    listaPredmeta.add(PredmetIGrupaRepository.dajNazivPredmetaZaID(grupa.getInt("PredmetId")))
                }
            }
            val datumP:Date? = if(datumPocetka=="null") null else SimpleDateFormat("yyyy-MM-dd").parse(datumPocetka)
            val datumK:Date? = if(datumKraja=="null") Datum.dajDatum(2050,1,2) else SimpleDateFormat("yyyy-MM-dd").parse(datumPocetka)
            return Kviz(idKviza,nazivKviza,listaPredmeta.toList(),datumP,datumK,null,trajanje,null)
        }
        suspend fun getUpisani(): List<Kviz>? {
           return withContext(Dispatchers.IO){
               val mojeGrupe = PredmetIGrupaRepository.getUpisaneGrupe() as List<Grupa>
               val povratniKvizovi = HashSet<Kviz>()
               for(grupa in mojeGrupe){
                   val url = URL(ApiConfig.baseURL+"/grupa/${grupa.id}/kvizovi")
                   (url.openConnection() as? HttpURLConnection)?.run {
                       val rezultat = this.inputStream.bufferedReader().use { it.readText() }
                       val jsonNizKvizova = JSONArray(rezultat)
                       for(i:Int in 0..jsonNizKvizova.length()-1){
                           povratniKvizovi.add(sklepajObjekat(jsonNizKvizova.getJSONObject(i)))
                       }
                   }
               }
               return@withContext povratniKvizovi.toList()
           }
        }

    }
}