package ba.etf.rma21.projekat

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.core.view.iterator
import androidx.fragment.app.Fragment
import ba.etf.rma21.projekat.data.repositories.AccountRepository
import ba.etf.rma21.projekat.data.repositories.DBRepository
import ba.etf.rma21.projekat.data.repositories.OdgovorRepository
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    lateinit var bottomNavigation: BottomNavigationView
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
                val fragmentPoruka = FragmentPoruka(Korisnik.pokusajViewModel.predajKviz())
                promijeniMenu()
                postaviFragment(fragmentPoruka,"poruka")
                return@OnNavigationItemSelectedListener true
            }
            R.id.zaustaviKviz->{
                promijeniMenu()
                bottomNavigation.selectedItemId = R.id.kvizovi
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
        AccountRepository.setContext(_context = applicationContext)
        DBRepository.setContext(applicationContext)
        OdgovorRepository.setContext(applicationContext)

        obradiIntent(intent)

    }

    override fun onResume() {
        super.onResume()
        bottomNavigation.selectedItemId = R.id.kvizovi
  }
    fun obradiIntent(intent: Intent){
        val hash: String? = intent.getStringExtra("payload")

        if(hash!=null){
            CoroutineScope(Job()).launch {
                AccountRepository.postaviHash(hash)
            }
        }else{
            CoroutineScope(Job()).launch {
                AccountRepository.postaviHash(AccountRepository.getHash())
            }
        }
    }

}

