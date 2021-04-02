package ba.etf.rma21.projekat.data.repositories

import ba.etf.rma21.projekat.data.models.Predmet
import ba.etf.rma21.projekat.data.static.dajPredmete
import ba.etf.rma21.projekat.data.static.dajUpisanePredmete
import java.util.stream.Collectors

class PredmetRepository {
    companion object {
        private var upisaniPredmeti:MutableList<Predmet> = mutableListOf()
        init {
            upisaniPredmeti.addAll(dajUpisanePredmete())
        }
        fun getUpisani(): List<Predmet> {
            return upisaniPredmeti;
        }
        fun upisiPredmet(predmet: Predmet){
            upisaniPredmeti.add(predmet);
        }
        fun getAll(): List<Predmet> {
            return dajPredmete();
        }
        fun getPredmetsByGodina(godina:Int):List<Predmet>{
            return getAll().stream().filter({p: Predmet ->p.godina==godina}).collect(Collectors.toList());
        }
    }

}