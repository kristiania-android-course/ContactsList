package no.kristiania.android.contentproviderexample.ui

import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_contacts.*
import no.kristiania.android.contentproviderexample.R


class ContactsActivity : AppCompatActivity() {

    // Defines the id of the loader for later reference
    private val CONTACT_LOADER_ID = 78 // From docs: A unique identifier for this loader. Can be whatever you want.


    private lateinit var adapter: ContactAdapter

    // Defines the asynchronous callback for the contacts data loader
    private val contactsLoader: LoaderManager.LoaderCallbacks<Cursor> = object : LoaderManager.LoaderCallbacks<Cursor> {
        override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {

            val projectionFields = arrayOf(ContactsContract.Contacts._ID,
                    ContactsContract.Contacts.DISPLAY_NAME)

            return CursorLoader(this@ContactsActivity,
                    ContactsContract.Contacts.CONTENT_URI,  // URI
                    projectionFields,  // projection fields
                    null,  // the selection criteria
                    null,  // the selection args
                    null // the sort order
            )
        }

        override fun onLoadFinished(loader: Loader<Cursor>, cursor: Cursor) {
            // The swapCursor() method assigns the new Cursor to the adapter
            val list = mutableListOf<String>()
            while (cursor.moveToNext()){
                list.add(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)))
            }
            adapter.setData(list)
        }

        override fun onLoaderReset(loader: Loader<Cursor>) {
            // Clear the Cursor we were using with another call to the swapCursor()
            adapter.setData(mutableListOf());
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts)

        adapter = ContactAdapter(
            this,
            mutableListOf()
        ) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
        contacts_recycler_view.layoutManager = GridLayoutManager(this, 1)
        contacts_recycler_view.adapter = adapter

        // Initialize the loader with a special ID and the defined callbacks from above
        // Initialize the loader with a special ID and the defined callbacks from above
        LoaderManager
                .getInstance(this)
                .initLoader(
                        CONTACT_LOADER_ID,
                        Bundle(),
                        contactsLoader
                )


    }

    override fun onResume() {
        super.onResume()

    }
}
