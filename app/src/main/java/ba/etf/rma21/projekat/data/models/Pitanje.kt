package ba.etf.rma21.projekat.data.models

class Pitanje(val naziv:String, val tekst:String, val opcije:List<String>,val tacan:Int) {
    override fun hashCode(): Int {
        return (naziv+tekst).hashCode()
    }
}