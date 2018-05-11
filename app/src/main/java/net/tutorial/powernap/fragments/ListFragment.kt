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
import net.tutorial.powernap.utils.Track

class ListFragment : Fragment(), ViewHolderListener {
    private val TAG = "ListFragment";

    private val tracks = ArrayList<Track>()
    private lateinit var callbackFragment: FragmentListener

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ) : View? = inflater.inflate(R.layout.fragment_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initTrackList()
        recycler_view.layoutManager = LinearLayoutManager(this.context)
        recycler_view.adapter = MusicRecyclerAdapter(tracks, this);
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

    private fun initTrackList() {
        tracks.add(Track("Increase Creativity", "Выход из спящего состояния (7Гц)\n" +
                "в креативное состояние (13Гц)"))
        tracks.add(Track("Meditation", "Поддержание спокойного состояния (7Гц)"))
        tracks.add(Track("Mental refresher alpha", "Быстрый способ resresh yourself mentally.\n" +
                "Переносит мозговую активность из бета-1\n" +
                "в альфа"))
        tracks.add(Track("Mental refresher beta", "Быстрый способ refresh yourself mentally.\n" +
                "Переносит мозговую активность к 10 Гц\n" +
                "и возвращается к 15 Гц"))
        tracks.add(Track("Relaxation", "Помогает расслабиться вовремя бета-активности.\n" +
                "Переводит из активности 15 Гц в 12 Гц\n" +
                "и под конец возвращается обратно в 15 Гц"))
        tracks.add(Track("Sleep induction", "Тихонько понижает мозговую активность до 3 Гц"))
    }
}
