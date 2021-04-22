package com.example.myreminder

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import java.text.DateFormat
import java.util.*

class AddReminder : AppCompatActivity(), OnItemSelectedListener {
    //declare intent
    //var intent: Intent? = null

    //delcare editTexts
    var nameEditText: EditText? = null
    var textEditText: EditText? = null

    //declare calendar
    var calendar: Calendar? = null
    var prioritySpinner: Spinner? = null
    var currentDate: String? = null

    //declare DBHandler
    var dbHandler: DBHandler? = null
    var priority: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_reminder)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        //initialize edittexts
        nameEditText = findViewById<View>(R.id.nameEditText) as EditText
        textEditText = findViewById<View>(R.id.textEditText) as EditText
        calendar = Calendar.getInstance()
        currentDate = DateFormat.getDateInstance().format(calendar!!.time)
        //initialize dbhandler
        dbHandler = DBHandler(this, null)

        //initialize spinner
        prioritySpinner = findViewById<View>(R.id.prioritySpinner) as Spinner

        //initialize arrayadapter with values in quantities string-array
        //and stylize it with style defined by simple spinner item
        val adapter = ArrayAdapter.createFromResource(this,
                R.array.priority, android.R.layout.simple_spinner_item)

        //further stylize the arrayadapter
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)

        //set the arrayadapter on the spinner
        prioritySpinner!!.adapter = adapter

        //register an onitemselectedlistener to spinner
        prioritySpinner!!.onItemSelectedListener = this
    }

    /**
     * this method further initializes the action bar of the activity.
     * it gets the code (xml) in the menu resource file and incorporates it into the
     * action bar.
     * @param menu menu resource file for the activity
     * @return true
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_add_reminder, menu)
        return true
    }

    /**
     * This method gets called when a menu item in the overflow menu is
     * selected and it controls what happens when the menu item is selected
     * @param item selected menu item in the overflow menu
     * @return true if menu item is selected, else false
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //get the id of the menu item selected
        return when (item.itemId) {
            R.id.action_home -> {
                // initialize an intent for the main activity and start it
                intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_add_reminder -> {
                // initialize an intent for the main activity and start it
                intent = Intent(this, AddReminder::class.java)
                startActivity(intent)
                true
            }
            R.id.action_view_high_priority -> {
                //initialize an intent for the viewhighprio activity
                intent = Intent(this, ViewHighPrio::class.java)
                startActivity(intent)
                true
            }
            R.id.action_view_medium_priority -> {
                //initialize an intent for the viewmedprio activity
                intent = Intent(this, ViewMedPrio::class.java)
                startActivity(intent)
                true
            }
            R.id.action_view_low_priority -> {
                //initialize an intent for the viewlowprio activity
                intent = Intent(this, ViewLowPrio::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * This method gets called when the add button in the action bar gets clicked
     * @param menuItem add list menu item
     */
    fun addReminder(menuItem: MenuItem?) {
        //get data input in editTexts and store it in Strings
        val name = nameEditText!!.text.toString()
        val text = textEditText!!.text.toString()
        val date = currentDate

        //trim strings and see if they are equal to empty string
        if (name.trim { it <= ' ' } == "" || text.trim { it <= ' ' } == "" || date!!.trim { it <= ' ' } == "" || priority!!.trim { it <= ' ' } == "") {
            //display toast
            Toast.makeText(this, "Please enter a title, text and priority!", Toast.LENGTH_LONG).show()
        } else {
            //add list to DB
            dbHandler!!.addAReminder(name, text, date, priority)
            //display Toast
            Toast.makeText(this, "Reminder created!", Toast.LENGTH_LONG).show()
        }
    }

    /**
     * This method gets called when an item in the spinner is selected
     * @param parent Spinner adapterview
     * @param view add item view
     * @param position position of item that was selected in the spinner
     * @param id database id of the selected item in the spinner
     */
    override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        priority = parent.getItemAtPosition(position).toString()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}
}