package ba.etf.rma21.projekat.data.repositories

import ba.etf.rma21.projekat.data.models.Predmet
import ba.etf.rma21.projekat.data.static.dajPredmetePrveGodine

class PredmetRepository {
    companion object {
        fun getUpisani(): List<Predmet> {
            // TODO: Implementirati
            return emptyList()
        }

        fun getAll(): List<Predmet> {
            // TODO: Implementirati
            return dajPredmetePrveGodine();
        }
        // TODO: Implementirati i ostale potrebne metode
    }

}