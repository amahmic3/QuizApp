package ba.etf.rma21.projekat.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Odgovor(@PrimaryKey(autoGenerate = true) val id:Int?, val idPitanja:Int, val idKviza:Int, var odgovoreno:Int) {
}