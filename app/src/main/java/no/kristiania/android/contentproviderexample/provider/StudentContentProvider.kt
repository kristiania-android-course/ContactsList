package no.kristiania.android.contentproviderexample.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import no.kristiania.android.contentproviderexample.db.StudentDAO
import java.lang.IllegalArgumentException


/**
 *
 *
 * Created by:  Arun Pillai
 * Email: arun.vijayan.pillai@shortcut.no
 *
 * Date: 26 March 2020
 */

class StudentContentProvider : ContentProvider(){

    private lateinit var studentDAO: StudentDAO


    private val Get_All_Data = 1
    private val Get_Single_Data: Int = 2


    companion object {
        private const val AUTHORITY = "no.kristiania.android.contentproviderexample.provider.StudentContentProvider"
        val URL = "content://$AUTHORITY/STUDENT_TABLE"
        val CONTENT_URI = Uri.parse(URL)
    }

    private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
        /*
         * The calls to addURI() go here, for all of the content URI patterns that the provider
         * should recognize. For this snippet, only the calls for table 3 are shown.
         */

        /*
         * Sets the integer value for multiple rows in STUDENT_TABLE to 1. Notice that no wildcard is used
         * in the path
         */
        addURI("no.kristiania.android.contentproviderexample.provider.StudentContentProvider", "STUDENT_TABLE", Get_All_Data)
        //addURI("no.kristiania.android.contentproviderexample.provider.StudentContentProvider", "STUDENT_TABLE/#", Get_Single_Data)


    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val id =  values?.let {
            studentDAO.insert(it)
        } ?: kotlin.run {
            throw IllegalArgumentException("No ContentValues available")
        }
        return Uri.parse("STUDENT_TABLE/$id")
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        val type = sUriMatcher.match(uri)

        return when (type) {
            Get_All_Data -> {
                studentDAO.fetchAllRecodeAsCursor()
            }
            Get_Single_Data -> {
                uri.lastPathSegment?.toLong()?.let {
                    studentDAO.getRecordWithID(it)
                }
            }
            else -> {
                null
            }
        }
    }

    override fun onCreate(): Boolean {
        studentDAO = StudentDAO(context!!)
        return true
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        TODO("Not yet implemented")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun getType(uri: Uri): String? {
        TODO("Not yet implemented")
    }

}