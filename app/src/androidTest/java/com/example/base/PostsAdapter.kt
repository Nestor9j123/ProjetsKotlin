package com.example.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class PostsAdapter(
    context: Context,
    private val resource: Int,                  // 🔥 Correction ici : propriété privée pour resource
    objects: ArrayList<String>
) : ArrayAdapter<String>(context, resource, objects) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(resource, parent, false)
        val poste = getItem(position)
        val textView = view.findViewById<TextView>(android.R.id.text1)
        textView.text = poste
        return view
    }
}
