package br.edu.ifsc.eventos.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import br.edu.ifsc.database.DatabaseHelper
import br.edu.ifsc.eventos.R
import br.edu.ifsc.eventos.entities.Event
import br.edu.ifsc.eventos.services.RetrofitInitializer
import kotlinx.android.synthetic.main.activity_select_event.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SelectEventActivity : AppCompatActivity() {
    var events = mutableListOf<Event>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_event)

        updateEventList()
        eventSpinner.onItemSelectedListener = eventSpinnerListener
    }

    override fun onResume() {
        super.onResume()
        eventSpinner.setSelection(0)
    }

    private fun updateEventList() {
        events.clear()
        val database = DatabaseHelper(applicationContext)
        val call = RetrofitInitializer().eventsService().getEvents()

        call.enqueue(object : Callback<List<Event>?> {
            override fun onResponse(call: Call<List<Event>?>?,
                                    response: Response<List<Event>?>?) {
                response?.body()?.let(events::addAll)

                if (events.isEmpty()) {
                    events.add(Event(0, getString(R.string.none_available_event), "", ""))
                }else {
                    database.addEvents(events)
                    events.add(0, Event(0, getString(R.string.select_event), "", ""))
                }
                updateEventSpinner(events)
            }

            override fun onFailure(call: Call<List<Event>?>?,
                                   t: Throwable?) {
                events.addAll(database.getEvents())
                if(events.isEmpty())
                    events.add(Event(0, getString(R.string.none_available_event), "", ""))
                else
                    events.add(0, Event(0, getString(R.string.select_event), "", ""))
                updateEventSpinner(events)
            }
        })
    }

    private fun updateEventSpinner(events: List<Event>) {
        val eventList = mutableListOf<String>()
        for (event in events) eventList.add(event.name)
        eventSpinner.adapter = ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item,
                eventList)
    }

    private val eventSpinnerListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            if (position != 0) {
                val eventID = events[position].id.toString()
                val eventName = events[position].name
                val eventDescription = events[position].description
                val contactInfo = events[position].contactInfo
                val intent = Intent(view?.context, EventActivity::class.java)
                intent.putExtra("eventID", eventID)
                intent.putExtra("eventName", eventName)
                intent.putExtra("eventDescription", eventDescription)
                intent.putExtra("contactInfo", contactInfo)
                startActivity(intent)
            }
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {}
    }
}


