package ba.etf.rma21.projekat

import android.graphics.Color
import android.os.Bundle
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.toSpannable
import androidx.core.view.get
import androidx.core.view.size
import androidx.fragment.app.Fragment
import ba.etf.rma21.projekat.data.models.Pitanje
import ba.etf.rma21.projekat.viewmodel.PokusajViewModel
import com.google.android.material.navigation.NavigationView


class FragmentPokusaj(val pitanja:List<Pitanje>) : Fragment() {

    lateinit var navigacijaPitanja: NavigationView
    val zelenaBoja = Color.parseColor("#3DDC84")
    val crvenaBoja = Color.parseColor("#DB4F3D")
    val crnaBoja = Color.parseColor("#FFFFFF")
    var trenutnoPitanje :Int=0
    lateinit var pokusajViewModel:PokusajViewModel

    fun postaviBoju(redniBroj:Int=trenutnoPitanje){
        val tekst = navigacijaPitanja.menu.get(redniBroj).title.toString().toSpannable()
        if(pokusajViewModel.daLiJeOdgovorio(pitanja.get(redniBroj))){
            if(pokusajViewModel.dajOdgovor(pitanja.get(redniBroj))==pitanja.get(redniBroj).tacan){
                tekst.setSpan(ForegroundColorSpan(zelenaBoja),0,tekst.length,0)
            }else tekst.setSpan(ForegroundColorSpan(crvenaBoja),0,tekst.length,0)
        }else tekst.setSpan(ForegroundColorSpan(crnaBoja),0,tekst.length,0)
        navigacijaPitanja.menu.get(redniBroj).setTitle(tekst)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        pokusajViewModel=(activity as MainActivity).pokusajViewModel
        pokusajViewModel.brPitanja=pitanja.size
        val view = inflater.inflate(R.layout.fragment_pokusaj, container, false)
        navigacijaPitanja = view.findViewById(R.id.navigacijaPitanja)
        for(i in 1..pitanja.size){
            navigacijaPitanja.menu.add(i.toString())
            postaviBoju(i-1)
            navigacijaPitanja.menu.getItem(i-1).setOnMenuItemClickListener {
                trenutnoPitanje=i-1
                val fragment = FragmentPitanje.newInstance(pitanja.get(i-1))
                postaviPitanje(fragment)
                return@setOnMenuItemClickListener true
            }
        }
        if(pokusajViewModel.kvizZavrsen){
            navigacijaPitanja.menu.add("Rezultat")
            navigacijaPitanja.menu.get(navigacijaPitanja.menu.size-1).setOnMenuItemClickListener {
                val fragmentPoruka = FragmentPoruka.newInstance(pokusajViewModel.dajPoruku())
                (activity as MainActivity).postaviFragment(fragmentPoruka,"poruka")
                (activity as MainActivity).promijeniMenu()
                return@setOnMenuItemClickListener true
            }
        }
        return view
    }
    fun postaviPitanje(fragment:Fragment){
     val transakcija= childFragmentManager.beginTransaction()
        transakcija.replace(R.id.framePitanje,fragment)
        transakcija.addToBackStack(null)
        transakcija.commit()
    }
    companion object {


        fun newInstance(pitanja: List<Pitanje>) =FragmentPokusaj(pitanja)


    }
}