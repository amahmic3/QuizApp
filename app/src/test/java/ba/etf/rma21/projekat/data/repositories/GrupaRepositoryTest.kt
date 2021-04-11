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
        assertEquals(0,GrupaRepository.getGroupsByPredmet("MK").size)
    }
    @Test
    fun test3GetGroupsByPredmet(){
        var bool = true;
        for(grupa in GrupaRepository.getGroupsByPredmet("TP")){
            if(grupa.nazivPredmeta != "TP"){
                bool = false;
                break;
            }
        }
        assertTrue(bool)
    }
    @Test
    fun test1DajUpisaneGrupe(){
        assertEquals(7,GrupaRepository.dajUpisaneGrupe().size)
    }
    @Test
    fun test1UpisiGrupu(){
        assertEquals(7,GrupaRepository.dajUpisaneGrupe().size)
        GrupaRepository.upisiGrupu(Grupa("TP-G1","TP"))
        assertEquals(8,GrupaRepository.dajUpisaneGrupe().size)
    }

}