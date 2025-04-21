package com.example.openeyetakehome_amccanna.feature.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.openeyetakehome_amccanna.R
import com.example.openeyetakehome_amccanna.core.model.Post

class PostRecyclerViewAdapter(
    private var posts: List<Post>,
    private val onItemClick: (Post) -> Unit
    ) :
    RecyclerView.Adapter<PostRecyclerViewAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvPostTitle: TextView = itemView.findViewById(R.id.postTitle)
        val tvPostBody: TextView = itemView.findViewById(R.id.postBody)
        val cvPost: CardView = itemView.findViewById(R.id.postCard)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val post = posts[position]
        holder.tvPostTitle.text = post.title
        holder.tvPostBody.text = post.body
        holder.cvPost.setOnClickListener {
            onItemClick(post)
        }
    }

    override fun getItemCount(): Int = posts.size

    fun setInitialData(newPosts: List<Post>) {
        posts = newPosts
        notifyDataSetChanged()
    }
}