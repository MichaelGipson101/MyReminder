package com.example.myreminder

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import com.example.myreminder.DBHandler
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.myreminder.R
import com.example.myreminder.ReminderList
import android.widget.AdapterView.OnItemClickListener
import android.widget.AdapterView
import android.widget.CursorAdapter
import android.widget.ListView
import androidx.appcompat.widget.Toolbar
import com.example.myreminder.ViewReminder
import com.example.myreminder.MainActivity
import com.example.myreminder.AddReminder
import com.example.myreminder.ViewHighPrio
import com.example.myreminder.ViewMedPrio
import com.example.myreminder.ViewLowPrio

class MainActivity : AppCompatActivity() {
    //declare intent
    //var intent: Intent? = null

    //declare a DBHandler
    var dbHandler: DBHandler? = null

    //declare a reminder cursoradapter
    var reminderCursorAdapter: CursorAdapter? = null

    //declare a listview
    var reminderListView: ListView? = null

    /**
     * This method initializes the action bar and view of the activity
     * @param savedInstanceState
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        //initializes the view and action bar of the activity
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        //initialize dbhandler
        dbHandler = DBHandler(this, null)
        //initialize listview
        reminderListView = findViewById<View>(R.id.reminderListView) as ListView

        //initialize cursoradapter
        reminderCursorAdapter = ReminderList(this, dbHandler!!.reminders, 0)

        //set reminder cursoradapter on listview
        reminderListView!!.adapter = reminderCursorAdapter
        supportActionBar!!.subtitle = reminderListView!!.adapter.count.toString() + " Reminders"

        //register an onitemclicklistener to the listview
        reminderListView!!.onItemClickListener = OnItemClickListener { parent, view, position, id ->
            /**
             * This method gets called when an item in the listview is clicked
             * @param parent item list view
             * @param view viewreminder activity view
             * @param position position of clicked item
             * @param id database id of clicked item
             */
            /**
             * This method gets called when an item in the listview is clicked
             * @param parent item list view
             * @param view viewreminder activity view
             * @param position position of clicked item
             * @param id database id of clicked item
             */

            //initialize intent for viewreminder activity
            intent = Intent(this@MainActivity, ViewReminder::class.java)

            //put the database id in the intent
            intent!!.putExtra("_id", id)

            //start the viewreminder activity
            startActivity(intent)
        }
    }

    /**
     * Further initializes the action bar.
     * It gets the xml code in the menu resource file and incorporates it into the action bar.
     * @param menu menu resource file for the activity
     * @return true
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
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
            R.id.action_view_high_priority -> {
                //initialize an intent for the AddReminder activity
                intent = Intent(this, ViewHighPrio::class.java)
                startActivity(intent)
                true
            }
            R.id.action_view_medium_priority -> {
                //initialize an intent for the AddReminder activity
                intent = Intent(this, ViewMedPrio::class.java)
                startActivity(intent)
                true
            }
            R.id.action_view_low_priority -> {
                //initialize an intent for the AddReminder activity
                intent = Intent(this, ViewLowPrio::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * This method is called when the add floating action button is clicked.
     * It starts the addreminder activity.
     * @param view
     */
    fun openAddReminder(view: View?) {
        //initializes an intent for the addreminder activity and starts it
        intent = Intent(this, AddReminder::class.java)
        startActivity(intent)
    }
}