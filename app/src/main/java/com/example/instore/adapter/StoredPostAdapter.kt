package com.example.instore.adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.media.ThumbnailUtils
import android.net.Uri
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instore.R
import java.io.File

class StoredPostAdapter(private var posts: List<File>, private val context: Context): RecyclerView.Adapter<StoredPostAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.post_layout , parent , false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = posts.get(position)

        holder.postName.setText(post.name)

        if(post.absolutePath.endsWith(".mp4")){
            val bitmap = ThumbnailUtils.createVideoThumbnail(post.absolutePath, MediaStore.Video.Thumbnails.MINI_KIND)
            holder.image.setImageBitmap(bitmap)
        }
        else{
            val bitmap = BitmapFactory.decodeFile(post.absolutePath)
            holder.image.setImageBitmap(bitmap)
        }
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val image = itemView.findViewById<ImageView>(R.id.post_image)
        val postName = itemView.findViewById<TextView>(R.id.post_name)
    }
}