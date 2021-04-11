package ba.etf.rma21.projekat.data.repositories

import ba.etf.rma21.projekat.data.models.Predmet
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class PredmetRepositoryTest{


    @Test
    fun testgetPredmetiByGodina(){
        assertEquals(10,PredmetRepository.getPredmetsByGodina(1).size)
        assertEquals(12,PredmetRepository.getPredmetsByGodina(2).size)
    }
    @Test
    fun testPredmetiByGdoina1(){
        var bool = true;
        for(predmet in PredmetRepository.getPredmetsByGodina(1)){
            if(predmet.godina!=1){
                bool =false
                break
            }
        }
        assertTrue(bool)
    }
    @Test
    fun testUpisiPredmet(){
        assertEquals(7,PredmetRepository.getUpisani().size)
        PredmetRepository.upisiPredmet(PredmetRepository.getAll().filter { predmet: Predmet -> predmet.naziv == "ASP" }.first())
        assertEquals(8,PredmetRepository.getUpisani().size)
        PredmetRepository.ispisiSaPredmeta(PredmetRepository.getAll().filter { predmet: Predmet -> predmet.naziv == "ASP" }.first())
    }
    @Test
    fun upisaniTest(){
        assertEquals(7,PredmetRepository.getUpisani().size)
    }

}