package ba.etf.rma21.projekat

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.core.view.iterator
import androidx.fragment.app.Fragment
import ba.etf.rma21.projekat.viewmodel.PokusajViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    lateinit var bottomNavigation: BottomNavigationView
    var pokusajViewModel = PokusajViewModel()
    fun promijeniMenu(){
        for(menu: MenuItem in bottomNavigation.menu){
            menu.isVisible=!menu.isVisible
        }
    }

    private val menuOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { odabraniItem ->
        when(odabraniItem.itemId){
            R.id.kvizovi ->{
                val kvizFragment = FragmentKvizovi.newInstance()
                postaviFragment(kvizFragment,"kvizovi")
                return@OnNavigationItemSelectedListener true
            }
            R.id.predmeti -> {
                val predmetFragment = FragmentPredmeti.newInstance()
                postaviFragment(predmetFragment,"predmeti")
                return@OnNavigationItemSelectedListener true
            }
            R.id.predajKviz -> {
                val fragmentPoruka = FragmentPoruka(pokusajViewModel.predajKviz())
                promijeniMenu()
                postaviFragment(fragmentPoruka,"poruka")
                return@OnNavigationItemSelectedListener true
            }
            R.id.zaustaviKviz->{
                val kvizFragment = FragmentKvizovi.newInstance()
                promijeniMenu()
                postaviFragment(kvizFragment,"kvizovi")
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

     fun postaviFragment(fragment: Fragment,tag:String) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container,fragment,tag)
            transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount==0){
            super.onBackPressed()
        }else {
            if(!bottomNavigation.menu[0].isVisible) promijeniMenu()
            while(supportFragmentManager.backStackEntryCount!=1) supportFragmentManager.popBackStackImmediate()
           if(bottomNavigation.selectedItemId != R.id.kvizovi) bottomNavigation.selectedItemId = R.id.kvizovi
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavigation = findViewById(R.id.bottomNav)
        bottomNavigation.setOnNavigationItemSelectedListener(menuOnNavigationItemSelectedListener)
        bottomNavigation.selectedItemId = R.id.kvizovi
        bottomNavigation.menu[2].isVisible=false
        bottomNavigation.menu[3].isVisible=false
    }

    override fun onResume() {
        super.onResume()
        bottomNavigation.selectedItemId = R.id.kvizovi
  }


}

