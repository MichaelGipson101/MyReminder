package com.example.myreminder

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import android.widget.TextView

/**
 * The HighPriorities class will map the data from the reminder table in the cursor
 * to the li_hp_reminder_list resource file
 */
class HighPriorities
/**
 * Initialize a highPriorities cursoradapter
 * @param context reference to the acctivity that initializes the highpriorities cursoradapter
 * @param c reference to the cursor that contains the data selected from the reminder table
 * @param flags determines special behavior of the cursoradapter, will always be 0
 */
(context: Context?, c: Cursor?, flags: Int) : CursorAdapter(context, c, flags) {
    /**
     * Make a new view to hold the data in the cursor
     * @param context reference to the acctivity that initializes the highpriorities cursoradapter
     * @param cursor reference to the cursor that contains the data selected from the reminder table
     * @param parent reference to highprioritylistview that will contain the new view created by this method
     * @return reference to the new table
     */
    override fun newView(context: Context, cursor: Cursor, parent: ViewGroup): View {
        return LayoutInflater.from(context).inflate(R.layout.li_hp_reminder_list, parent, false)
    }

    /**
     * Binds new view to data in cursor
     * @param view refrerence to new view
     * @param context reference to the acctivity that initializes the highpriorities cursoradapter
     * @param cursor reference to the cursor that contains the data selected from the reminder table
     */
    override fun bindView(view: View, context: Context, cursor: Cursor) {
        (view.findViewById<View>(R.id.nameTextView) as TextView).text = cursor.getString(cursor.getColumnIndex("name"))
        (view.findViewById<View>(R.id.dateTextView) as TextView).text = cursor.getString(cursor.getColumnIndex("date"))
    }
}