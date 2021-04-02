package ba.etf.rma21.projekat.data.static

import ba.etf.rma21.projekat.data.models.Kviz
import java.util.*

fun dajKvizove():List<Kviz>{
    return listOf(Kviz("Kviz 0","RMA", Date(2020-1900,1,1),Date(2021-1900,5,1),Date(),5,"RMA-G1",5.2F),
        Kviz("Kviz 1","IM2",Date(2021-1900,1,1),Date(2021-1900,2,1),null,5,"RI",null),
    Kviz("Kviz 2","TP",Date(2021-1900,5,1),Date(2021-1900,5,5),null,5,"TP-G1",null));
}