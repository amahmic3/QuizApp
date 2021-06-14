package ba.etf.rma21.projekat

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import ba.etf.rma21.projekat.data.models.Pitanje
import ba.etf.rma21.projekat.viewmodel.PokusajViewModel


class FragmentPitanje(val pitanje:Pitanje) : Fragment() {
    lateinit var odgovori:ListView
    lateinit var pokusajViewModel:PokusajViewModel
    val zelenaBoja = Color.parseColor("#3DDC84")
    val crvenaBoja = Color.parseColor("#DB4F3D")
    val crnaBoja = Color.parseColor("#FFFFFF")

    val itemClickedListener = object : AdapterView.OnItemClickListener{
        override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            if (!odgovoreno){
                pokusajViewModel.postaviOdgovor(pitanje,position)
                    if(position == pitanje.tacan) {
                        (parent!!.getChildAt(position) as TextView).setTextColor(zelenaBoja)

                    }else {
                        (parent!!.getChildAt(position) as TextView).setTextColor(crvenaBoja)
                        (parent.getChildAt(pitanje.tacan) as TextView).setTextColor(zelenaBoja)
                    }
                odgovoreno=true
                (parentFragment as FragmentPokusaj).postaviBoju()
            }

        }

    }
    var odgovoreno:Boolean = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        pokusajViewModel= (parentFragment as FragmentPokusaj).pokusajViewModel

        if(pokusajViewModel.kvizZavrsen||pokusajViewModel.daLiJeOdgovorio(pitanje) ){
            odgovoreno=true
        }
        val view =  inflater.inflate(R.layout.fragment_pitanje, container, false)

        val tekstPolje = view.findViewById<TextView>(R.id.tekstPitanja)
        tekstPolje.text = pitanje.tekstPitanja
        odgovori= view.findViewById(R.id.odgovoriLista)
        odgovori.adapter = object : ArrayAdapter<String>(activity as MainActivity,R.layout.item_odgovor,pitanje.opcije.split(",")){
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val pogled = super.getView(position, convertView, parent)
                if(pokusajViewModel.daLiJeOdgovorio(pitanje)){
                   if(pokusajViewModel.dajOdgovor(pitanje)==position && pokusajViewModel.dajOdgovor(pitanje)==pitanje.tacan){
                       (pogled as TextView).setTextColor(zelenaBoja)
                   }else if(pokusajViewModel.dajOdgovor(pitanje)==position){
                       (pogled as TextView).setTextColor(crvenaBoja)
                   }else if(pitanje.tacan==position){
                       (pogled as TextView).setTextColor(zelenaBoja)
                   }else{
                       (pogled as TextView).setTextColor(crnaBoja)
                   }
                }
                return pogled
            }
        }
        odgovori.onItemClickListener = itemClickedListener
        return view
    }

    companion object {

        fun newInstance(pitanje: Pitanje) = FragmentPitanje(pitanje)
    }
}