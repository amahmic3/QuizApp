package ba.etf.rma21.projekat.data.repositories

import ba.etf.rma21.projekat.data.models.KvizTaken
import ba.etf.rma21.projekat.data.models.Odgovor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL

class OdgovorRepository {
    companion object {

        suspend fun getOdgovoriKviz(idKviza: Int): List<Odgovor>? {
            return withContext(Dispatchers.IO){
                val listaOdgovora = mutableListOf<Odgovor>()
                val pocetiKviz = TakeKvizRepository.getPocetiKvizovi()?.first { kvizTaken -> kvizTaken.KvizId==idKviza }
                if(pocetiKviz==null) return@withContext listaOdgovora
                val url = URL(ApiConfig.baseURL+"/student/${AccountRepository.getHash()}/kviztaken/${pocetiKviz.id}/odgovori")
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
                var povratna:Int =-1
                val url = URL(ApiConfig.baseURL+"/student/${AccountRepository.getHash()}/kviztaken/$idKvizTaken/odgovor")
                val kvizTaken:KvizTaken? = TakeKvizRepository.getPocetiKvizovi()?.first { kvizTaken ->  kvizTaken.id == idKvizTaken}
                val pitanja = PitanjeKvizRepository.getPitanja(kvizTaken!!.KvizId)
                val postavljenoPitanje = pitanja.first { pitanje -> pitanje.id==idPitanje }
                val osvojeniBodovi:Double = if(odgovor == postavljenoPitanje.tacan) kvizTaken.osvojeniBodovi+100F/pitanja.size else kvizTaken.osvojeniBodovi
                povratna=osvojeniBodovi.toInt()
                val requestBody = "{\"odgovor\":$odgovor,\"pitanje\":$idPitanje,\"bodovi\":${osvojeniBodovi}}"
                (url.openConnection() as? HttpURLConnection)?.run {
                    this.doOutput = true
                    this.doInput=true
                    this.requestMethod="POST"
                    this.setRequestProperty("accept","application/json")
                    this.setRequestProperty("Content-Type","application/json")
                    val os =this.outputStream
                    os.write(requestBody.toByteArray())
                    val rezultat:String = this.inputStream.bufferedReader().use { it.readText() }
                }
                return@withContext povratna
            }
        }
    }


}