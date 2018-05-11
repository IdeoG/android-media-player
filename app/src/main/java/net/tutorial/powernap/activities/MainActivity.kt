package net.tutorial.powernap.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_list.*
import net.tutorial.powernap.R
import net.tutorial.powernap.fragments.ListFragment
import net.tutorial.powernap.fragments.PlayFragment
import net.tutorial.powernap.interfaces.FragmentListener

class MainActivity : AppCompatActivity(), FragmentListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        attachListFragment()

        setSupportActionBar(toolbar)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_down)
    }

    fun attachListFragment() {
        supportActionBar?.setDisplayHomeAsUpEnabled(false);
        supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.slide_in, R.anim.slide_out)
                .replace(R.id.container, ListFragment())
                .commit()
    }

    fun attachPlayFragment(position: Int) {
        supportActionBar?.setDisplayHomeAsUpEnabled(true);

        val args = Bundle()
        args.putInt("position", position)

        val playFragment = PlayFragment()
        playFragment.arguments = args
        supportFragmentManager.beginTransaction()
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                .replace(R.id.container, playFragment)
                .commit()
    }



    override fun fragmentCallback(message: String, position: Int?) {
        when (message) {
            "PlayFragment Button Clicked" -> attachListFragment()
            "ListFragment Button Clicked" -> attachPlayFragment(position!!)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> {
            attachListFragment()
            true
        }

        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }
}
