package ba.etf.rma21.projekat.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Kviz(@PrimaryKey val id:Int,
                val naziv: String, val naziviPredmeta: String, val datumPocetka: String?, val datumKraj: String?,
                var datumRada: String?, val trajanje: Int, var osvojeniBodovi: Float?, var predan:Boolean=false
) {
    private fun nazivPredmeta():String{

        return naziviPredmeta
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