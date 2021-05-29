package ba.etf.rma21.projekat.data.models

import java.util.*

data class Kviz(val id:Int,
    val naziv: String, val naziviPredmeta: List<String>, val datumPocetka: Date?, val datumKraj: Date?,
    var datumRada: Date?, val trajanje: Int, var osvojeniBodovi: Float?
) {
    private fun nazivPredmeta():String{
        var naziv=""
        naziviPredmeta.forEach {
            naziv+=it
        }
        return naziv
    }
    override fun hashCode(): Int {
        return (naziv+nazivPredmeta()).hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Kviz

        if (naziv != other.naziv) return false
        if (nazivPredmeta() != other.nazivPredmeta()) return false

        return true
    }
}