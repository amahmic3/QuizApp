package ba.etf.rma21.projekat.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Grupa(@PrimaryKey val id:Int, val naziv: String, val idPredmeta: Int, val nazivPredmeta:String) {
    override fun toString(): String {
        return naziv;
    }
}