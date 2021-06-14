package ba.etf.rma21.projekat.viewmodel

import android.content.Context
import ba.etf.rma21.projekat.Korisnik
import ba.etf.rma21.projekat.data.AppDatabase
import ba.etf.rma21.projekat.data.models.*
import ba.etf.rma21.projekat.data.repositories.DBRepository
import ba.etf.rma21.projekat.data.repositories.OdgovorRepository
import ba.etf.rma21.projekat.data.repositories.PitanjeKvizRepository
import ba.etf.rma21.projekat.data.repositories.TakeKvizRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PokusajViewModel(val ucitajKviz:((List<Pitanje>)->Unit)?) {
    lateinit var aktivniKviz:Kviz
    var brPitanja:Int=0
    var kvizZavrsen =false
    val scope = CoroutineScope(Job()+Dispatchers.Main)
    lateinit var kvizTaken: KvizTaken
    lateinit var listaPitanja:List<Pitanje>
    var brTacnih:Int=0
    var brOdgovorenih = 0
    private lateinit var context: Context
    fun setContext(_context: Context){
        context=_context
    }
    fun aktivirajKviz(kviz: Kviz){
        scope.launch {
            DBRepository.updateNow()
            aktivniKviz = kviz
            Korisnik.aktivirajKviz(kviz)
            kvizZavrsen = false
            if (aktivniKviz.osvojeniBodovi != null || Datum.before(aktivniKviz.datumKraj!!,Datum.dajDatumBezVremena())) kvizZavrsen =
                true
            val db = AppDatabase.getInstance(context)
            val odgovoriDao = db.odgovorDao()
            val pitanjaDao = db.pitanjeDao()
            val listaOdgovora = odgovoriDao.dajOdgovoreZaKviz(kviz.id)
            listaPitanja = pitanjaDao.dajPitanjaZaKviz(aktivniKviz.id)


            brPitanja=listaPitanja.size
            kvizTaken = TakeKvizRepository.zapocniKviz(kviz.id)!!
            brTacnih=pitanjaDao.dajBrTacnihNaKvizu(aktivniKviz.id)
            brOdgovorenih = odgovoriDao.dajBrOdgovorenih(aktivniKviz.id)
            for(odgovor in listaOdgovora){
                for(pitanje in listaPitanja){
                    if(pitanje.id == odgovor.idPitanja){
                        Korisnik.aktivnaPitanja!![pitanje] = odgovor.odgovoreno
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
        brOdgovorenih++
        CoroutineScope(Job()).launch {
            OdgovorRepository.postaviOdgovorKviz(kvizTaken.id,pitanje.id,odgovor)
        }
        if(brOdgovorenih==listaPitanja.size) predajKviz()
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
        if(Datum.after(aktivniKviz.datumKraj!!,Datum.dajDatumBezVremena())) {
            aktivniKviz.datumRada = Datum.dajDatumBezVremena()
            if(brPitanja!=0) {
                aktivniKviz.osvojeniBodovi = brTacnih * 100F / brPitanja
            }else aktivniKviz.osvojeniBodovi=0F
        }
        for(pitanje in listaPitanja){
            if(!Korisnik.aktivnaPitanja!!.containsKey(pitanje)){
                postaviOdgovor(pitanje,-1)
            }
        }
        aktivniKviz.predan=true

        CoroutineScope(Job()).launch{
            AppDatabase.getInstance(context).kvizDao().azurirajKviz(aktivniKviz)
            OdgovorRepository.predajOdgovore(aktivniKviz.id)
        }
        return dajPoruku(brTacnih)
    }

}