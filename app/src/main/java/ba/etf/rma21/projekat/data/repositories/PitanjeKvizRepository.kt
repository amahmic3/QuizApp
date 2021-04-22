package ba.etf.rma21.projekat.data.repositories

import ba.etf.rma21.projekat.data.models.Pitanje
import ba.etf.rma21.projekat.data.models.PitanjeKviz
import ba.etf.rma21.projekat.data.static.dajPitanjeKviz

class PitanjeKvizRepository {
    companion object{

        fun getPitanjeKviz(nazivKviza:String,nazivPredmeta: String):HashSet<String>{
            return dajPitanjeKviz().filter { pitanjeKviz: PitanjeKviz -> pitanjeKviz.nazivKviza==nazivKviza && pitanjeKviz.nazivPredmeta==nazivPredmeta }.map { pitanjeKviz -> pitanjeKviz.nazivPitanja }.toHashSet()
        }
        fun getPitanja(nazivKviza:String,nazivPredmeta:String):List<Pitanje>{
            val poveznica = getPitanjeKviz(nazivKviza,nazivPredmeta)
            return PitanjeRepository.getPitanja().filter { pitanje: Pitanje ->  poveznica.contains(pitanje.naziv)}
        }
    }
}