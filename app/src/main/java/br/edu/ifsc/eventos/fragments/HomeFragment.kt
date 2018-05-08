package br.edu.ifsc.eventos.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.edu.ifsc.eventos.R
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
            return inflater!!.inflate(R.layout.fragment_home, container, false)
    }

    override fun onResume() {
        super.onResume()
        textViewEventDescription.text = arguments.getString("eventDescription")
        textViewContactInfo.text = arguments.getString("contactInfo")
    }
}



