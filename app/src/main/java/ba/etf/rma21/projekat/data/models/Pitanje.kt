package ba.etf.rma21.projekat.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Pitanje(@PrimaryKey val id:Int, val naziv:String, val tekstPitanja:String, val opcije:String, val tacan:Int) {
    override fun hashCode(): Int {
        return (naziv+tekstPitanja).hashCode()
    }
}