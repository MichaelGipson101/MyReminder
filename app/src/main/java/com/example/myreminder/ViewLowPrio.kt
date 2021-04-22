package com.example.myreminder

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.CursorAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class ViewLowPrio : AppCompatActivity() {
    //declare intent
   // var intent: Intent? = null

    //declare a DBHandler
    var dbHandler: DBHandler? = null

    //declare a reminder cursoradapter
    var lowPriorityCursorAdapter: CursorAdapter? = null

    //declare a listview
    var lowPriorityListView: ListView? = null

    /**
     * This method initializes the action bar and view of the activity
     * @param savedInstanceState
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        //initializes the view and action bar of the activity
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_low_priority)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)


        //initialize dbhandler
        dbHandler = DBHandler(this, null)
        //initialize listview
        lowPriorityListView = findViewById<View>(R.id.lowPriorityListView) as ListView

        //initialize cursoradapter
        lowPriorityCursorAdapter = LowPriorities(this, dbHandler!!.lowPriorityReminders, 0)

        //set reminder cursoradapter on listview
        lowPriorityListView!!.adapter = lowPriorityCursorAdapter
        supportActionBar!!.subtitle = "Low"
    }

    /**
     * Further initializes the action bar.
     * It gets the xml code in the menu resource file and incorporates it into the action bar.
     * @param menu menu resource file for the activity
     * @return true
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_view_low_priority, menu)
        return true
    }

    /**
     * This method gets called when a menu item in the overflow menu is selected and it controls
     * what happens when the menu item is selected.
     *
     * @param item selected menu item in the overflow menu
     * @return true if the menu item is selected, otherwise false.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //get the id of the menu item selected
        return when (item.itemId) {
            R.id.action_home -> {
                //initialize an intent for the main activity and start it
                intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_add_reminder -> {
                //initialize an intent for the AddReminder activity
                intent = Intent(this, AddReminder::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}