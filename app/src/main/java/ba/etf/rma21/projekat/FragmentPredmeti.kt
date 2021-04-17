package ba.etf.rma21.projekat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.models.Predmet
import ba.etf.rma21.projekat.viewmodel.GrupaViewModel
import ba.etf.rma21.projekat.viewmodel.KvizViewModel
import ba.etf.rma21.projekat.viewmodel.PredmetViewModel


class FragmentPredmeti(private var godina: Int) : Fragment() {

    private lateinit var spinnerGodine: Spinner;
    private lateinit var spinnerPredmeti: Spinner;
    private lateinit var spinnerGrupe: Spinner;
    private lateinit var upis: Button;
    private var predmetViewModel = PredmetViewModel()
    private var grupaViewModel = GrupaViewModel()
    private var godineListener = object : AdapterView.OnItemSelectedListener{
        override fun onItemSelected(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long
        ) {
            godina= position+1

            spinnerPredmeti.adapter = ArrayAdapter(context!!, android.R.layout.simple_list_item_1, predmetViewModel.dajNeUpisanePredmete(godina).map { p: Predmet -> p.toString() })
            if(spinnerPredmeti.adapter.isEmpty){
                upis.isVisible=false

            }
        }
        override fun onNothingSelected(parent: AdapterView<*>?) {
            upis.isVisible = false;
        }
    }
    private var predmetiListener = object : AdapterView.OnItemSelectedListener{
        override fun onItemSelected(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long
        ) {

            spinnerGrupe.adapter = ArrayAdapter(context!!,android.R.layout.simple_list_item_1, grupaViewModel.dajGrupeZaPredmet(predmetViewModel.dajNeUpisanePredmete(godina)[position]).map {g: Grupa ->g.toString() })
            if(spinnerGrupe.adapter.isEmpty) upis.isVisible = false
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
            upis.isVisible = false;
        }
    }
    private var grupeListener = object : AdapterView.OnItemSelectedListener{
        override fun onItemSelected(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long
        ) {

            if(spinnerGrupe.count!=0) upis.isVisible=true
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_predmeti, container, false)

        upis = view.findViewById(R.id.dodajPredmetDugme)
        spinnerGodine = view.findViewById(R.id.odabirGodina)
        spinnerGodine.adapter = ArrayAdapter(context!!,android.R.layout.simple_list_item_1, listOf("1","2","3","4","5"))
        spinnerGodine.onItemSelectedListener = godineListener
        spinnerPredmeti = view.findViewById(R.id.odabirPredmet)
        spinnerPredmeti.onItemSelectedListener = predmetiListener
        spinnerGrupe = view.findViewById(R.id.odabirGrupa)
        spinnerGrupe.onItemSelectedListener = grupeListener
        spinnerGodine.setSelection(godina-1)
        upis.setOnClickListener{
            grupaViewModel.upisiUGrupu(spinnerGrupe.selectedItem.toString(),spinnerPredmeti.selectedItem.toString())
            predmetViewModel.upisiPredmet(spinnerPredmeti.selectedItem.toString(),godina)
            val transakcija = activity!!.supportFragmentManager.beginTransaction()
            transakcija.replace(R.id.container,FragmentPoruka.newInstance(grupaViewModel.dajGrupu(spinnerGrupe.selectedItem.toString(),spinnerPredmeti.selectedItem.toString())))
            transakcija.addToBackStack(null)
            transakcija.commit()
        }
        return view
    }

    companion object {
        val predmeti = newInstance(1)
        fun newInstance(godina : Int) = FragmentPredmeti(godina)

    }
}