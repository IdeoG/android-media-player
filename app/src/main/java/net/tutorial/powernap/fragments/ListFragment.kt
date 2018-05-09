package net.tutorial.powernap.fragments


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import kotlinx.android.synthetic.main.fragment_list.*
import net.tutorial.powernap.R
import net.tutorial.powernap.interfaces.FragmentListener
import net.tutorial.powernap.interfaces.ViewHolderListener
import net.tutorial.powernap.utils.MusicRecyclerAdapter

class ListFragment : Fragment(), ViewHolderListener {
    private val TAG = "ListFragment";

    val music = ArrayList<String>()
    private lateinit var callbackFragment: FragmentListener
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this.context)

        music.add("Alpha")
        music.add("Beta")

        val adapter = MusicRecyclerAdapter(music, this);
        recyclerView.adapter = adapter

        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if(context is FragmentListener){
            Log.i(TAG, "onAttach: FragmentListener has triggered");
            callbackFragment = context
        }
    }


    override fun viewholderCallback(message: String, position: Int) {
        when(message) {
            "Item Click Callback" -> callbackFragment.fragmentCallback(
                    "ListFragment Button Clicked",
                    position
            )
        }
    }
}
