package ba.etf.rma21.projekat.data.repositories

import ba.etf.rma21.projekat.data.models.Predmet
import ba.etf.rma21.projekat.data.static.dajPredmete
import ba.etf.rma21.projekat.data.static.dajUpisanePredmete

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
            return getAll().filter({p: Predmet ->p.godina==godina})
        }
        fun ispisiSaPredmeta(predmet: Predmet){
            upisaniPredmeti.remove(predmet)
        }
    }

}