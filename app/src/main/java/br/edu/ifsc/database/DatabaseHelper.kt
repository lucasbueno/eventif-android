package br.edu.ifsc.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import br.edu.ifsc.eventos.entities.Event

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASENAME, null, DATABASEVERSION) {

    companion object {
        private val DATABASENAME = "eventif.db"
        private val DATABASEVERSION = 1
    }

    override fun onCreate(database: SQLiteDatabase?) {
        val query = "CREATE TABLE EVENT (id TEXT, name TEXT, description TEXT, contactInfo TEXT)"
        database!!.execSQL(query)
    }

    override fun onUpgrade(database: SQLiteDatabase?, p1: Int, p2: Int) {
        drop(database)
        onCreate(database)
    }

    private fun drop(database: SQLiteDatabase?) {
        database!!.execSQL("DROP TABLE IF EXISTS EVENT")
    }

    fun addEvents(events: List<Event>) {
        val database = this.writableDatabase
        drop(database)
        onCreate(database)

        for(event: Event in events) {
            var values = ContentValues()
            values.put("id", event.id)
            values.put("name", event.name)
            values.put("description", event.description)
            values.put("contactInfo", event.contactInfo)
            database.insert("EVENT", null, values)
        }
        database.close()
    }

    fun getEvents(): List<Event> {
        val db = this.writableDatabase
        val list = ArrayList<Event>()
        val cursor: Cursor
        cursor = db.rawQuery("SELECT * FROM EVENT", null)
        if (cursor != null) {
            if (cursor.count > 0) {
                cursor.moveToFirst()
                do {
                    val id = cursor.getString(cursor.getColumnIndex("id"))
                    val name = cursor.getString(cursor.getColumnIndex("name"))
                    val description = cursor.getString(cursor.getColumnIndex("description"))
                    val contactInfo = cursor.getString(cursor.getColumnIndex("contactInfo"))
                    val event = Event(id.toLong(), name, description, contactInfo)
                    list.add(event)
                } while (cursor.moveToNext())
            }
        }
        db.close()
        return list
    }
}