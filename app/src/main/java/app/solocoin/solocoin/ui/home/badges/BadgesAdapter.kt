package app.solocoin.solocoin.ui.home.badges

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.solocoin.solocoin.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_badge.view.*

/*
Created by Vijay Daita
 */

class BadgesAdapter(val items: ArrayList<Badge>, val context: Context): RecyclerView.Adapter<BadgesAdapter.BadgeHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BadgesAdapter.BadgeHolder {
        return BadgeHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_badge, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: BadgesAdapter.BadgeHolder, position: Int) {
        holder?.setUpView(items.get(position), context)
    }

    class BadgeHolder(v: View): RecyclerView.ViewHolder(v){
        private var view: View = v;

        fun setUpView(badge: Badge?, context: Context){
            var imgView: ImageView = view.findViewById(R.id.badge_image)
            var txtView: TextView = view.findViewById(R.id.badge_name)
            Picasso.get().load(badge?.imageUrl).into(imgView)
            txtView.setText(badge?.name)
            if(!(badge?.has!!)){
                imgView.alpha = 0.5f
                txtView.alpha = 0.75f
            }
            imgView.setOnClickListener {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "text/plain"
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.badge_invite_subject))
                shareIntent.putExtra(Intent.EXTRA_TEXT, context.getString(R.string.badge_invite_message_start) + badge?.name + context.getString(R.string.badge_invite_message_end))
                context.startActivity(Intent.createChooser(shareIntent, context.getString(R.string.invite_title)))
            }
        }
    }
}