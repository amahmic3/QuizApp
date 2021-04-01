package ba.etf.rma21.projekat

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.static.dajFiltere
import ba.etf.rma21.projekat.view.KvizListAdapter
import java.util.*


class MainActivity : AppCompatActivity() {

    var kvizovi:MutableList<Kviz> = mutableListOf(Kviz("Kviz 0","RMA",Date(2020,1,1),Date(2021,5,1),Date(),5,"RI",5.2F),
        Kviz("Kviz 1","IM2",Date(2021,1,1),Date(2021,5,1),Date(),5,"RI",5F));

    private lateinit var kvizkovi:RecyclerView;
    private lateinit var kviskoAdapter:KvizListAdapter;
    private lateinit var filter:Spinner;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        kvizovi.sortWith(Comparator { k1: Kviz, k2: Kviz -> -k1.datumPocetka.compareTo(k2.datumPocetka) });
        filter = findViewById(R.id.filterKvizova);
        filter.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, dajFiltere());
        filter.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }
        kvizkovi=findViewById(R.id.listaKvizova);
        kvizkovi.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        kviskoAdapter = KvizListAdapter(kvizovi);
        kvizkovi.adapter=kviskoAdapter;
    }


}

