package ba.etf.rma21.projekat.data.static

import ba.etf.rma21.projekat.data.models.Pitanje
import ba.etf.rma21.projekat.data.models.PitanjeKviz

fun dajSvaPitanja():List<Pitanje>{
    return mutableListOf<Pitanje>(Pitanje("p1","Koji hazardi ne postoje?", listOf("Strukturni","Kontrolni","Opasni","Podatkovni"),2),
    Pitanje("p2","Koja instrukcija ne postoji u MIPS ISA?", listOf("PUSHA","ADDI","SUB","XOR"),0))
}
fun dajPitanjeKviz():List<PitanjeKviz>{
    return listOf(PitanjeKviz("p1","Kviz 11 G2","RA"),PitanjeKviz("p2","Kviz 11 G2","RA"))
}