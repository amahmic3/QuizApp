package ba.etf.rma21.projekat.viewmodel

import ba.etf.rma21.projekat.Korisnik
import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.models.KvizTaken
import ba.etf.rma21.projekat.data.models.Odgovor
import ba.etf.rma21.projekat.data.models.Pitanje
import ba.etf.rma21.projekat.data.repositories.OdgovorRepository
import ba.etf.rma21.projekat.data.repositories.PitanjeKvizRepository
import ba.etf.rma21.projekat.data.repositories.TakeKvizRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

class PokusajViewModel(val ucitajKviz:((List<Pitanje>)->Unit)?) {
    lateinit var aktivniKviz:Kviz
    var brPitanja:Int=0
    var kvizZavrsen =false
    val scope = CoroutineScope(Job()+Dispatchers.Main)
    fun aktivirajKviz(kviz: Kviz){
        scope.launch {
            aktivniKviz = kviz
            Korisnik.aktivirajKviz(kviz)
            kvizZavrsen = false
            if (aktivniKviz.osvojeniBodovi != null || aktivniKviz.datumKraj!!.before(Calendar.getInstance().time)) kvizZavrsen =
                true
            val listaPitanja = PitanjeKvizRepository.getPitanja(kviz.id)
            brPitanja=listaPitanja.size
            val kvizTaken = TakeKvizRepository.zapocniKviz(kviz.id)
            Korisnik.kvizTaken=kvizTaken as KvizTaken
            if(kvizTaken!=null) {
                val listaOdgovora = OdgovorRepository.getOdgovoriKviz(kvizTaken.id) as List<Odgovor>
                for(odgovor in listaOdgovora){
                    for(pitanje in listaPitanja){
                        if(pitanje.id == odgovor.idPitanja){
                            Korisnik.aktivnaPitanja!![pitanje] = odgovor.redniBrojOdgovora
                            Korisnik.brTacnihOdgovora+=if(pitanje.tacan==odgovor.redniBrojOdgovora) 1 else 0
                            break
                        }
                    }
                }
                ucitajKviz?.invoke(listaPitanja)
            }
        }
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
        Korisnik.brTacnihOdgovora += if(pitanje.tacan == odgovor) 1 else 0
        Korisnik.kvizTaken.osvojeniBodovi = (Korisnik.brTacnihOdgovora/brPitanja.toDouble())*100
        CoroutineScope(Job()).launch {
            OdgovorRepository.postaviOdgovorKviz(Korisnik.kvizTaken.id,pitanje.id,odgovor)
        }
    }
    fun dajBrTacnih():Int{
        return Korisnik.brTacnihOdgovora
    }
    fun dajPoruku(brTacnih:Int=dajBrTacnih()):String{
        var procenat = brTacnih*100.0/brPitanja
        if(brPitanja==0) procenat=0.0;
        return "Završili ste kviz ${aktivniKviz.naziv} sa tačnosti ${procenat}%"
    }
    fun predajKviz():String{
        val brTacnih = dajBrTacnih()
        if(aktivniKviz.datumKraj!!.after(Calendar.getInstance().time)) {
            aktivniKviz.datumRada = Calendar.getInstance().time
            if(brPitanja!=0) {
                aktivniKviz.osvojeniBodovi = brTacnih * 100F / brPitanja
            }else aktivniKviz.osvojeniBodovi=0F
        }
        return dajPoruku(brTacnih)
    }

}