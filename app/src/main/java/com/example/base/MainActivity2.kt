package com.example.base

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.base.data.Post
import com.example.base.db.facebookdatabase
import java.io.ByteArrayOutputStream

class MainActivity2 : AppCompatActivity() {
    private lateinit var bouton: Button
    private lateinit var edittitre: EditText
    private lateinit var editdescription: EditText
    private lateinit var image: ImageView
    private lateinit var db: facebookdatabase
    private var bitmap: Bitmap? = null  // Correction pour éviter une NullPointerException

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        db = facebookdatabase(this, null)

        bouton = findViewById(R.id.button4)
        edittitre = findViewById(R.id.editTextText4)
        editdescription = findViewById(R.id.editTextText5)
        image = findViewById(R.id.imageView3)

        val launch = registerForActivityResult(ActivityResultContracts.GetContent()) { data ->
            data?.let {
                val inputStream = contentResolver.openInputStream(it)
                bitmap = BitmapFactory.decodeStream(inputStream)
                image.setImageBitmap(bitmap)
            }
        }

        image.setOnClickListener {
            launch.launch("image/*")
        }

        bouton.setOnClickListener {
            val titre = edittitre.text.toString().trim()
            val description = editdescription.text.toString().trim()

            if (titre.isEmpty() || description.isEmpty()) {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (bitmap != null) {
                val imageBlob = getBytes(bitmap)
                val post = Post(titre, description, imageBlob)

                val isInserted = db.addpost(post.titre, post.description, post.image)
                if (isInserted) {
                    Toast.makeText(this, "Post ajouté avec succès", Toast.LENGTH_SHORT).show()
                    edittitre.setText("")
                    editdescription.setText("")
                    finish()
                } else {
                    Toast.makeText(this, "Erreur lors de l'ajout du post", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Veuillez sélectionner une image", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getBytes(bitmap: Bitmap?): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
    }
}
