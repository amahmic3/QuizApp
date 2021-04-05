package ba.etf.rma21.projekat

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isEmpty
import androidx.core.view.isNotEmpty
import androidx.core.view.isVisible
import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.models.Predmet
import ba.etf.rma21.projekat.viewmodel.GrupaViewModel
import ba.etf.rma21.projekat.viewmodel.PredmetViewModel
import java.util.*
import java.util.stream.Collectors

class UpisPredmet : AppCompatActivity() {
    lateinit var spinnerGodine:Spinner;
    lateinit var spinnerPredmeti:Spinner;
    lateinit var spinnerGrupe:Spinner;
    lateinit var upis: Button;
    var predmetViewModel = PredmetViewModel()
    var grupaViewModel = GrupaViewModel()
    var godina:Int=1;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upis_predmet)
        spinnerGodine = findViewById(R.id.odabirGodina);
        spinnerPredmeti = findViewById(R.id.odabirPredmet);
        spinnerGrupe = findViewById(R.id.odabirGrupa);
        upis = findViewById(R.id.dodajPredmetDugme)
        upis.isVisible = false;
        spinnerGodine.adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1, listOf("1","2","3","4","5"));

        spinnerGodine.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                godina= position+1;
                spinnerPredmeti.adapter = ArrayAdapter(this@UpisPredmet,android.R.layout.simple_list_item_1, predmetViewModel.dajNeUpisanePredmete(godina).stream().map {p: Predmet -> p.toString() }.toArray())
                if(spinnerPredmeti.adapter.isEmpty) upis.isVisible=false
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                upis.isVisible = false;
            }

        }
        spinnerPredmeti.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                spinnerGrupe.adapter = ArrayAdapter(this@UpisPredmet,android.R.layout.simple_list_item_1, grupaViewModel.dajGrupeZaPredmet(predmetViewModel.dajNeUpisanePredmete(godina)[position]).stream().map {g:Grupa->g.toString() }.toArray())
                if(spinnerGrupe.adapter.isEmpty) upis.isVisible = false
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                upis.isVisible = false;
            }

        }

        spinnerGrupe.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if(parent?.isNotEmpty()!!) upis.isVisible=true
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }
        upis.setOnClickListener {
            val otvoriPocetnuAktivnost = Intent(this,MainActivity::class.java).apply {
                var bundle = Bundle()
                bundle.putString("godina",spinnerGodine.selectedItem.toString())
                bundle.putString("predmet",spinnerPredmeti.selectedItem.toString())
                bundle.putString("grupa",spinnerGrupe.selectedItem.toString())
                putExtra("upisPredmeta",bundle)
            }
            startActivity(otvoriPocetnuAktivnost)
        }

    }
}