package ba.etf.rma21.projekat.data.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class AccountRepository(var acHash:String) {

    companion object {
        var acHash: String = "b993f14e-5b8a-4cf6-b88d-05f200894c4e"
        suspend fun postaviHash(acHash:String):Boolean{
                return withContext(Dispatchers.IO) {
                    try{
                    var povratna = false
                    val url = URL(ApiConfig.baseURL + "/student/$acHash")
                    (url.openConnection() as? HttpURLConnection)?.run {
                        val result = this.inputStream.bufferedReader().use { it.readText() }
                        val jo = JSONObject(result)
                        if (jo.length() > 1) {
                            this@Companion.acHash = acHash
                            povratna = true
                        } else {
                            povratna = false
                        }
                    }
                    return@withContext povratna
                }catch (t:Throwable){
                    return@withContext false
                }
                }

        }

        fun getHash(): String {
            return acHash
        }


    }

}

