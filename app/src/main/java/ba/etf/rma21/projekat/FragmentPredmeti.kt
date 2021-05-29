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
import ba.etf.rma21.projekat.viewmodel.PredmetViewModel
import kotlin.collections.List
import kotlin.collections.listOf
import kotlin.collections.map
import kotlin.collections.set


class FragmentPredmeti : Fragment() {
    private var spasiKorisnika:Boolean = false
    private lateinit var spinnerGodine: Spinner;
    private lateinit var spinnerPredmeti: Spinner;
    private lateinit var spinnerGrupe: Spinner;
    private lateinit var upis: Button;
    private var predmetViewModel = PredmetViewModel(this@FragmentPredmeti::izmijeniPredmete)
    private var grupaViewModel = GrupaViewModel(this@FragmentPredmeti::izmijeniGrupe)
    private var godineListener = object : AdapterView.OnItemSelectedListener{
        override fun onItemSelected(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long
        ) {
            if(spasiKorisnika) Korisnik.mapaVrijednosti["Godina"]=position
            if(!spasiKorisnika){
                Korisnik.mapaVrijednosti["Predmet"]?.let { spinnerPredmeti.setSelection(it) }
            }
            predmetViewModel.dajNeUpisanePredmete(position+1)
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
            if(spasiKorisnika) Korisnik.mapaVrijednosti["Predmet"]=position
            if(!spasiKorisnika){
                Korisnik.mapaVrijednosti["Grupa"]?.let { spinnerGrupe.setSelection(it) }
            }
            grupaViewModel.dajGrupeZaPredmet(Korisnik.listaPredmeta.get(position))
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
            if(spasiKorisnika) Korisnik.mapaVrijednosti["Grupa"]=position
            if(spinnerGrupe.count!=0) upis.isVisible=true
            spasiKorisnika=true
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
        upis.isVisible=false
        spinnerGodine = view.findViewById(R.id.odabirGodina)
        spinnerGodine.adapter = ArrayAdapter(context!!,android.R.layout.simple_list_item_1, listOf("1","2","3","4","5"))
        spinnerGodine.onItemSelectedListener = godineListener
        spinnerPredmeti = view.findViewById(R.id.odabirPredmet)
        spinnerPredmeti.onItemSelectedListener = predmetiListener
        spinnerGrupe = view.findViewById(R.id.odabirGrupa)
        spinnerGrupe.onItemSelectedListener = grupeListener
        Korisnik.mapaVrijednosti["Godina"]?.let { spinnerGodine.setSelection(it) }
        upis.setOnClickListener{
            val grupa =Korisnik.listaGrupa.get(spinnerGrupe.selectedItemId.toInt())
            grupaViewModel.upisiUGrupu(grupa.id)
            Korisnik.mapaVrijednosti["Predmet"]=0
            Korisnik.mapaVrijednosti["Grupa"]=0
            val transakcija = activity!!.supportFragmentManager.beginTransaction()
            transakcija.replace(R.id.container,FragmentPoruka.newInstance("Uspješno ste upisani u grupu ${grupa.naziv} predmeta ${grupa.nazivPredmeta}!"))
            transakcija.addToBackStack(null)
            transakcija.commit()
        }
        return view
    }
    fun izmijeniPredmete(listaPredmet:List<Predmet>){
        Korisnik.listaPredmeta = listaPredmet
        spinnerPredmeti.adapter = ArrayAdapter(context!!, android.R.layout.simple_list_item_1, listaPredmet.map { p: Predmet -> p.toString() })
        if(spinnerPredmeti.adapter.isEmpty){
            upis.isVisible=false
            spinnerGrupe.adapter=ArrayAdapter<String>(context!!, android.R.layout.simple_list_item_1, listOf())
            spasiKorisnika=true
        }
    }
    fun izmijeniGrupe(listaGrupa:List<Grupa>){
        Korisnik.listaGrupa = listaGrupa
        spinnerGrupe.adapter = ArrayAdapter(context!!,android.R.layout.simple_list_item_1, listaGrupa.map {g: Grupa ->g.toString() })
        if(spinnerGrupe.adapter.isEmpty){
            upis.isVisible = false
            spasiKorisnika=true
        }
    }

    companion object {
        fun newInstance() = FragmentPredmeti()
    }
}