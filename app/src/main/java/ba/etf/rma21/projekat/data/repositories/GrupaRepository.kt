package ba.etf.rma21.projekat.data.repositories

import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.static.dajGrupe
import ba.etf.rma21.projekat.data.static.getUpisaneGrupe
import java.util.stream.Collectors

class GrupaRepository {
    companion object {
        private var grupe: MutableList<Grupa> = mutableListOf()
        private var upisaneGrupe: MutableList<Grupa> = mutableListOf()
        init {
          grupe.addAll(dajGrupe())
            upisaneGrupe.addAll(getUpisaneGrupe())
        }

        fun getGroupsByPredmet(nazivPredmeta: String): List<Grupa> {
            return grupe.stream().filter { t: Grupa? -> t?.nazivPredmeta == nazivPredmeta }.collect(Collectors.toList());
        }
        fun dajUpisaneGrupe():List<Grupa>{
            return upisaneGrupe;
        }
        fun upisiGrupu(grupa: Grupa){
            upisaneGrupe.add(grupa)
        }
    }
}