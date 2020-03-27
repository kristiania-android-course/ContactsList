package no.kristiania.android.contentproviderexample.provider

import android.content.UriMatcher
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

class StudentContentProvider {

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


}