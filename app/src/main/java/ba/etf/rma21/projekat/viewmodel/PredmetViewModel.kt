package ba.etf.rma21.projekat.viewmodel

import ba.etf.rma21.projekat.data.models.Predmet
import ba.etf.rma21.projekat.data.repositories.PredmetRepository
import java.util.stream.Collectors

class PredmetViewModel {
    fun dajNeUpisanePredmete(godina : Int):List<Predmet>{
        return PredmetRepository.getPredmetsByGodina(godina).stream().filter{p:Predmet -> !PredmetRepository.getUpisani().contains(p)}.collect(Collectors.toList())
    }
    fun upisiPredmet(naziv:String,godina:Int){
        PredmetRepository.upisiPredmet(Predmet(naziv,godina))
    }

}