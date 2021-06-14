package ba.etf.rma21.projekat.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Account(
    @PrimaryKey @SerializedName("acHash") var acHash:String,
    @ColumnInfo(name ="lastUpdate") var lastUpdate:String) {

}