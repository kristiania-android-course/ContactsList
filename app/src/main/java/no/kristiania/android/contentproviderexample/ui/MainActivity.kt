package no.kristiania.android.contentproviderexample.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.loader.app.LoaderManager
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import no.kristiania.android.contentproviderexample.R


class MainActivity : AppCompatActivity() {


    private lateinit var loaderManagerInstance: LoaderManager
    private lateinit var adapter: StudentAdapter




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

    }

    private fun refreshList() {

    }

    private fun addData() {

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
