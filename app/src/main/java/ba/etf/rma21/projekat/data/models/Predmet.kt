package ba.etf.rma21.projekat.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Predmet(@PrimaryKey val id:Int, val naziv: String, val godina: Int){
    override fun toString(): String {
        return naziv
    }

}