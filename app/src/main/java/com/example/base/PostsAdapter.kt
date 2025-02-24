package com.example.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

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
        textView?.text = poste?.titre ?: "Poste inconnu"
        textView2?.text = poste?.description ?: "Description inconnu"
        imageView?.setImageResource(poste?.image ?: R.drawable.img1)

        return view
    }
}
