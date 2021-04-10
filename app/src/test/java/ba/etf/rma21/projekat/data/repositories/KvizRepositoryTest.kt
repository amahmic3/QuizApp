package ba.etf.rma21.projekat.data.repositories

import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.models.Predmet
import org.junit.Assert.*
import org.junit.Test
import java.util.stream.Collectors

class KvizRepositoryTest{
    @Test
    fun testGetMyKvizes(){
        assertTrue(KvizRepository.getMyKvizes().stream().filter{kviz : Kviz -> PredmetRepository.getUpisani().map { predmet:Predmet -> predmet.naziv  }.contains(kviz.nazivPredmeta)}.collect(Collectors.toList()).size == KvizRepository.getMyKvizes().size)
    }
    @Test
    fun testDajSveKvizove(){
        assertEquals(3,KvizRepository.getAll().size)
    }
    @Test
    fun testGetDone(){
        assertEquals(1,KvizRepository.getDone().size)
    }

}