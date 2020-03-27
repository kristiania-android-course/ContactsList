package no.kristiania.android.contentproviderexample.ui

import android.content.ContentValues
import android.database.Cursor
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


class MainActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<Cursor> {


    private lateinit var loaderManagerInstance: LoaderManager
    private lateinit var adapter: StudentAdapter


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
            addData()
            refreshList()
        }

        initListData()


    }

    private fun initListData() {
        loaderManagerInstance.initLoader(11, Bundle.EMPTY, this)
    }

    private fun refreshList() {

    }

    private fun addData() {
        val value = ContentValues()
        value.put(StudentTable.COLUMN_NAME, edit_name.text.toString())
        val uri = contentResolver
            .insert(StudentContentProvider.CONTETNT_URI, value)

        Toast.makeText(this, uri.toString(), Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        // Do close the db after use.
        //studentDAO.close()
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        return CursorLoader(
            this,
            StudentContentProvider.CONTETNT_URI,
            arrayOf(StudentTable.COLUMN_ID, StudentTable.COLUMN_NAME),
            null,
            null,
            null
        )
    }

    override fun onLoadFinished(loader: Loader<Cursor>, cursor: Cursor) {
        val list = mutableListOf<Student>()

        while (cursor.moveToNext()) {
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
        adapter.setData(mutableListOf())
    }
}
