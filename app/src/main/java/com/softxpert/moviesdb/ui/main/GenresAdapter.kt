package com.softxpert.moviesdb.ui.main

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.softxpert.moviesdb.R
import com.softxpert.moviesdb.domain.model.GenresModel

class GenresAdapter
    (private val dataSet: ArrayList<GenresModel>, private val listner: ONTypeClicked) :
    RecyclerView.Adapter<GenresAdapter.ViewHolder>() {
    lateinit var context: Context


    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val btnproblem: Button

        init {
            // Define click listener for the ViewHolder's View.
            btnproblem = view.findViewById(R.id.btnproblem)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        context = viewGroup.context
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_genre, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.btnproblem.text = dataSet[position].name
        viewHolder.btnproblem.setOnClickListener {
            for (item in dataSet) {
                item.selected = false
            }
            dataSet[position].selected = true
            listner.onTypeClicked(dataSet[position])
            notifyDataSetChanged()
        }

        if (dataSet[position].selected) {
            viewHolder.btnproblem.setBackgroundColor(
                context.resources.getColor(R.color.purple_700)
            )
            viewHolder.btnproblem.setTextColor(Color.WHITE)
        } else {
            viewHolder.btnproblem.setBackgroundColor(
                context.resources.getColor(R.color.light_grey)
            )
            viewHolder.btnproblem.setTextColor(Color.BLACK)
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

    public interface ONTypeClicked {
        fun onTypeClicked(genresModel: GenresModel)
    }

}