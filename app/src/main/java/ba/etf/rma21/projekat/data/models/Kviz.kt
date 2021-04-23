package ba.etf.rma21.projekat.data.models

import java.util.*

data class Kviz(
    val naziv: String, val nazivPredmeta: String, val datumPocetka: Date, val datumKraj: Date,
    var datumRada: Date?, val trajanje: Int, val nazivGrupe: String, var osvojeniBodovi: Float?
) {
    override fun hashCode(): Int {
        return (naziv+nazivPredmeta).hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Kviz

        if (naziv != other.naziv) return false
        if (nazivPredmeta != other.nazivPredmeta) return false

        return true
    }
}