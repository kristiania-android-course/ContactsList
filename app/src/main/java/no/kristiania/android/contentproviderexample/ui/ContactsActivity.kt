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
                                    /*ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME ContactsContract.Contacts.CONTENT_URI*/

class ContactsActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<Cursor> {

    private lateinit var adapter: ContactAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts)

        adapter = ContactAdapter(
            this,
            mutableListOf()
        ) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
        contacts_recycler_view.layoutManager = GridLayoutManager(this, 2)
        contacts_recycler_view.adapter = adapter

        LoaderManager.getInstance(this)
            .initLoader(12, Bundle.EMPTY, this)

    }

    override fun onResume() {
        super.onResume()

    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {

        val projecttion =
            arrayOf(ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME)

        return CursorLoader(
            this,
            ContactsContract.Contacts.CONTENT_URI,
            projecttion,
            null,
            null,
            null
        )
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor) {
        val list = mutableListOf<String>()
        while (data.moveToNext()) {
            list.add(data.getString(data.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME)))
        }

        adapter.setData(list)
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        adapter.setData(mutableListOf())
    }
}
