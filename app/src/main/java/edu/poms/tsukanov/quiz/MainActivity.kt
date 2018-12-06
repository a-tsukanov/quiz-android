package edu.poms.tsukanov.quiz

import android.arch.persistence.room.Room
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import edu.poms.tsukanov.quiz.database.QuizResultsDb
import edu.poms.tsukanov.quiz.fragments.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
import java.lang.RuntimeException
import java.net.URL

class MainActivity :
        AppCompatActivity(),
        NavigationView.OnNavigationItemSelectedListener {

    private lateinit var chooseQuizFragment: ChooseQuizFragment
    private lateinit var dashboardFragment: DashboardFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        db = Room.databaseBuilder(
                    applicationContext,
                    QuizResultsDb::class.java,
                    "results_db")
                .allowMainThreadQueries()
                .build()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        initToggle()

        nav_view.setNavigationItemSelectedListener(this)

        initFragments()

        openFragment(chooseQuizFragment)
    }

    private fun initToggle() {
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
    }

    private fun initFragments() {
        chooseQuizFragment = ChooseQuizFragment()
        dashboardFragment = DashboardFragment()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_about -> {
                val about = Intent(this, AboutActivity::class.java)
                startActivity(about)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun downloadQuizes() {
        toast("Downloading...")
        doAsync {
            val res = try {
                val r = URL("http://10.0.2.2:9595/api/quizes/").readText()
                Log.w("Hello", r)
                r
            }
            catch(e: Exception) {
                Log.e("Hello", "Damn", e)
                throw e
            }
            val frag = DownloadFragment.newInstance(res)
            uiThread {
                openFragment(frag)
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_start_quiz -> openFragment(chooseQuizFragment)
            R.id.nav_dashboard -> openFragment(dashboardFragment)
            R.id.nav_download -> downloadQuizes()
            else -> throw RuntimeException("Unknown item chosen")
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun openFragment(newFragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragments_container, newFragment)
                .addToBackStack(newFragment.toString())
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit()
    }

    companion object {
        lateinit var db: QuizResultsDb
    }

}
