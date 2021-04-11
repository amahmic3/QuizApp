package ba.etf.rma21.projekat.data.repositories

import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.models.Predmet
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.*
import java.util.stream.Collectors

class KvizRepositoryTest{
    @Test
    fun testGetMyKvizes(){
        assertTrue(KvizRepository.getMyKvizes().stream().filter{kviz : Kviz -> PredmetRepository.getUpisani().map { predmet:Predmet -> predmet.naziv  }.contains(kviz.nazivPredmeta)}.collect(Collectors.toList()).size == KvizRepository.getMyKvizes().size)
    }
    @Test
    fun testGetDone(){
        var brKvizova=0
        val mojiKvizovi = KvizRepository.getMyKvizes()
        for(kviz in KvizRepository.getDone()){
            if(kviz.datumRada!=null && mojiKvizovi.contains(kviz)) brKvizova++;
        }
        assertEquals(brKvizova,KvizRepository.getDone().size)
    }
    @Test
    fun testGetFuture(){
        var brKvizova=0
        val mojiKvizovi = KvizRepository.getMyKvizes()
        for(kviz in KvizRepository.getFuture()){
            if(kviz.datumRada==null && mojiKvizovi.contains(kviz)) brKvizova++;
        }

        assertEquals(brKvizova,KvizRepository.getFuture().size)
    }
    @Test
    fun testGetNotTaken(){
        var brKvizova=0
        val mojiKvizovi = KvizRepository.getMyKvizes()
        for(kviz in KvizRepository.getNotTaken()){
            if(kviz.datumKraj.before(Date()) && kviz.datumRada==null && mojiKvizovi.contains(kviz)) brKvizova++;
        }
        assertEquals(brKvizova,KvizRepository.getNotTaken().size)
    }

}