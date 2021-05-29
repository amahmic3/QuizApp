package ba.etf.rma21.projekat.viewmodel

import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.models.Predmet
import ba.etf.rma21.projekat.data.repositories.PredmetIGrupaRepository
import kotlinx.coroutines.*

class GrupaViewModel(private val ispuniGrupe: ((List<Grupa>)->Unit)?) {
    val scope = CoroutineScope(Job() + Dispatchers.Main)

    fun dajGrupeZaPredmet(predmet: Predmet){
       scope.launch {
           PredmetIGrupaRepository.getGrupeZaPredmet(predmet.id)?.let { ispuniGrupe?.invoke(it) }
       }
    }
    fun upisiUGrupu(idGrupe:Int){
        CoroutineScope(Dispatchers.IO).launch {
            PredmetIGrupaRepository.upisiUGrupu(idGrupe)
        }
    }
}