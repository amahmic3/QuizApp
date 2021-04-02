package ba.etf.rma21.projekat.viewmodel

import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.repositories.KvizRepository

class KvizViewModel {
    private val kriterij = Comparator{k1:Kviz,k2:Kviz -> k1.datumPocetka.compareTo(k2.datumPocetka)}
    fun dajKvizoveZaKorisnika():List<Kviz>{
        return KvizRepository.getMyKvizes().sortedWith(kriterij)
    }
    fun dajSveKvizove():List<Kviz>{
        return KvizRepository.getAll().sortedWith(kriterij)
    }
    fun dajUrađeneKvizove():List<Kviz>{
        return KvizRepository.getDone().sortedWith(kriterij)
    }
    fun dajBuduceKvizove():List<Kviz>{
        return KvizRepository.getFuture().sortedWith(kriterij)
    }
    fun dajProsleKvizove():List<Kviz>{
        return KvizRepository.getNotTaken().sortedWith(kriterij)
    }
}