package com.example.base

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.base.data.Post

class HomeActivity : AppCompatActivity() {
    lateinit var liste: ListView
    var postearray = ArrayList<Post>()
    lateinit var adapter: PostsAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        val email = intent.getStringExtra("email")

        val liste = findViewById<ListView>(R.id.listePoste)

        // Liste des postes
        postearray = arrayListOf(
            Post(
                "Titre1",
                "Une descritption du post 1 en bas de cette page est une bonne pratique",
                R.drawable.img1
            ),
            Post(
                "Titre2",
                "Une descritption du post 2 en bas de cette page est une bonne pratique",
                R.drawable.img2
            ),
            Post(
                "Titre3",
                "Une descritption du post 3 en bas de cette page est une bonne pratique",
                R.drawable.img3
            ),
            Post(
                "Titre4",
                "Une descritption du post 4 en bas de cette page est une bonne pratique",
                R.drawable.img4
            ),
            Post(
                "Titre5",
                "Une descritption du post 5 en bas de cette page est une bonne pratique",
                R.drawable.img5
            ),
        )

        // Utilisation du PostsAdapter personnalisé
         adapter = PostsAdapter(this, R.layout.itempost, postearray)
        liste.adapter = adapter

        liste.setOnItemClickListener { parent, view, position, id ->
            val poste = postearray[position]
            val intent = Intent(this, PostdetailActivity2::class.java)
            intent.putExtra("titre", poste.titre)
            startActivity(intent)
        }
        registerForContextMenu(liste)

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.homemenu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.itemAdd -> {
                Toast.makeText(this, "Ajouter", Toast.LENGTH_SHORT).show()

            }

            R.id.itemSet -> {
                Toast.makeText(this, "Setting", Toast.LENGTH_SHORT).show()

            }

            R.id.itemDeconnect -> {
               deconnexion()
            }
        }

        return super.onOptionsItemSelected(item)


    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menuInflater.inflate(R.menu.listepopup, menu)
        super.onCreateContextMenu(menu, v, menuInfo)
    }

    fun deconnexion(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirmation")
        builder.setMessage("Êtes-vous sûr de vouloir vous déconnecter ?")
        builder.setPositiveButton("Oui") { dialog, which ->
            val editor = getSharedPreferences("app_state", MODE_PRIVATE).edit()
            editor.putBoolean("authentification", false)
            editor.apply()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        builder.setNegativeButton("Non") { dialog, which ->
            dialog.dismiss()
        }
        builder.setNeutralButton("Annuler") { dialog, which ->
            dialog.dismiss()
        }

        val alert = builder.create()
        alert.show()
    }


}
