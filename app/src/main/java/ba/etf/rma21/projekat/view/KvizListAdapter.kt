package ba.etf.rma21.projekat.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma21.projekat.R
import ba.etf.rma21.projekat.data.models.Kviz
import java.util.*

@Suppress("DEPRECATION")
class KvizListAdapter(var kvizovi: List<Kviz>):RecyclerView.Adapter<KvizListAdapter.KvizViewHolder>() {
    private lateinit var trenutniDatum :Date;

    inner class KvizViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val nazivKviza:TextView = itemView.findViewById(R.id.nazivKviza);
        val nazivPredmeta:TextView = itemView.findViewById(R.id.nazivPredmeta);
        val datum:TextView = itemView.findViewById(R.id.datumKviza);
        val trajanjeKviza:TextView = itemView.findViewById(R.id.trajanjeKviza)
        val brojOsvojenihPoena:TextView = itemView.findViewById(R.id.brojOsvojenihPoena);
        val stanjeKviza:ImageView = itemView.findViewById(R.id.stanjeKviza);
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KvizViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_kviz, parent, false)
        return KvizViewHolder(view)
    }

    override fun getItemCount(): Int {
        return kvizovi.size;
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: KvizViewHolder, position: Int) {
        trenutniDatum = Date();
        holder.nazivPredmeta.text = kvizovi[position].nazivPredmeta;
        holder.nazivKviza.text = kvizovi[position].naziv;
        holder.trajanjeKviza.text = kvizovi[position].trajanje.toString()+" min";
        if(kvizovi[position].osvojeniBodovi==null && kvizovi[position].datumKraj.before(trenutniDatum)){
            holder.stanjeKviza.setImageResource(R.drawable.crvena);
            holder.brojOsvojenihPoena.text ="";
            holder.datum.text = String.format("%02d.%02d.%d.",kvizovi[position].datumKraj.day,kvizovi[position].datumKraj.month,kvizovi[position].datumKraj.year + 1900);
        }else if(kvizovi[position].osvojeniBodovi!=null){
            holder.stanjeKviza.setImageResource(R.drawable.plava);
            holder.brojOsvojenihPoena.text = kvizovi[position].osvojeniBodovi.toString();
            holder.datum.text = String.format("%02d.%02d.%d.",kvizovi[position].datumRada?.day,kvizovi[position].datumRada?.month,kvizovi[position].datumRada!!.year + 1900);
        }else if(kvizovi[position].datumPocetka.after(trenutniDatum)){
            holder.stanjeKviza.setImageResource(R.drawable.zuta);
            holder.brojOsvojenihPoena.text ="";
            holder.datum.text = String.format("%02d.%02d.%d.",kvizovi[position].datumPocetka.day,kvizovi[position].datumPocetka.month,kvizovi[position].datumPocetka.year + 1900);
        }else{
            holder.stanjeKviza.setImageResource(R.drawable.zelena);
            holder.brojOsvojenihPoena.text ="";
            holder.datum.text = String.format("%02d.%02d.%d.",kvizovi[position].datumKraj.day,kvizovi[position].datumKraj.month,kvizovi[position].datumKraj.year + 1900);
        }

    }
}