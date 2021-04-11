package ba.etf.rma21.projekat.viewmodel

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class KvizViewModelTest {



    @Test
    fun dajKvizoveZaKorisnika() {
        val kvizViewModel = KvizViewModel()
        var bool = true
        val kvizovi = kvizViewModel.dajKvizoveZaKorisnika()
        for (i in 0..kvizovi.size-2){
            if(kvizovi[i].datumPocetka.after(kvizovi[i+1].datumPocetka)) bool = false
        }
        assertTrue(bool)
    }

    @Test
    fun dajSveKvizove() {
        val kvizViewModel = KvizViewModel()
        var bool = true
        val kvizovi = kvizViewModel.dajSveKvizove()
        for (i in 0..kvizovi.size-2){
            if(kvizovi[i].datumPocetka.after(kvizovi[i+1].datumPocetka)) bool = false
        }
        assertTrue(bool)
    }

    @Test
    fun dajUrađeneKvizove() {
        val kvizViewModel = KvizViewModel()
        var bool = true
        val kvizovi = kvizViewModel.dajUrađeneKvizove()
        for (i in 0..kvizovi.size-2){
            if(kvizovi[i].datumPocetka.after(kvizovi[i+1].datumPocetka)) bool = false
        }
        assertTrue(bool)
    }

    @Test
    fun dajBuduceKvizove() {
        val kvizViewModel = KvizViewModel()
        var bool = true
        val kvizovi = kvizViewModel.dajBuduceKvizove()
        for (i in 0..kvizovi.size-2){
            if(kvizovi[i].datumPocetka.after(kvizovi[i+1].datumPocetka)) bool = false
        }
        assertTrue(bool)
    }

    @Test
    fun dajProsleKvizove() {
        val kvizViewModel = KvizViewModel()
        var bool = true
        val kvizovi = kvizViewModel.dajProsleKvizove()
        for (i in 0..kvizovi.size-2){
            if(kvizovi[i].datumPocetka.after(kvizovi[i+1].datumPocetka)) bool = false
        }
        assertTrue(bool)
    }
}