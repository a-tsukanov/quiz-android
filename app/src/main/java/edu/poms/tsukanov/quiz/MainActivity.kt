package edu.poms.tsukanov.quiz

import android.arch.persistence.room.Room
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import edu.poms.tsukanov.quiz.database.QuizResults
import edu.poms.tsukanov.quiz.database.QuizResultsDb
import edu.poms.tsukanov.quiz.fragments.*
import edu.poms.tsukanov.quiz.passage.QuizPassage
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity :
        AppCompatActivity(),
        NavigationView.OnNavigationItemSelectedListener,
        ChooseQuizFragment.OnFragmentInteractionListener,
        CreateUserFragment.OnFragmentInteractionListener,
        QuizFragment.OnFragmentInteractionListener,
        QuizResultsFragment.OnFragmentInteractionListener {

    lateinit var chooseQuizFragment: ChooseQuizFragment
    lateinit var quizFragment: QuizFragment
    lateinit var quizResultsFragment: QuizResultsFragment
    lateinit var createUserFragment: CreateUserFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        db = Room.databaseBuilder(
                applicationContext,
                QuizResultsDb::class.java,
                "results_db"
        ).build()

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
        val defaultQp = QuizPassage("python", 10, "")

        chooseQuizFragment = ChooseQuizFragment.newInstance()
        quizFragment = QuizFragment.newInstance(defaultQp)
        createUserFragment = CreateUserFragment.newInstance("python", 10)
        quizResultsFragment = QuizResultsFragment.newInstance(defaultQp)
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
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_start_quiz -> {
                openFragment(chooseQuizFragment)
            }
            R.id.nav_dashboard -> {

            }
            R.id.nav_switch_user -> {

            }
            R.id.nav_new_user -> {
                openFragment(createUserFragment)
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onFragmentInteraction(uri: Uri) {

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
