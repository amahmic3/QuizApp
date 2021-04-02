package ba.etf.rma21.projekat.data.static

import ba.etf.rma21.projekat.data.models.Grupa

fun dajGrupe():List<Grupa>{
    return listOf(
        Grupa("IM1-G1","IM1"),
        Grupa("IM1-G2","IM1"),
        Grupa("IM1-G3","IM1"),
        Grupa("IM1-G4","IM1"),
        Grupa("IM2-G1","IM2"),
        Grupa("IM1-G2","IM2"),
        Grupa("IM1-G3","IM2"),
        Grupa("RMA-G1","RMA"),
        Grupa("RMA-G2","RMA"),
        Grupa("RMA-G3","RMA"),
        Grupa("TP-G1","TP"),
        Grupa("TP-G2","TP"),
        Grupa("TP-G3","TP"),
        Grupa("ASP-G1","ASP"),
        Grupa("ASP-G2","ASP"),
        Grupa("ASP-G3","ASP"))
}

fun getUpisaneGrupe():List<Grupa>{
    return listOf(Grupa("IM2-G1","IM2"),  Grupa("RMA-G1","RMA"),Grupa("OOAD","OOAD-G2"))
}