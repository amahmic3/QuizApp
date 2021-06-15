package ba.etf.rma21.projekat

import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.models.Pitanje
import ba.etf.rma21.projekat.data.models.Predmet
import ba.etf.rma21.projekat.viewmodel.PokusajViewModel

object Korisnik {
    var mapaVrijednosti = HashMap<String,Int>()
    var mapaKvizova = HashMap<Kviz,HashMap<Pitanje,Int>>()
    var aktivnaPitanja :HashMap<Pitanje,Int>?=null
    lateinit var pokusajViewModel: PokusajViewModel

    var listaPredmeta = listOf<Predmet>()
    var listaGrupa = listOf<Grupa>()

    fun aktivirajKviz(kviz: Kviz){

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