package com.example.base

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.base.data.users
import com.example.base.db.facebookdatabase

class UsersActivity : AppCompatActivity() {

    lateinit var db : facebookdatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_users)
        val editText = findViewById<EditText>(R.id.editTextText)
        val editText2 = findViewById<EditText>(R.id.editTextText2)
        val editText3 = findViewById<EditText>(R.id.editTextTextPassword)
        val editText4 = findViewById<EditText>(R.id.editTextTextPassword2)
        val button = findViewById<Button>(R.id.button3)
        val textView = findViewById<TextView>(R.id.textViews)

        db = facebookdatabase(this, null)

        button.setOnClickListener{
            textView.visibility = View.GONE
            textView.text= ""
            val name = editText.text.toString()
            val email = editText2.text.toString()
            val password = editText3.text.toString()
            val password2 = editText4.text.toString()

            if(name.isEmpty() || email.isEmpty() || password.isEmpty() || password2.isEmpty()){
                textView.visibility= View.VISIBLE
                textView.text= "Veuillez remplir tous les champs"
                }
            else if(password != password2){
                textView.visibility= View.VISIBLE
                textView.text= "Les mots de passe ne correspondent pas"
            }else{
                editText.text.clear()
                editText2.text.clear()
                editText3.text.clear()
                editText4.text.clear()
                val user = users(name, email, password)
                val isInserted = db.adduser(user)
                if (isInserted){
                    Toast.makeText(this, "Utilisateur ajouté avec succès", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, HomeActivity::class.java)
                    intent.putExtra("email",email)
                    startActivity(intent)

                }

            }

        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}