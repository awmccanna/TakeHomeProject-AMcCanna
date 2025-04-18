package com.example.openeyetakehome_amccanna.feature.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.openeyetakehome_amccanna.R
import com.example.openeyetakehome_amccanna.core.model.Post
import com.example.openeyetakehome_amccanna.feature.main.detail.PostDetailActivity

class PostRecyclerViewAdapter(private var posts: List<Post>) :
    RecyclerView.Adapter<PostRecyclerViewAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvPostTitle: TextView = itemView.findViewById(R.id.postTitle)
        val tvPostBody: TextView = itemView.findViewById(R.id.postBody)
        val iconButton: ImageButton = itemView.findViewById(R.id.postIconButton)
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
        holder.iconButton.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, PostDetailActivity::class.java)
            intent.putExtra("postId", post.id)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int = posts.size

    fun setInitialData(newPosts: List<Post>) {
        posts = newPosts
        notifyDataSetChanged()
    }
}