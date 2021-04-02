package ba.etf.rma21.projekat

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import ba.etf.rma21.projekat.data.models.Predmet
import ba.etf.rma21.projekat.data.repositories.PredmetRepository
import ba.etf.rma21.projekat.data.static.dajGodine

class UpisPredmet : AppCompatActivity() {
    lateinit var spinnerGodine:Spinner;
    lateinit var spinnerPredmeti:Spinner;
    lateinit var spinnerGrupe:Spinner;
    var godina:Int=1;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upis_predmet)
        spinnerGodine = findViewById(R.id.odabirGodina);
        spinnerPredmeti = findViewById(R.id.odabirPredmet);
        spinnerGrupe = findViewById(R.id.odabirGrupa);
        spinnerGodine.adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1, dajGodine());
        spinnerGodine.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                godina= position+1;
                spinnerPredmeti.adapter = ArrayAdapter(this@UpisPredmet,android.R.layout.simple_list_item_1, PredmetRepository.getAll().stream().filter({p: Predmet ->p.godina==godina}).toArray().toMutableList());

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

    }
}