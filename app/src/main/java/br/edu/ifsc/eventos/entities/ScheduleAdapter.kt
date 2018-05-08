package br.edu.ifsc.eventos.entities

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.view.View
import android.view.ViewGroup
import android.content.Context
import android.view.LayoutInflater
import br.edu.ifsc.eventos.R
import kotlinx.android.synthetic.main.item_talk.view.*


class ScheduleAdapter(val talks: List<Talk>, val context: Context) : Adapter<ScheduleAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_talk, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return talks.size
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.let {
            it.bindView(talks[position])
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(talk: Talk) {
            itemView.talkTitle.text = talk.name
            itemView.talkSpeaker.text = talk.speaker
            itemView.talkTime.text = talk.time
        }
    }
}