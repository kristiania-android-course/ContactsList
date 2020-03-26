package no.kristiania.android.contentproviderexample.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.student_item.view.*
import no.kristiania.android.contentproviderexample.R
import no.kristiania.android.contentproviderexample.Student


/**
 *
 *
 * Created by:  Arun Pillai
 * Email: arun.vijayan.pillai@shortcut.no
 *
 * Date: 01 March 2020
 */
class StudentAdapter(
    val context: Context,
    val studentList: MutableList<Student>,
    val clickLambda: (Student) -> Unit
) :
    RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {


    class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.student_item, null)
        val holder =
            StudentViewHolder(
                view
            )
        holder.itemView.setOnClickListener {
            clickLambda(studentList[holder.adapterPosition])
        }
        return holder
    }

    override fun getItemCount(): Int {
        return studentList.size
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        holder.itemView.student_name.text = studentList[position].name
        holder.itemView.student_id.text = studentList[position].id.toString()
    }

    fun setData(list: List<Student>) {
        this.studentList.clear()
        this.studentList.addAll(list)
        notifyDataSetChanged()
    }
}