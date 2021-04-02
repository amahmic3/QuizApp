package ba.etf.rma21.projekat

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma21.projekat.view.KvizListAdapter
import ba.etf.rma21.projekat.viewmodel.GrupaViewModel
import ba.etf.rma21.projekat.viewmodel.KvizViewModel
import ba.etf.rma21.projekat.viewmodel.PredmetViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {


    private lateinit var kvizovi: RecyclerView;
    private lateinit var kvizAdapter: KvizListAdapter;
    private lateinit var filter: Spinner;
    private lateinit var upisPredmeta: FloatingActionButton;
    private var godina: String = "1";
    private var kvizViewModel = KvizViewModel()
    private var predmetViewModel = PredmetViewModel()
    private var grupaViewModel = GrupaViewModel();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        filter = findViewById(R.id.filterKvizova)
        upisPredmeta = findViewById(R.id.upisDugme)
        kvizovi = findViewById(R.id.listaKvizova)
        kvizovi.layoutManager = GridLayoutManager(this,2);
        kvizAdapter = KvizListAdapter(kvizViewModel.dajKvizoveZaKorisnika())
        kvizovi.adapter = kvizAdapter

        upisPredmeta.setOnClickListener {
            upisiPredmet();
        }
        filter.adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_list_item_1,
            listOf(
                "Svi moji kvizovi",
                "Svi kvizovi",
                "Urađeni kvizovi",
                "Budući kvizovi",
                "Prošli kvizovi"
            )
        );
        filter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {
                    0 -> kvizAdapter.kvizovi = (kvizViewModel.dajKvizoveZaKorisnika())
                    1 -> kvizAdapter.kvizovi = (kvizViewModel.dajSveKvizove())
                    2 -> kvizAdapter.kvizovi = (kvizViewModel.dajUrađeneKvizove())
                    3 -> kvizAdapter.kvizovi = (kvizViewModel.dajBuduceKvizove())
                    4 -> kvizAdapter.kvizovi = (kvizViewModel.dajProsleKvizove())
                }
                kvizovi.adapter?.notifyDataSetChanged()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

    }

    private fun upisiPredmet() {
        val intent = Intent(this, UpisPredmet::class.java)
        intent.putExtra("godina", godina)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        if(intent.getBundleExtra("upisPredmeta")!=null){
            val bundle = intent.getBundleExtra("upisPredmeta")
            godina = bundle?.getString("godina").toString()
            val predmet = bundle?.getString("predmet")
            val grupa = bundle?.getString("grupa")
            predmetViewModel.upisiPredmet(predmet!!,godina.toInt())
            grupaViewModel.upisiUGrupu(grupa!!,predmet)
            kvizovi.adapter?.notifyDataSetChanged()
            kvizovi.adapter?.notifyDataSetChanged()
           intent.removeExtra("upisPredmeta")
        }
  }


}

