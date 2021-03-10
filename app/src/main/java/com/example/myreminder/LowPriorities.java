package com.example.myreminder;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * The LowPriorities class will map the data from the reminder table in the cursor
 * to the li_lp_reminder_list resource file
 */
public class LowPriorities extends CursorAdapter {
    /**
     * Initialize a lowpriorities cursoradapter
     * @param context reference to the acctivity that initializes the lowpriorities cursoradapter
     * @param c reference to the cursor that contains the data selected from the reminder table
     * @param flags determines special behavior of the cursoradapter, will always be 0
     */
    public LowPriorities(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    /**
     * Make a new view to hold the data in the cursor
     * @param context reference to the acctivity that initializes the lowpriorities cursoradapter
     * @param cursor reference to the cursor that contains the data selected from the reminder table
     * @param parent reference to lowprioritylistview that will contain the new view created by this method
     * @return reference to the new table
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.li_lp_reminder_list, parent, false);
    }
    /**
     * Binds new view to data in cursor
     * @param view refrerence to new view
     * @param context reference to the acctivity that initializes the lowpriorities cursoradapter
     * @param cursor reference to the cursor that contains the data selected from the reminder table
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ((TextView) view.findViewById(R.id.nameTextView)).
                setText(cursor.getString(cursor.getColumnIndex("name")));
        ((TextView) view.findViewById(R.id.dateTextView)).
                setText(cursor.getString(cursor.getColumnIndex("date")));
    }
}