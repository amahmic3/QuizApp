package ba.etf.rma21.projekat.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PitanjeKviz(@PrimaryKey(autoGenerate = true) val id:Int?,val idKviza:Int,val idPitanja:Int) {
}