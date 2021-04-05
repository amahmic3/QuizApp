package ba.etf.rma21.projekat.data.static

import ba.etf.rma21.projekat.data.models.Datum
import ba.etf.rma21.projekat.data.models.Kviz
import java.util.*

fun dajKvizove():List<Kviz>{

        return listOf(Kviz("Kviz 0","RMA", Datum.dajDatum(2020,1,1),Datum.dajDatum(2021,5,1),Calendar.getInstance().time,5,"RMA-G1",5.2F),
            Kviz("Kviz 1","IM2",Datum.dajDatum(2021,1,1),Datum.dajDatum(2021,2,1),null,5,"RI",null),
            Kviz("Kviz 2","TP",Datum.dajDatum(2021,5,1),Datum.dajDatum(2021,5,5),null,5,"TP-G1",null));
}