package ba.etf.rma21.projekat.viewmodel

import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.models.Predmet
import ba.etf.rma21.projekat.data.repositories.GrupaRepository

class GrupaViewModel {
    fun dajGrupeZaPredmet(predmet: Predmet):List<Grupa>{
        return GrupaRepository.getGroupsByPredmet(predmet.naziv)
    }
    fun upisiUGrupu(nazivGrupe:String,nazivPredmeta:String){
        GrupaRepository.upisiGrupu(Grupa(nazivGrupe,nazivPredmeta))
    }
}