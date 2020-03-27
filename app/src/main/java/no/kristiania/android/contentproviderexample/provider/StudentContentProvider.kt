package no.kristiania.android.contentproviderexample.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import no.kristiania.android.contentproviderexample.db.StudentDAO


/**
 *
 *
 * Created by:  Arun Pillai
 * Email: arun.vijayan.pillai@shortcut.no
 *
 * Date: 26 March 2020
 */

class StudentContentProvider : ContentProvider() {

    private lateinit var studentDao: StudentDAO

    companion object {
        val AUTHORITY: String =
            "no.kristiania.android.contentproviderexample.provider.StudentContentProvider"

        val CONTETNT_URI: Uri = Uri.parse("content://${AUTHORITY}/STUDENT_TABLE")

        val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(AUTHORITY, "STUDENT_TABLE", 1)
            addURI(AUTHORITY, "STUDENT_TABLE/#", 2)
        }


    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val id = studentDao.insert(values!!)
        return Uri.parse(id.toString())
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        val type = uriMatcher.match(uri)
        return if (type == 1) {
            studentDao.fetchAllRecodeAsCursor()
        } else {
            null
        }

    }

    override fun onCreate(): Boolean {
        studentDao = StudentDAO(context!!)
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