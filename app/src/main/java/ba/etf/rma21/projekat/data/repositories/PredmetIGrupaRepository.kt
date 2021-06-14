package ba.etf.rma21.projekat.data.repositories


import android.content.Context
import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.models.Predmet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL


class PredmetIGrupaRepository {
    companion object{
        private lateinit var context: Context
        fun setContext(_context: Context){
            context=_context
        }
        suspend fun getPredmeti():List<Predmet>? {
            return withContext(Dispatchers.IO){
                val listaPredmeta = mutableListOf<Predmet>()
                val url = URL(ApiConfig.baseURL+"/predmet")
                (url.openConnection() as? HttpURLConnection)?.run {
                    val rezultat = this.inputStream.bufferedReader().use { it.readText() }
                    val jsonArray = JSONArray(rezultat)
                    for(i:Int in 0..jsonArray.length()-1){
                        val jsonObject = jsonArray.getJSONObject(i)
                        val id = jsonObject.getInt("id")
                        val naziv = jsonObject.getString("naziv")
                        val godina = jsonObject.getInt("godina")
                        listaPredmeta.add(Predmet(id, naziv, godina))
                    }
                }

                return@withContext listaPredmeta
            }
        }
        suspend fun getPredmetByID(idPredmet:Int):Predmet?{
            return withContext(Dispatchers.IO){
                val listaPredmeta = mutableListOf<Predmet>()
                val url = URL(ApiConfig.baseURL+"/predmet/$idPredmet")
                var predmet:Predmet? = null
                (url.openConnection() as? HttpURLConnection)?.run {
                    val rezultat = this.inputStream.bufferedReader().use { it.readText() }
                    val jsonObject = JSONObject(rezultat)
                    val id = jsonObject.getInt("id")
                    val naziv = jsonObject.getString("naziv")
                    val godina = jsonObject.getInt("godina")
                    predmet=Predmet(id, naziv, godina)
                }
                return@withContext predmet
            }
        }
       suspend fun dajNazivPredmetaZaID(id:Int):String{
           return withContext(Dispatchers.IO) {
               var povratna = ""
               val url = URL(ApiConfig.baseURL + "/predmet/$id")
               (url.openConnection() as? HttpURLConnection)?.run {
                   val rez = this.inputStream.bufferedReader().use { it.readText() }
                   val jo = JSONObject(rez)
                   if(jo.length()>1) povratna = jo.getString("naziv")
               }
                return@withContext povratna
           }
        }
        suspend fun getGrupe():List<Grupa>?{
            return withContext(Dispatchers.IO){
                val listaGrupa = mutableListOf<Grupa>()
                val url = URL(ApiConfig.baseURL+"/grupa")
                (url.openConnection() as? HttpURLConnection)?.run {
                    val rezultat = this.inputStream.bufferedReader().use { it.readText() }
                    val jsonArray = JSONArray(rezultat)
                    for(i:Int in 0..jsonArray.length()-1){
                        val jsonObject = jsonArray.getJSONObject(i)
                        val id = jsonObject.getInt("id")
                        val naziv = jsonObject.getString("naziv")
                        val idPredmeta = jsonObject.getInt("PredmetId")
                        listaGrupa.add(Grupa(id, naziv, idPredmeta, dajNazivPredmetaZaID(idPredmeta)))
                    }
                }

                return@withContext listaGrupa
            }
        }
        suspend fun dajUpisanePredmete():List<Predmet>?{
            return  withContext(Dispatchers.IO){
                val upisaneGrupe = getUpisaneGrupe()
                val hashset = HashSet<Predmet>()
                for(grupa in upisaneGrupe!!){
                    hashset.add(getPredmetByID(grupa.idPredmeta)!!)
                }
                return@withContext hashset.toList()
            }
        }
        suspend fun getGrupeZaPredmet(idPredmeta:Int):List<Grupa>?{

            return getGrupe()?.filter { g->g.idPredmeta==idPredmeta }
        }

        suspend fun upisiUGrupu(idGrupa:Int):Boolean?{
            return withContext(Dispatchers.IO){
                var povratna:Boolean = false
                val url = URL(ApiConfig.baseURL+"/grupa/$idGrupa/student/${AccountRepository.getHash()}")
                (url.openConnection() as? HttpURLConnection)?.run {
                    this.doOutput = true
                    this.requestMethod = "POST"
                    val jo=JSONObject(this.inputStream.bufferedReader().use { it.readText() })
                    if(jo.getString("message").startsWith("Student")){
                        povratna=true
                    }
                }
                return@withContext povratna
            }
        }

        suspend fun getUpisaneGrupe():List<Grupa>?{
            return withContext(Dispatchers.IO){
                val listaGrupa = mutableListOf<Grupa>()
                val url = URL(ApiConfig.baseURL+"/student/${AccountRepository.getHash()}/grupa")
                (url.openConnection() as? HttpURLConnection)?.run {
                    val rezultat = this.inputStream.bufferedReader().use { it.readText() }
                    val jsonArray = JSONArray(rezultat)
                    for(i:Int in 0..jsonArray.length()-1){
                        val jsonObject = jsonArray.getJSONObject(i)
                        val id = jsonObject.getInt("id")
                        val naziv = jsonObject.getString("naziv")
                        val idPredmeta = jsonObject.getInt("PredmetId")
                        listaGrupa.add(Grupa(id, naziv, idPredmeta, dajNazivPredmetaZaID(idPredmeta)))
                    }
                }

                return@withContext listaGrupa
            }
        }


    }
}