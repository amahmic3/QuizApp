package ba.etf.rma21.projekat

import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.models.Pitanje
import ba.etf.rma21.projekat.data.repositories.KvizRepository

object Korisnik {
    var mapaVrijednosti = HashMap<String,Int>()
    var mapaKvizova = HashMap<Kviz,HashMap<Pitanje,Int>>()
    var aktivnaPitanja :HashMap<Pitanje,Int>?=null
    fun azururajKvizove(){
        for(kviz in KvizRepository.getMyKvizes()) mapaKvizova.putIfAbsent(kviz,HashMap())
    }
    fun aktivirajKviz(kviz: Kviz){
        aktivnaPitanja= mapaKvizova[kviz]
    }
    init {
        azururajKvizove()
        mapaVrijednosti["Godina"]=0
        mapaVrijednosti["Predmet"]=0
        mapaVrijednosti["Grupa"]=0
    }
}