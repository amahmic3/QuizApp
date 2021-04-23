package ba.etf.rma21.projekat.viewmodel

import ba.etf.rma21.projekat.Korisnik
import ba.etf.rma21.projekat.data.models.Datum
import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.models.Pitanje
import java.util.*

class PokusajViewModel() {
    lateinit var aktivniKviz:Kviz
    var brPitanja:Int=0
    var kvizZavrsen =false
    fun aktivirajKviz(kviz: Kviz){
        aktivniKviz=kviz
        Korisnik.aktivirajKviz(kviz)
        kvizZavrsen=false
        if(aktivniKviz.osvojeniBodovi !=null || aktivniKviz.datumKraj.before(Calendar.getInstance().time)) kvizZavrsen = true
    }
    fun daLiJeOdgovorio(pitanje: Pitanje):Boolean{
        return Korisnik.aktivnaPitanja!!.containsKey(pitanje)
    }
    fun dajOdgovor(pitanje: Pitanje):Int
    {
        if(daLiJeOdgovorio(pitanje)){
            return Korisnik.aktivnaPitanja?.get(pitanje)!!
        }
        return -1
    }
    fun postaviOdgovor(pitanje: Pitanje,odgovor:Int){
        Korisnik.aktivnaPitanja!![pitanje]=odgovor
    }
    fun dajBrTacnih():Int{
        val pitanja = Korisnik.aktivnaPitanja
        var brTacnih=0
        for((k,v) in pitanja!!){
            if(k.tacan==v) brTacnih++
        }
        return brTacnih
    }
    fun dajPoruku(brTacnih:Int=dajBrTacnih()):String{
        var procenat = brTacnih*100.0/brPitanja
        if(brPitanja==0) procenat=0.0;
        return "Završili ste kviz ${aktivniKviz.naziv} sa tačnosti ${procenat}%"
    }
    fun predajKviz():String{
        val brTacnih = dajBrTacnih()
        if(aktivniKviz.datumKraj.after(Calendar.getInstance().time)) {
            aktivniKviz.datumRada = Calendar.getInstance().time
            if(brPitanja!=0) {
                aktivniKviz.osvojeniBodovi = brTacnih * 100F / brPitanja
            }else aktivniKviz.osvojeniBodovi=0F
        }
        return dajPoruku(brTacnih)
    }
}