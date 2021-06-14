package ba.etf.rma21.projekat.data.models

import java.text.SimpleDateFormat
import java.util.*

class Datum {
    companion object{
        val datum:Calendar
        init {
            datum = Calendar.getInstance()
        }
        fun dajDatum(godina:Int,mjesec:Int,dan:Int): Date {
            datum.clear()
            datum.set(godina,mjesec-1,dan)
            return datum.time
        }
        fun dajFormatiranDatum(trazeniDatum:Date):String {
            datum.time = trazeniDatum
            return String.format("%02d.%02d.%d.", datum.get(Calendar.DAY_OF_MONTH), datum.get(Calendar.MONTH)+1, datum.get(Calendar.YEAR))
        }
        fun dajTrenutniDatum():String{
            val format =SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            return format.format(Calendar.getInstance().time)
        }
        fun dajDatumBezVremena():String{
            val format =SimpleDateFormat("yyyy-MM-dd")
            return format.format(Calendar.getInstance().time)
        }
        fun after(datum1:String,datum2:String):Boolean{
            val d1 = SimpleDateFormat("yyyy-MM-dd").parse(datum1)
            val d2 = SimpleDateFormat("yyyy-MM-dd").parse(datum2)
            return d1.after(d2)
        }
        fun before(datum1:String,datum2: String):Boolean{
            val d1 = SimpleDateFormat("yyyy-MM-dd").parse(datum1)
            val d2 = SimpleDateFormat("yyyy-MM-dd").parse(datum2)
            return d1.before(d2)
        }
        fun poredi(datum1: String,datum2: String):Int{
            val d1 = SimpleDateFormat("yyyy-MM-dd").parse(datum1)
            val d2 = SimpleDateFormat("yyyy-MM-dd").parse(datum2)
            return d1.compareTo(d2)
        }
    }
}