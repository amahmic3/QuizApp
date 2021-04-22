package ba.etf.rma21.projekat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import ba.etf.rma21.projekat.data.models.Grupa


class FragmentPoruka(private var poruka:String) : Fragment() {
    lateinit var tekst:TextView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_poruka, container, false)
        tekst = view.findViewById(R.id.tvPoruka)
        tekst.text = poruka
        return view
    }
    
    companion object {
        fun newInstance(poruka: String) = FragmentPoruka(poruka)
    }
}