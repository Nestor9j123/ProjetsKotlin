package com.example.base

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.base.data.Post
import com.example.base.db.facebookdatabase

class HomeActivity : AppCompatActivity() {
    lateinit var liste: ListView
    var postearray = ArrayList<Post>()
    lateinit var adapter: PostsAdapter
    lateinit var db: facebookdatabase

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        val email = intent.getStringExtra("email")
        db = facebookdatabase(this, null)
        liste = findViewById(R.id.listePoste)


    }
    override fun onResume() {
        super.onResume()
        val emptyView = findViewById<TextView>(R.id.emptyView)
        liste.emptyView = emptyView

        // Récupération des posts
        postearray = db.getAllPosts()
        Log.d("DEBUG", "Nombre de posts récupérés : ${postearray.size}")
        adapter = PostsAdapter(this, R.layout.itempost, postearray)
        liste.adapter = adapter


        liste.setOnItemClickListener { _, _, position, _ ->
            val post = postearray[position]
            val intent = Intent(this, PostdetailActivity2::class.java)
            intent.putExtra("titre", post.titre)
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
                val intent = Intent(this, MainActivity2::class.java)
                startActivity(intent)

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
            finish()
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
