package com.example.myreminder;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    //declare intent
    Intent intent;

    //declare a DBHandler
    DBHandler dbHandler;

    //declare a reminder cursoradapter
    CursorAdapter reminderCursorAdapter;

    //declare a listview
    ListView reminderListView;

    /**
     * This method initializes the action bar and view of the activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //initializes the view and action bar of the activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //initialize dbhandler
        dbHandler = new DBHandler(this, null);
        //initialize listview
        reminderListView = (ListView) findViewById(R.id.reminderListView);

        //initialize cursoradapter
        reminderCursorAdapter = new ReminderList(this, dbHandler.getReminders(), 0);

        //set reminder cursoradapter on listview
        reminderListView.setAdapter(reminderCursorAdapter);

        getSupportActionBar().setSubtitle(reminderListView.getAdapter().getCount() + " Reminders");
    }

    /**
     * Further initializes the action bar.
     * It gets the xml code in the menu resource file and incorporates it into the action bar.
     * @param menu menu resource file for the activity
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * This method gets called when a menu item in the overflow menu is selected and it controls
     * what happens when the menu item is selected.
     *
     * @param item selected menu item in the overflow menu
     * @return true if the menu item is selected, otherwise false.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //get the id of the menu item selected
        switch (item.getItemId()) {
            case R.id.action_home :
                //initialize an intent for the main activity and start it
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_add_reminder :
                //initialize an intent for the AddReminder activity
                intent = new Intent(this, AddReminder.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * This method is called when the add floating action button is clicked.
     * It starts the addreminder activity.
     * @param view
     */
    public void openAddReminder(View view){
        //initializes an intent for the addreminder activity and starts it
        intent = new Intent(this, AddReminder.class);
        startActivity(intent);
    }
}