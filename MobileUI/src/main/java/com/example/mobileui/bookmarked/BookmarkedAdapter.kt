package com.example.mobileui.bookmarked

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.mobileui.R
import com.example.mobileui.model.Project
import javax.inject.Inject

class BookmarkedAdapter @Inject constructor(): RecyclerView.Adapter<BookmarkedAdapter.ViewHolder>(){

    var bookmarkedProjects: List<Project> = arrayListOf()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val itemView = LayoutInflater.from(p0.context)
            .inflate(R.layout.item_bookmarked_project, p0, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return bookmarkedProjects.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        val project = bookmarkedProjects[pos]
        holder.ownerNameText.text = project.ownerName
        holder.projectNameText.text = project.fullName

        Glide.with(holder.itemView.context)
            .load(project.ownerAvatar)
            .apply(RequestOptions.circleCropTransform())
            .into(holder.avatarImage)
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        var avatarImage: ImageView
        var ownerNameText: TextView
        var projectNameText: TextView

        init {
            avatarImage = view.findViewById(R.id.image_owner_avatar)
            ownerNameText = view.findViewById(R.id.text_owner_name)
            projectNameText = view.findViewById(R.id.text_project_name)
        }
    }
}