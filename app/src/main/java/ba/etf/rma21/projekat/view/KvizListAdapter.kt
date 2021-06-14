package ba.etf.rma21.projekat.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma21.projekat.R
import ba.etf.rma21.projekat.data.models.Datum
import ba.etf.rma21.projekat.data.models.Kviz
import java.util.*

@Suppress("DEPRECATION")
class KvizListAdapter(var kvizovi: List<Kviz>, private val onItemClicked: (kviz:Kviz) -> Unit):RecyclerView.Adapter<KvizListAdapter.KvizViewHolder>() {
    private lateinit var trenutniDatum :Date

    inner class KvizViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val nazivKviza:TextView = itemView.findViewById(R.id.nazivKviza)
        val nazivPredmeta:TextView = itemView.findViewById(R.id.nazivPredmeta)
        val datum:TextView = itemView.findViewById(R.id.datumKviza)
        val trajanjeKviza:TextView = itemView.findViewById(R.id.trajanjeKviza)
        val brojOsvojenihPoena:TextView = itemView.findViewById(R.id.brojOsvojenihPoena)
        val stanjeKviza:ImageView = itemView.findViewById(R.id.stanjeKviza)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KvizViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_kviz, parent, false)
        return KvizViewHolder(view)
    }

    override fun getItemCount(): Int {
        return kvizovi.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: KvizViewHolder, position: Int) {
        trenutniDatum = Date()
        holder.nazivPredmeta.text = kvizovi[position].naziviPredmeta
        holder.nazivKviza.text = kvizovi[position].naziv
        holder.trajanjeKviza.text = kvizovi[position].trajanje.toString()+" min"
        holder.itemView.setOnClickListener {
            onItemClicked(kvizovi[position])
        }
        if(kvizovi[position].osvojeniBodovi==null && Datum.before(kvizovi[position].datumKraj!!,Datum.dajDatumBezVremena())){
            holder.stanjeKviza.setImageResource(R.drawable.crvena)
            holder.brojOsvojenihPoena.text =""
            holder.datum.text = kvizovi[position].datumKraj!!
        }else if(kvizovi[position].osvojeniBodovi!=null){
            holder.stanjeKviza.setImageResource(R.drawable.plava)
            holder.brojOsvojenihPoena.text = kvizovi[position].osvojeniBodovi.toString()
            holder.datum.text =kvizovi[position].datumRada!!
        }else if(Datum.after(kvizovi[position].datumPocetka!!,Datum.dajDatumBezVremena())){
            holder.stanjeKviza.setImageResource(R.drawable.zuta)
            holder.brojOsvojenihPoena.text =""
            holder.datum.text =kvizovi[position].datumPocetka!!
        }else{
            holder.stanjeKviza.setImageResource(R.drawable.zelena)
            holder.brojOsvojenihPoena.text =""
            holder.datum.text = kvizovi[position].datumKraj!!
        }

    }
}