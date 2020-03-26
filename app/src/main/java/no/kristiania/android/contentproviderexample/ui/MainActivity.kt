package no.kristiania.android.contentproviderexample.ui

import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import no.kristiania.android.contentproviderexample.R
import no.kristiania.android.contentproviderexample.Student
import no.kristiania.android.contentproviderexample.db.StudentTable
import no.kristiania.android.contentproviderexample.provider.StudentContentProvider


class MainActivity : AppCompatActivity() {


    private lateinit var loaderManagerInstance: LoaderManager
    private lateinit var adapter: StudentAdapter
    private val studentLoader: LoaderManager.LoaderCallbacks<Cursor> = object : LoaderManager.LoaderCallbacks<Cursor> {
        override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {

            val projectionFields = arrayOf(
                StudentTable.COLUMN_ID,
                StudentTable.COLUMN_NAME)

            return CursorLoader(this@MainActivity,
                StudentContentProvider.CONTENT_URI,  // URI
                projectionFields,  // projection fields
                null,  // the selection criteria
                null,  // the selection args
                null // the sort order
            )
        }

        override fun onLoadFinished(loader: Loader<Cursor>, cursor: Cursor) {
            // The swapCursor() method assigns the new Cursor to the adapter
            val list = mutableListOf<Student>()
            while (cursor.moveToNext()){
                list.add(
                    Student(
                        cursor.getLong(cursor.getColumnIndex(StudentTable.COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(StudentTable.COLUMN_NAME))
                    )
                )
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
        setContentView(R.layout.activity_main)

        loaderManagerInstance = LoaderManager.getInstance(this)

        adapter = StudentAdapter(
            this,
            mutableListOf()
        ) {
            Toast.makeText(this, it.name, Toast.LENGTH_SHORT).show()

        }

        students_recycler_view.layoutManager = GridLayoutManager(this, 1)
        students_recycler_view.adapter = adapter

        // OnClick listener to button
        btn_save.setOnClickListener {

            val values = ContentValues()
            values.put(
                StudentTable.COLUMN_NAME,
                edit_name.text.toString()
            )

            val uri: Uri? = contentResolver.insert(
                StudentContentProvider.CONTENT_URI, values
            )

            Toast.makeText(getBaseContext(),
                uri.toString(), Toast.LENGTH_LONG).show();

            loaderManagerInstance.restartLoader(222, Bundle.EMPTY, studentLoader)

            /*// Get value from the edit text (edit_name)
            val name = edit_name.text.toString()
            // Save it to DB
            val id = studentDAO.insert(name)
            // Show what is stored
            Toast.makeText(this, "stored $name in row $id ", Toast.LENGTH_SHORT).show()
*/
        }

        loaderManagerInstance.initLoader(11, Bundle.EMPTY, studentLoader)


    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        // Do close the db after use.
        //studentDAO.close()
    }
}
