package ba.etf.rma21.projekat.data.static

import ba.etf.rma21.projekat.data.models.Predmet

fun dajPredmete(): List<Predmet> {
    return listOf(
        Predmet("IM1", 1), Predmet
            ("LAG", 1), Predmet
            ("OE", 1), Predmet
            ("UUP", 1), Predmet
            ("IF1", 1), Predmet
            ("IM2", 1), Predmet
            ("MLTI", 1), Predmet
            ("TP", 1), Predmet
            ("OS", 1), Predmet
            ("VIS", 1), Predmet
            ("DM", 2), Predmet
            ("ASP", 2), Predmet
            ("LD", 2), Predmet
            ("SP", 2), Predmet
            ("RPR", 2), Predmet
            ("OBP", 2), Predmet
            ("OOAD", 2), Predmet
            ("RMA", 2), Predmet
            ("US", 2), Predmet
            ("ORM", 2), Predmet
            ("RA", 2), Predmet
            ("AFJ", 2)
    )
}
fun dajUpisanePredmete():List<Predmet>{
    return listOf( Predmet
        ("OOAD", 2), Predmet
        ("RMA", 2), Predmet
        ("US", 2), Predmet
        ("ORM", 2), Predmet
        ("RA", 2), Predmet
        ("AFJ", 2),Predmet
        ("IM2", 1))
}