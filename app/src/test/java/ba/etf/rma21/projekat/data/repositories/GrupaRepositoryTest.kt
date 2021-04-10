package ba.etf.rma21.projekat.data.repositories

import ba.etf.rma21.projekat.data.models.Grupa
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith


class GrupaRepositoryTest{

    @Test
    fun test1GetGropsByPredmet(){
        assertEquals(3,GrupaRepository.getGroupsByPredmet("TP").size)
    }
    @Test
    fun test2GetGropsByPredmet(){
        assertEquals(0,GrupaRepository.getGroupsByPredmet("MLTI").size)
    }
    @Test
    fun test1DajUpisaneGrupe(){
        assertEquals(3,GrupaRepository.dajUpisaneGrupe().size)
    }
    @Test
    fun test1UpisiGrupu(){
        assertEquals(3,GrupaRepository.dajUpisaneGrupe().size)
        GrupaRepository.upisiGrupu(Grupa("TP-G1","TP"))
        assertEquals(4,GrupaRepository.dajUpisaneGrupe().size)
    }

}