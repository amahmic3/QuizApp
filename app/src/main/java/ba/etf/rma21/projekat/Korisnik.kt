package ba.etf.rma21.projekat

import ba.etf.rma21.projekat.data.models.*
import ba.etf.rma21.projekat.viewmodel.PokusajViewModel

object Korisnik {
    var mapaVrijednosti = HashMap<String,Int>()
    var mapaKvizova = HashMap<Kviz,HashMap<Pitanje,Int>>()
    var aktivnaPitanja :HashMap<Pitanje,Int>?=null
    lateinit var pokusajViewModel: PokusajViewModel

    var listaPredmeta = listOf<Predmet>()
    var listaGrupa = listOf<Grupa>()

    lateinit var kvizTaken:KvizTaken
    var brTacnihOdgovora:Int = 0

    fun aktivirajKviz(kviz: Kviz){
        brTacnihOdgovora=0
        if(!mapaKvizova.containsKey(kviz)){
                mapaKvizova[kviz] = HashMap()
        }
        aktivnaPitanja= mapaKvizova[kviz]

    }
    init {

        mapaVrijednosti["Godina"]=0
        mapaVrijednosti["Predmet"]=0
        mapaVrijednosti["Grupa"]=0
    }
}