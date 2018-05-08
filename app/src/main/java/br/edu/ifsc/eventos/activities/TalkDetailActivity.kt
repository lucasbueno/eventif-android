package br.edu.ifsc.eventos.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import br.edu.ifsc.eventos.R

class TalkDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_talk_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val talkName = intent.getStringExtra("talkName")
        title = talkName
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean  {
        if(item?.itemId == android. R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
