package br.edu.ifsc.eventos.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import br.edu.ifsc.eventos.R
import br.edu.ifsc.eventos.activities.TalkDetailActivity
import br.edu.ifsc.eventos.entities.ScheduleAdapter
import br.edu.ifsc.eventos.entities.Talk
import br.edu.ifsc.eventos.services.RetrofitInitializer
import kotlinx.android.synthetic.main.fragment_schedule.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ScheduleFragment : Fragment() {
    var talks = mutableListOf<Talk>()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_schedule, container, false)
    }

    override fun onResume() {
        super.onResume()
        val eventID = arguments.getString("eventID")
        val day = arguments.getString("day")
        updateTalks(eventID, day, this.context)

        val layoutManager = LinearLayoutManager(context)
        scheduleRecyclerView.layoutManager = layoutManager
    }

    private fun updateTalks(eventID: String, day: String, context: Context) {
        var call = RetrofitInitializer().eventsService().getEventDayTalks(eventID, day)
        if(day.isNullOrEmpty())
            call = RetrofitInitializer().eventsService().getEventTalks(eventID)

        call.enqueue(object : Callback<List<Talk>?> {
            override fun onResponse(call: Call<List<Talk>?>?, response: Response<List<Talk>?>?) {
                response?.body()?.let(talks::addAll)
                scheduleRecyclerView.adapter = ScheduleAdapter(talks, context)
            }

            override fun onFailure(call: Call<List<Talk>?>?,
                                   t: Throwable?) {
            }
        })
    }

}
