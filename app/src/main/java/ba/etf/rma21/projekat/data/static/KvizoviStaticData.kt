 package ba.etf.rma21.projekat.data.static

import ba.etf.rma21.projekat.data.models.Datum
import ba.etf.rma21.projekat.data.models.Kviz
import java.util.*

fun dajKvizove():List<Kviz>{

        return listOf(Kviz("Kviz 0 G1","RMA", Datum.dajDatum(2020,1,1),Datum.dajDatum(2021,5,1),Calendar.getInstance().time,3,"RMA-G1",5.2F),
            Kviz("Kviz 1 G1","IM2",Datum.dajDatum(2021,1,1),Datum.dajDatum(2021,2,1),null,5,"IM2-G1",null),
            Kviz("Kviz 2 G2","TP",Datum.dajDatum(2021,5,1),Datum.dajDatum(2021,5,5),null,2,"TP-G2",null),
                Kviz("Kviz 3 G1","TP",Datum.dajDatum(2021,5,10),Datum.dajDatum(2021,5,15),null,2,"TP-G1",null),
                Kviz("Kviz 4 G1","OS",Datum.dajDatum(2021,4,20),Datum.dajDatum(2021,4,21),null,5,"OS-G1",null),
                Kviz("Kviz 4 G2","OS",Datum.dajDatum(2021,4,20),Datum.dajDatum(2021,4,21),null,5,"OS-G2",null),
                Kviz("Kviz 5 G3","LD",Datum.dajDatum(2021,1,1),Datum.dajDatum(2021,1,3),null,5,"LD-G3",null),
                Kviz("Kviz 5 G2","LD",Datum.dajDatum(2021,1,1),Datum.dajDatum(2021,1,3),null,5,"LD-G2",null),
                Kviz("Kviz 5 G1","LD",Datum.dajDatum(2021,1,1),Datum.dajDatum(2021,1,3),null,5,"LD-G1",null),
                Kviz("Kviz 6 G1","MLTI",Datum.dajDatum(2021,5,3),Datum.dajDatum(2021,5,4),null,5,"MLTI-G1",null),
                Kviz("Kviz 6 G2","MLTI",Datum.dajDatum(2021,5,4),Datum.dajDatum(2021,5,5),null,5,"MLTI-G2",null) ,
                Kviz("Kviz 6 G3","MLTI",Datum.dajDatum(2021,5,4),Datum.dajDatum(2021,5,5),null,5,"MLTI-G3",null),
                Kviz("Kviz 7 G2","US",Datum.dajDatum(2021,5,1),Datum.dajDatum(2021,5,5),null,5,"US-G2",null),
                Kviz("Kviz 8 G1","ORM",Datum.dajDatum(2021,4,2),Datum.dajDatum(2021,4,3),Datum.dajDatum(2021,4,2),5,"ORM-G1",9F),
                Kviz("Kviz 9 G1","VIS",Datum.dajDatum(2021,5,1),Datum.dajDatum(2021,5,5),null,5,"VIS-G1",null),
                Kviz("Kviz 9 G2","VIS",Datum.dajDatum(2021,5,1),Datum.dajDatum(2021,5,5),null,5,"VIS-G2",null),
                Kviz("Kviz 11 G2","RA",Datum.dajDatum(2021,5,1),Datum.dajDatum(2021,5,5),null,5,"RA-G2",null),
                Kviz("Kviz 12 G1","OOAD",Datum.dajDatum(2021,5,1),Datum.dajDatum(2021,5,5),null,5,"OOAD-G1",null),
                Kviz("Kviz 13 G1","LD",Datum.dajDatum(2021,5,1),Datum.dajDatum(2021,5,5),null,5,"LD-G1",null)
        );
}