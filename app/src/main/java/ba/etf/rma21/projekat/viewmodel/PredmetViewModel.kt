package ba.etf.rma21.projekat.viewmodel

import ba.etf.rma21.projekat.data.models.Predmet
import ba.etf.rma21.projekat.data.repositories.PredmetRepository

class PredmetViewModel {
    fun dajNeUpisanePredmete(godina : Int):List<Predmet>{
        return PredmetRepository.getPredmetsByGodina(godina).filter{p:Predmet -> !PredmetRepository.getUpisani().contains(p)}
    }
    fun upisiPredmet(naziv:String,godina:Int){
        PredmetRepository.upisiPredmet(PredmetRepository.getPredmetsByGodina(godina).filter { predmet: Predmet -> predmet.naziv==naziv }.first())

    }

}