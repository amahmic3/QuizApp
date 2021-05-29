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
    lateinit var kvizTaken: KvizTaken
    lateinit var listaPitanja:List<Pitanje>
    var brTacnih:Int=0
    fun aktivirajKviz(kviz: Kviz){
        scope.launch {
            aktivniKviz = kviz
            Korisnik.aktivirajKviz(kviz)
            kvizZavrsen = false
            if (aktivniKviz.osvojeniBodovi != null || aktivniKviz.datumKraj!!.before(Calendar.getInstance().time)) kvizZavrsen =
                true
            listaPitanja = PitanjeKvizRepository.getPitanja(kviz.id)
            brPitanja=listaPitanja.size
            kvizTaken = TakeKvizRepository.zapocniKviz(kviz.id)!!

            val listaOdgovora = OdgovorRepository.getOdgovoriKviz(kvizTaken.id) as List<Odgovor>
            for(odgovor in listaOdgovora){
                for(pitanje in listaPitanja){
                    if(pitanje.id == odgovor.id){
                        Korisnik.aktivnaPitanja!![pitanje] = odgovor.odgovoreno
                        brTacnih+=if(pitanje.tacan==odgovor.odgovoreno) 1 else 0
                        break
                    }
                }
            }
            ucitajKviz?.invoke(listaPitanja)
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
        brTacnih += if(pitanje.tacan == odgovor) 1 else 0
        CoroutineScope(Job()).launch {
            OdgovorRepository.postaviOdgovorKviz(kvizTaken.id,pitanje.id,odgovor)
        }
    }
    fun dajBrTacnih():Int{
        return brTacnih
    }
    fun dajPoruku(brTacnih:Int=dajBrTacnih()):String{
        var procenat = if(brPitanja!=0) brTacnih*100.0/brPitanja else 0.0
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
        for(pitanje in listaPitanja){
            if(!Korisnik.aktivnaPitanja!!.containsKey(pitanje)){
                postaviOdgovor(pitanje,-1)
            }
        }
        return dajPoruku(brTacnih)
    }

}