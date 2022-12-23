package com.example.instore.adapter

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.ThumbnailUtils
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instore.R
import java.io.File
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class StoredPostAdapter(private val posts: MutableList<File>, private val context: Context): RecyclerView.Adapter<StoredPostAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.post_layout , parent , false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = posts.get(position)

        holder.postName.setText(post.name)
        holder.options.setOnClickListener(View.OnClickListener {
            Log.d("ABHI", "onBindViewHolder: ${holder.adapterPosition} ")

            showPopup(holder.options , post )
        })

        if(post.absolutePath.endsWith(".mp4")){
            val bitmap = ThumbnailUtils.createVideoThumbnail(post.absolutePath, MediaStore.Video.Thumbnails.MINI_KIND)
            holder.image.setImageBitmap(bitmap)
        }
        else{
            val bitmap = BitmapFactory.decodeFile(post.absolutePath)
            holder.image.setImageBitmap(bitmap)
        }
        val sdt = SimpleDateFormat("dd/MM/yy hh.mm aaa")
        holder.date.text = sdt.format(Date(post.lastModified()))

        holder.size.text = "${getMB(post)} mb | "
    }

    private fun getMB(file: File): String {
        val size = file.length().toDouble() / (1024*1024)
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.DOWN
        return df.format(size).toDouble().toString()
    }

    private fun showPopup(view: View , post : File ) {
        val popupMenu = PopupMenu(view.context , view , Gravity.LEFT)
        popupMenu.inflate(R.menu.stored_post_options_menu)
        popupMenu.show()
        popupMenu.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener{
            override fun onMenuItemClick(p0: MenuItem?): Boolean {
                when(p0?.itemId){
                    R.id.post_delete ->{
                        post.delete()
                        posts.remove(post)
                        notifyDataSetChanged()
                    }

                    R.id.post_send -> {
                        if (post.endsWith(".mp4")){
                            sendPost("video/*" , "Share Video" , post)
                        }
                        else {
                            sendPost("image/*" , "Share Image" , post)
                        }

                    }
                }
                return false
            }
        })
    }

    private fun sendPost(type : String , title : String, file : File) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.setType(type)
        intent.putExtra(Intent.EXTRA_STREAM , Uri.parse(file.path))
        context.startActivity(Intent.createChooser(intent , title))
    }


    override fun getItemCount(): Int {
        return posts.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image = itemView.findViewById<ImageView>(R.id.post_image)
        val postName = itemView.findViewById<TextView>(R.id.post_name)
        val options = itemView.findViewById<ImageView>(R.id.options_menu_img)
        val date = itemView.findViewById<TextView>(R.id.post_download_date)
        val size = itemView.findViewById<TextView>(R.id.post_size)

    }
}