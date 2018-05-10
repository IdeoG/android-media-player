package net.tutorial.powernap.utils

import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import net.tutorial.powernap.R
import net.tutorial.powernap.interfaces.ViewHolderListener


class MusicRecyclerAdapter(private val items: ArrayList<String>,
                           private val vhListener: ViewHolderListener): RecyclerView.Adapter<ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.i("onBindViewHolder", "position = $position, items.size = ${items.size}, text = ${items[position]}")
        holder.title?.text = items[position]
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder{
        val vh = ViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.music_item,
                        parent,
                        false),
                vhListener)
        vh.bind()
        return vh
    }
}

class ViewHolder(view: View?, var vhListener: ViewHolderListener) : RecyclerView.ViewHolder(view), View.OnClickListener {
    val title = view?.findViewById<TextView>(R.id.music_title)
    val layout = view?.findViewById<ConstraintLayout>(R.id.layout_item)

    fun bind() {
        layout?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        vhListener.viewholderCallback("Item Click Callback", adapterPosition)
    }
}
