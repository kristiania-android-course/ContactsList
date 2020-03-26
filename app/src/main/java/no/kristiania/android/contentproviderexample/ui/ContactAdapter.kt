package no.kristiania.android.contentproviderexample.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.student_item.view.*
import no.kristiania.android.contentproviderexample.R


/**
 *
 *
 * Created by:  Arun Pillai
 * Email: arun.vijayan.pillai@shortcut.no
 *
 * Date: 26 March 2020
 */
class ContactAdapter(val context: Context,
                     private val list: MutableList<String>,
                     val clickLambda: (String) -> Unit) :
    RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.student_item, null)
        val holder =
            ContactViewHolder(
                view
            )
        holder.itemView.setOnClickListener {
            clickLambda(list[holder.adapterPosition])
        }
        return holder
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.itemView.student_name.text = list[position]

    }

    fun setData(list: List<String>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }
}