package ba.etf.rma21.projekat.data.repositories

import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.models.Predmet
import ba.etf.rma21.projekat.data.static.dajKvizove
import java.util.*
import java.util.stream.Collectors

class KvizRepository {

    companion object {
        private var kvizovi:MutableList<Kviz> = mutableListOf();
        init {
           kvizovi.addAll(dajKvizove())
        }

        fun getMyKvizes(): List<Kviz> {
            val mojiPredmeti = PredmetRepository.getUpisani().stream().map({p:Predmet -> p.toString()}).collect(Collectors.toList())
            val mojeGrupe = GrupaRepository.dajUpisaneGrupe().stream().map { grupa: Grupa? -> grupa?.toString() }.collect(Collectors.toList())
            return kvizovi.stream().filter({k:Kviz -> mojiPredmeti.contains(k.nazivPredmeta)}).filter{kviz: Kviz ->  mojeGrupe.contains(kviz.nazivGrupe)}.collect(Collectors.toList());
        }

        fun getAll(): List<Kviz> {
            return kvizovi
        }

        fun getDone(): List<Kviz> {
            return getMyKvizes().stream().filter({k:Kviz -> k.datumRada!=null}).collect(Collectors.toList())
        }

        fun getFuture(): List<Kviz> {
            return getMyKvizes().stream().filter({k:Kviz -> k.datumRada==null && k.datumPocetka.after(Date())}).collect(Collectors.toList())
        }

        fun getNotTaken(): List<Kviz> {
            return getMyKvizes().stream().filter({k: Kviz -> k.datumRada==null && k.datumKraj.before(Date())}).collect(Collectors.toList());
        }

    }
}