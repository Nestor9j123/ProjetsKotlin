package com.example.base

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import com.example.base.data.Post
import com.example.base.db.facebookdatabase

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
        val textView3 = view.findViewById<TextView>(R.id.textView)
        val imageView = view.findViewById<ImageView>(R.id.imageView)
        val imagepopup = view.findViewById<ImageView>(R.id.imagepopup)
        val partage = view.findViewById<TextView>(R.id.textView3)


        textView?.text = poste?.titre ?: "Poste inconnu"
        textView2?.text = poste?.description ?: "Description inconnu"
        val bitmap = getBitmap(poste?.image)
        imageView.setImageBitmap(bitmap)
        val db = facebookdatabase(context, null)
        textView3.text = "${poste?.LIKES} j'aime"
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
                        val result = db.deletepost(poste?.id ?: 0)
                        if (result) {
                            remove(poste)
                            notifyDataSetChanged()
                        } else {
                            Toast.makeText(context, "Erreur lors de la suppression", Toast.LENGTH_SHORT).show()
                        }


                    }

                }
                true
            }
            popupMenu.show()
        }
        textView3.setOnClickListener {
            poste?.let {
                db.updatepost(it)  // Mise à jour du post dans la base de données
                val newLike = it.LIKES + 1
                it.LIKES = newLike  // Mise à jour en mémoire
                textView3.text = "${newLike} j'aime"
            }
        }
        partage.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, "J'ai partagé${poste?.id} sur Facebook !")
            val chooser = Intent.createChooser(intent, poste?.titre)
            context.startActivity(chooser)


        }


        return view
    }
    fun getBitmap(image: ByteArray?): Bitmap? {
        val bitmap = BitmapFactory.decodeByteArray(image, 0, image?.size ?: 0)
        return bitmap

    }

}
