package ba.etf.rma21.projekat.viewmodel

import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.repositories.KvizRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class KvizViewModel(val izmijeniKivzove:((listaKivzova:List<Kviz>)->Unit)?) {
    private val kriterij = Comparator{k1:Kviz,k2:Kviz -> k1.datumPocetka!!.compareTo(k2.datumPocetka) }
    val scope = CoroutineScope(Job() + Dispatchers.Main)

    fun dajKvizoveZaKorisnika(){
        scope.launch {
            val sviKivzovi = KvizRepository.getUpisani()?.sortedWith(kriterij)
            if (sviKivzovi != null) {
                izmijeniKivzove?.invoke(sviKivzovi)
            }
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
            val sviKivzovi = KvizRepository.getDone()?.sortedWith(kriterij)
            if (sviKivzovi != null) {
                izmijeniKivzove?.invoke(sviKivzovi)
            }
        }
    }
    fun dajBuduceKvizove(){
        scope.launch {
            val sviKivzovi = KvizRepository.getFuture()?.sortedWith(kriterij)
            if (sviKivzovi != null) {
                izmijeniKivzove?.invoke(sviKivzovi)
            }
        }
    }
    fun dajProsleKvizove(){
        scope.launch {
            val sviKivzovi = KvizRepository.getNotTaken()?.sortedWith(kriterij)
            if (sviKivzovi != null) {
                izmijeniKivzove?.invoke(sviKivzovi)
            }
        }
    }
}