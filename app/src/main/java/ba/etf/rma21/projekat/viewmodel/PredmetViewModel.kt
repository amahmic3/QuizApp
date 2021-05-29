package ba.etf.rma21.projekat.viewmodel

import ba.etf.rma21.projekat.data.models.Predmet
import ba.etf.rma21.projekat.data.repositories.PredmetIGrupaRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PredmetViewModel(private val ispuniPredmete:((predmeti:List<Predmet>)->Unit)?){
    val scope = CoroutineScope(Job() + Dispatchers.Main)
    fun dajNeUpisanePredmete(godina : Int){
            scope.launch {
                val mojeGrupeID = PredmetIGrupaRepository.getUpisaneGrupe()?.map { grupa -> grupa.idPredmeta }
                val predmeti = PredmetIGrupaRepository.getPredmeti()?.filter { predmet -> !(mojeGrupeID?.contains(predmet.id) as Boolean) && predmet.godina==godina }
                if (predmeti != null) {
                    ispuniPredmete?.invoke(predmeti)
                }

            }
    }

}