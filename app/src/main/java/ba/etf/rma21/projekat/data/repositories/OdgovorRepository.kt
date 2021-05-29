package ba.etf.rma21.projekat.data.repositories

import ba.etf.rma21.projekat.Korisnik
import ba.etf.rma21.projekat.data.models.Odgovor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class OdgovorRepository {
    companion object {

        suspend fun getOdgovoriKviz(idKviza: Int): List<Odgovor>? {
            return withContext(Dispatchers.IO){
                val listaOdgovora = mutableListOf<Odgovor>()
                val url = URL(ApiConfig.baseURL+"/student/${AccountRepository.getHash()}/kviztaken/$idKviza/odgovori")
                (url.openConnection() as? HttpURLConnection)?.run {
                    val rezultat = this.inputStream.bufferedReader().use { it.readText() }
                    val jsonNiz = JSONArray(rezultat)
                    for(i:Int in 0..jsonNiz.length()-1){
                        val jo = jsonNiz.getJSONObject(i)
                        listaOdgovora.add(Odgovor(jo.getInt("PitanjeId"),jo.getInt("odgovoreno")))
                    }
                }
                return@withContext listaOdgovora
            }
        }

        suspend fun postaviOdgovorKviz(idKvizTaken: Int, idPitanje: Int, odgovor: Int): Int? {
            return withContext(Dispatchers.IO){
                val povratna:Int =-1
                val url = URL(ApiConfig.baseURL+"/student/${AccountRepository.getHash()}/kviztaken/$idKvizTaken/odgovor")
                val requestBody = "{\"odgovor\":$odgovor,\"pitanje\":$idPitanje,\"bodovi\":${Korisnik.kvizTaken.osvojeniBodovi}}"
                (url.openConnection() as? HttpURLConnection)?.run {
                    this.doOutput = true
                    this.doInput=true
                    this.requestMethod="POST"
                    this.setRequestProperty("accept","application/json")
                    this.setRequestProperty("Content-Type","application/json")
                    val os =this.outputStream
                    os.write(requestBody.toByteArray())
                    val rezultat:String = this.inputStream.bufferedReader().use { it.readText() }
                    val jo = JSONObject(rezultat)
                }
                return@withContext povratna
            }
        }
    }


}