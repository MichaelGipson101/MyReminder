package com.example.myreminder;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;


public class AddReminder extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    //declare intent
    Intent intent;

    //delcare editTexts
    EditText nameEditText;
    EditText textEditText;

    //declare calendar
    Calendar calendar;

    Spinner prioritySpinner;

    String currentDate;
    //declare DBHandler
    DBHandler dbHandler;

    String priority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //initialize edittexts
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        textEditText = (EditText) findViewById(R.id.textEditText);

        calendar = Calendar.getInstance();
        currentDate = DateFormat.getDateInstance().format(calendar.getTime());
        //initialize dbhandler
        dbHandler = new DBHandler(this, null);

        //initialize spinner
        prioritySpinner = (Spinner) findViewById(R.id.prioritySpinner);

        //initialize arrayadapter with values in quantities string-array
        //and stylize it with style defined by simple spinner item
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.priority, android.R.layout.simple_spinner_item);

        //further stylize the arrayadapter
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        //set the arrayadapter on the spinner
        prioritySpinner.setAdapter(adapter);

        //register an onitemselectedlistener to spinner
        prioritySpinner.setOnItemSelectedListener(this);
    }

    /**
     * this method further initializes the action bar of the activity.
     * it gets the code (xml) in the menu resource file and incorporates it into the
     * action bar.
     * @param menu menu resource file for the activity
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_reminder, menu);
        return true;
    }

    /**
     * This method gets called when a menu item in the overflow menu is
     * selected and it controls what happens when the menu item is selected
     * @param item selected menu item in the overflow menu
     * @return true if menu item is selected, else false
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //get the id of the menu item selected
        switch (item.getItemId()) {
            case R.id.action_home :
                // initialize an intent for the main activity and start it
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_add_reminder :
                // initialize an intent for the main activity and start it
                intent = new Intent(this, AddReminder.class);
                startActivity(intent);
                return true;
            case R.id.action_view_high_priority :
                //initialize an intent for the viewhighprio activity
                intent = new Intent(this, ViewHighPrio.class);
                startActivity(intent);
                return true;
            case R.id.action_view_medium_priority :
                //initialize an intent for the viewmedprio activity
                intent = new Intent(this, ViewMedPrio.class);
                startActivity(intent);
                return true;
            case R.id.action_view_low_priority :
                //initialize an intent for the viewlowprio activity
                intent = new Intent(this, ViewLowPrio.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * This method gets called when the add button in the action bar gets clicked
     * @param menuItem add list menu item
     */
    public void addReminder(MenuItem menuItem) {
        //get data input in editTexts and store it in Strings
        String name = nameEditText.getText().toString();
        String text = textEditText.getText().toString();
        String date = currentDate;

        //trim strings and see if they are equal to empty string
        if (name.trim().equals("") || text.trim().equals("") || date.trim().equals("") || priority.trim().equals("")){
            //display toast
            Toast.makeText(this, "Please enter a title, text and priority!", Toast.LENGTH_LONG).show();
        } else {
            //add list to DB
            dbHandler.addAReminder(name, text, date, priority);
            //display Toast
            Toast.makeText(this, "Reminder created!", Toast.LENGTH_LONG).show();

        }
    }
    /**
     * This method gets called when an item in the spinner is selected
     * @param parent Spinner adapterview
     * @param view add item view
     * @param position position of item that was selected in the spinner
     * @param id database id of the selected item in the spinner
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        priority = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
