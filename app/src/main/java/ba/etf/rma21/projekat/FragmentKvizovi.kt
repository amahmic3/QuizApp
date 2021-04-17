package ba.etf.rma21.projekat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.view.KvizListAdapter
import ba.etf.rma21.projekat.viewmodel.GrupaViewModel
import ba.etf.rma21.projekat.viewmodel.KvizViewModel
import ba.etf.rma21.projekat.viewmodel.PredmetViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class FragmentKvizovi : Fragment() {

    private lateinit var kvizovi: RecyclerView;
    private lateinit var kvizAdapter: KvizListAdapter;
    private lateinit var filter: Spinner;
    private var kvizViewModel = KvizViewModel()
    private var predmetViewModel = PredmetViewModel()
    private var grupaViewModel = GrupaViewModel();
    private val onFilterChanged = object : AdapterView.OnItemSelectedListener{
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_kvizovi, container, false)

        kvizovi = view.findViewById(R.id.listaKvizova)
        kvizovi.layoutManager = GridLayoutManager(activity,2);
        kvizAdapter = KvizListAdapter(kvizViewModel.dajKvizoveZaKorisnika(),{kviz-> zapocniKviz(kviz)})
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

        return view
    }

    override fun onResume() {
        super.onResume()
        val menu: BottomNavigationView? = view?.findViewById(R.id.bottomNav)
        menu?.selectedItemId =R.id.kvizovi
    }
    private fun zapocniKviz(kviz: Kviz){

    }
    companion object {
        fun newInstance() = FragmentKvizovi()
    }
}