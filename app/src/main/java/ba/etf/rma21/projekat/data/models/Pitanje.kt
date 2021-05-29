package ba.etf.rma21.projekat.data.models

class Pitanje(val id:Int, val naziv:String, val tekstPitanja:String, val opcije:List<String>, val tacan:Int) {
    override fun hashCode(): Int {
        return (naziv+tekstPitanja).hashCode()
    }
}