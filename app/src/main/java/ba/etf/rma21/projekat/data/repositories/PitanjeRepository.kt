package ba.etf.rma21.projekat.data.repositories

import ba.etf.rma21.projekat.data.models.Pitanje
import ba.etf.rma21.projekat.data.static.dajSvaPitanja

class PitanjeRepository {
    companion object{
        var listaPitanja = mutableListOf<Pitanje>()
        init {
            listaPitanja.addAll(dajSvaPitanja())
        }
        fun getPitanja():List<Pitanje>{
            return listaPitanja
        }
    }
}