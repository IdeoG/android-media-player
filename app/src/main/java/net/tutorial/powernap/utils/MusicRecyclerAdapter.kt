package net.tutorial.powernap.utils

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import net.tutorial.powernap.R
import net.tutorial.powernap.interfaces.ViewHolderListener
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_music.*


class MusicRecyclerAdapter(private val tracks: ArrayList<Track>,
                           private val vhListener: ViewHolderListener): RecyclerView.Adapter<ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tracks[position])
    }

    override fun getItemCount(): Int = tracks.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_music, parent, false)
        return ViewHolder(view, vhListener, view)
    }

}

class ViewHolder(view: View?, var vhListener: ViewHolderListener, override val containerView: View?) :
        RecyclerView.ViewHolder(view), View.OnClickListener, LayoutContainer {

    fun bind(track: Track) {
        music_title.text = track.title
        music_subtitle.text = track.subtitle
        layout_item.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        vhListener.viewholderCallback("Item Click Callback", adapterPosition)
    }
}
