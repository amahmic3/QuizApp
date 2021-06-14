package ba.etf.rma21.projekat.viewmodel

import android.content.Context
import android.content.Intent
import ba.etf.rma21.projekat.data.AppDatabase
import ba.etf.rma21.projekat.data.models.Datum
import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.repositories.AccountRepository
import ba.etf.rma21.projekat.data.repositories.DBRepository
import ba.etf.rma21.projekat.data.repositories.KvizRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class KvizViewModel(val izmijeniKivzove:((listaKivzova:List<Kviz>)->Unit)?) {
    private val kriterij = Comparator{k1:Kviz,k2:Kviz -> Datum.poredi(k1.datumPocetka!!,k2.datumPocetka!!) }
    val scope = CoroutineScope(Job() + Dispatchers.Main)
    private lateinit var context: Context
    fun setContext(_context: Context){
        context=_context
    }
    fun dajKvizoveZaKorisnika(){
        scope.launch {
            DBRepository.updateNow()
            val sviKivzovi = AppDatabase.getInstance(context).kvizDao().dajSveKvizove().sortedWith(kriterij)
            izmijeniKivzove?.invoke(sviKivzovi)
        }
    }
    fun dajSveKvizove(){
        scope.launch {
            val sviKivzovi = KvizRepository.getAll()?.sortedWith(kriterij)
            if (sviKivzovi != null) {
                izmijeniKivzove?.invoke(sviKivzovi)
            }
        }
    }
    fun dajUrađeneKvizove(){
        scope.launch {
            DBRepository.updateNow()
            val sviKivzovi = AppDatabase.getInstance(context).kvizDao().dajSveKvizove().filter { kviz -> kviz.osvojeniBodovi!=null }.sortedWith(kriterij)
            izmijeniKivzove?.invoke(sviKivzovi)
        }
    }
    fun dajBuduceKvizove(){
        scope.launch {
            DBRepository.updateNow()
            val sviKivzovi = AppDatabase.getInstance(context).kvizDao().dajSveKvizove().filter { kviz: Kviz -> Datum.after(kviz.datumPocetka!!,Datum.dajDatumBezVremena()) }.sortedWith(kriterij)
            if (sviKivzovi != null) {
                izmijeniKivzove?.invoke(sviKivzovi)
            }
        }
    }
    fun dajProsleKvizove(){
        scope.launch {
            DBRepository.updateNow()
            val sviKivzovi = AppDatabase.getInstance(context).kvizDao().dajSveKvizove().filter { kviz: Kviz -> Datum.before(kviz.datumKraj!!,Datum.dajDatumBezVremena()) }.sortedWith(kriterij)
            if (sviKivzovi != null) {
                izmijeniKivzove?.invoke(sviKivzovi)
            }
        }
    }

}