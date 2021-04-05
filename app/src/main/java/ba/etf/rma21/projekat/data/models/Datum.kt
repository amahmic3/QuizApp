package ba.etf.rma21.projekat.data.models

import java.util.*

class Datum {
    companion object{
        val datum:Calendar
        init {
            datum = Calendar.getInstance()
        }
        fun dajDatum(godina:Int,mjesec:Int,dan:Int): Date {
            datum.clear()
            datum.set(godina,mjesec-1,dan);
            return datum.time
        }
        fun dajFormatiranDatum(trazeniDatum:Date):String {
            datum.time = trazeniDatum
            return String.format("%02d.%02d.%d.", datum.get(Calendar.DAY_OF_MONTH), datum.get(Calendar.MONTH)+1, datum.get(Calendar.YEAR))
        }
    }
}