package com.example.base

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import com.example.base.data.Post

class PostsAdapter(
    context: Context,
    private val resource: Int,
    objects: ArrayList<Post>
) : ArrayAdapter<Post>(context, resource, objects) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        //  Réutilisation correcte de la vue
        val view = convertView ?: LayoutInflater.from(context).inflate(resource, parent, false)

        //  Récupération de l'élément actuel
        val poste = getItem(position)

        // Mise à jour du contenu de la vue
        val textView = view.findViewById<TextView>(R.id.tvTitre)
        val textView2 = view.findViewById<TextView>(R.id.textView2)
        val imageView = view.findViewById<ImageView>(R.id.imageView)
        val imagepopup = view.findViewById<ImageView>(R.id.imagepopup)

        textView?.text = poste?.titre ?: "Poste inconnu"
        textView2?.text = poste?.description ?: "Description inconnu"
        imageView?.setImageResource(poste?.image ?: R.drawable.img1)
        imagepopup.setOnClickListener{
            val popupMenu = PopupMenu(context, imagepopup)
            popupMenu.inflate(R.menu.listepopup)
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.itemshow -> {
                        val intent = Intent(context, PostdetailActivity2::class.java)
                        intent.putExtra("titre", poste?.titre)
                        context.startActivity(intent)
                    }

                    R.id.itemsupp -> {
                        remove(poste)
                        notifyDataSetChanged()

                    }

                }
                true
            }
            popupMenu.show()
        }

        return view
    }

}
