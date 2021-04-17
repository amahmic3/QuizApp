package ba.etf.rma21.projekat

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma21.projekat.view.KvizListAdapter
import ba.etf.rma21.projekat.viewmodel.GrupaViewModel
import ba.etf.rma21.projekat.viewmodel.KvizViewModel
import ba.etf.rma21.projekat.viewmodel.PredmetViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {


    private var godina: Int = 1;

    private lateinit var bottomNavigation: BottomNavigationView
    private val menuOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { odabraniItem ->
        when(odabraniItem.itemId){
            R.id.kvizovi ->{
                val kvizFragment = FragmentKvizovi.newInstance()
                postaviFragment(kvizFragment,"kvizovi")
                return@OnNavigationItemSelectedListener true
            }
            R.id.predmeti -> {
                val predmetFragment = FragmentPredmeti.newInstance(godina)
                postaviFragment(FragmentPredmeti.predmeti,"predmeti")
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun postaviFragment(fragment: Fragment,tag:String) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container,fragment,tag)
            transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount==0){
            super.onBackPressed()
        }else while(supportFragmentManager.backStackEntryCount!=1) supportFragmentManager.popBackStackImmediate()


    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavigation = findViewById(R.id.bottomNav)
        bottomNavigation.setOnNavigationItemSelectedListener(menuOnNavigationItemSelectedListener)
        bottomNavigation.selectedItemId = R.id.kvizovi

    }

    override fun onResume() {
        super.onResume()
        bottomNavigation.selectedItemId = R.id.kvizovi
  }


}

