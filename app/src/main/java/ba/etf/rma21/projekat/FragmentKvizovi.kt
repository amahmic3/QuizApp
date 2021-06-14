package ba.etf.rma21.projekat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma21.projekat.data.models.Datum
import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.models.Pitanje
import ba.etf.rma21.projekat.view.KvizListAdapter
import ba.etf.rma21.projekat.viewmodel.KvizViewModel
import ba.etf.rma21.projekat.viewmodel.PokusajViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*

class FragmentKvizovi : Fragment() {

    private lateinit var kvizovi: RecyclerView;
    private lateinit var kvizAdapter: KvizListAdapter;
    private lateinit var filter: Spinner;
    private var kvizViewModel = KvizViewModel(this@FragmentKvizovi::izmijeniKvizove)
    private val onFilterChanged = object : AdapterView.OnItemSelectedListener{
        override fun onItemSelected(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long
        ) {
            kvizAdapter.kvizovi= listOf<Kviz>()
            kvizAdapter.notifyDataSetChanged()

            when (position) {
                0 -> (kvizViewModel.dajKvizoveZaKorisnika())
                1 -> (kvizViewModel.dajSveKvizove())
                2 -> (kvizViewModel.dajUrađeneKvizove())
                3 -> (kvizViewModel.dajBuduceKvizove())
                4 -> (kvizViewModel.dajProsleKvizove())
            }
            Toast.makeText(activity, "Loading...", Toast.LENGTH_LONG).show()
            //kvizovi.adapter?.notifyDataSetChanged()
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {

        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_kvizovi, container, false)

        kvizovi = view.findViewById(R.id.listaKvizova)
        kvizovi.layoutManager = GridLayoutManager(activity,2);
        kvizAdapter = KvizListAdapter(listOf<Kviz>(),{kviz-> zapocniKviz(kviz)})
        kvizovi.adapter = kvizAdapter
        filter = view.findViewById(R.id.filterKvizova)
        filter.adapter = ArrayAdapter<String>(activity!!, android.R.layout.simple_list_item_1, listOf(
                "Svi moji kvizovi",
                "Svi kvizovi",
                "Urađeni kvizovi",
                "Budući kvizovi",
                "Prošli kvizovi"
            ))
        filter.onItemSelectedListener = onFilterChanged
        context?.let { kvizViewModel.setContext(it) }

        return view
    }

    override fun onResume() {
        super.onResume()
        val menu: BottomNavigationView? = view?.findViewById(R.id.bottomNav)
        menu?.selectedItemId =R.id.kvizovi
    }
    private fun zapocniKviz(kviz: Kviz){
        if(Datum.before(kviz.datumPocetka!!,Datum.dajDatumBezVremena())) {
            (activity as MainActivity).promijeniMenu()
            val pokusajViewModel = PokusajViewModel(this@FragmentKvizovi::ucitajKviz)
            context?.let { pokusajViewModel.setContext(it) }
            Korisnik.pokusajViewModel=pokusajViewModel
            Toast.makeText(activity, "Loading...", Toast.LENGTH_LONG).show()
            pokusajViewModel.aktivirajKviz(kviz)
        }else{
            Toast.makeText(context as MainActivity,"Kviz još nije počeo",Toast.LENGTH_SHORT).show()
        }
    }
    fun ucitajKviz(listaPitanja:List<Pitanje>){
        val transakcija = (activity as MainActivity).supportFragmentManager.beginTransaction()
        transakcija.replace(R.id.container, FragmentPokusaj.newInstance(listaPitanja))
        transakcija.addToBackStack(null)
        transakcija.commit()
    }
    fun izmijeniKvizove(listaKvizova:List<Kviz>):Unit{
        kvizAdapter.kvizovi=listaKvizova
        kvizAdapter.notifyDataSetChanged()
    }
    companion object {
        fun newInstance() = FragmentKvizovi()
    }
}