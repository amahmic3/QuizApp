package ba.etf.rma21.projekat.data.repositories

import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.models.Predmet
import ba.etf.rma21.projekat.data.static.dajKvizove
import java.util.*

class KvizRepository {

    companion object {
        private var kvizovi:MutableList<Kviz> = mutableListOf();
        init {
           kvizovi.addAll(dajKvizove())
        }

        fun getMyKvizes(): List<Kviz> {
            val mojiPredmeti = PredmetRepository.getUpisani().map({p:Predmet -> p.toString()})
            val mojeGrupe = GrupaRepository.dajUpisaneGrupe().map { grupa: Grupa? -> grupa?.toString() }
            return kvizovi.filter({k:Kviz -> mojiPredmeti.contains(k.nazivPredmeta)}).filter{kviz: Kviz ->  mojeGrupe.contains(kviz.nazivGrupe)}
        }

        fun getAll(): List<Kviz> {
            return kvizovi
        }

        fun getDone(): List<Kviz> {
            return getMyKvizes().filter({k:Kviz -> k.datumRada!=null})
        }

        fun getFuture(): List<Kviz> {
            return getMyKvizes().filter({k:Kviz -> k.datumRada==null && k.datumPocetka.after(Date())})
        }

        fun getNotTaken(): List<Kviz> {
            return getMyKvizes().filter({k: Kviz -> k.datumRada==null && k.datumKraj.before(Date())})
        }

    }
}