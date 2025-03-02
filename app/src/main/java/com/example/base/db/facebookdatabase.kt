package com.example.base.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.base.data.Post
import com.example.base.data.users

class facebookdatabase(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DB_NAME, factory, DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        Log.d("DATABASE", "Création de la base de données...")

        val createTableUser = """
            CREATE TABLE IF NOT EXISTS $DB_TABLE (
                $DB_COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $DB_COLUMN_NAME VARCHAR(30),
                $DB_COLUMN_EMAIL VARCHAR(50),
                $DB_COLUMN_PASSWORD VARCHAR(50)
            )
        """.trimIndent()
        db?.execSQL(createTableUser)

        val createTablePost = """
            CREATE TABLE IF NOT EXISTS $DB_TABLE_POST (
                $DB_COLUMN_ID_POST INTEGER PRIMARY KEY AUTOINCREMENT,
                $DB_COLUMN_TITRE VARCHAR(30),
                $DB_COLUMN_DESCRIPTION TEXT,
                $DB_COLUMN_IMAGE BLOB,
                $LIKES INTEGER
            )
        """.trimIndent()
       // db?.execSQL(createTableUser)
        db?.execSQL(createTablePost)

        Log.d("DATABASE", " Tables créées avec succès")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        Log.d("DATABASE", "🔄 onUpgrade() appelé : Suppression des tables et recréation...")

       // db?.execSQL("DROP TABLE IF EXISTS user")
        db?.execSQL("DROP TABLE IF EXISTS $DB_TABLE_POST")
        onCreate(db)
    }


    fun adduser(user: users): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(DB_COLUMN_NAME, user.name)
            put(DB_COLUMN_EMAIL, user.email)
            put(DB_COLUMN_PASSWORD, user.password)
        }
        val result = db.insert(DB_TABLE, null, values)
        db.close()
        return result != -1L
    }
    fun getAllPosts(): ArrayList<Post> {
        val postList = ArrayList<Post>()
        val db = this.readableDatabase
        val selectQuery = "SELECT * FROM $DB_TABLE_POST"
        val cursor = db.rawQuery(selectQuery, null)
        if(cursor!=null){
            if (cursor.moveToFirst()) {
                do {
                    val id = cursor.getInt(cursor.getColumnIndexOrThrow(DB_COLUMN_ID_POST))
                    val titre = cursor.getString(cursor.getColumnIndexOrThrow(DB_COLUMN_TITRE))
                    val description = cursor.getString(cursor.getColumnIndexOrThrow(DB_COLUMN_DESCRIPTION))
                    val image = cursor.getBlob(cursor.getColumnIndexOrThrow(DB_COLUMN_IMAGE))
                    val jaime = cursor.getInt(cursor.getColumnIndexOrThrow(LIKES))
                    val post = Post(id, titre, description, image, jaime)
                    postList.add(post)

                }while (cursor.moveToNext())

            }

        }
        cursor.close()
        db.close()
        return postList
    }

    fun addpost(titre: String, description: String, image: ByteArray): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(DB_COLUMN_TITRE, titre)
            put(DB_COLUMN_DESCRIPTION, description)
            put(DB_COLUMN_IMAGE, image)
            put(LIKES, 0)
        }
        val result = db.insert(DB_TABLE_POST, null, values)
        db.close()

        if (result == -1L) {
            Log.e("DATABASE", "❌ Erreur d'insertion du post")
            return false
        } else {
            Log.d("DATABASE", "✅ Post ajouté avec succès, ID: $result")
            return true
        }
    }


    fun deletepost(id: Int): Boolean {
        val db = this.writableDatabase
        val result = db.delete(DB_TABLE_POST, "$DB_COLUMN_ID_POST = ?", arrayOf(id.toString()))
        db.close()
        return result != -1
    }

    fun finduser(email: String, password: String): users? {
        var user: users? = null
        val db = this.readableDatabase
        val cursor = db.query(
            DB_TABLE,
            null,
            "$DB_COLUMN_EMAIL = ? AND $DB_COLUMN_PASSWORD = ?",
            arrayOf(email, password),
            null,
            null,
            null
        )

        if (cursor.moveToFirst()) {
            val name = cursor.getString(1)
            val email = cursor.getString(2)
            val password = cursor.getString(3)
            user = users(name, email, password)
        }

        cursor.close()
        db.close()
        return user
    }

    fun updatepost(i: Post?) {
        if (i == null) return

        val db = this.writableDatabase
        val newLike = i.LIKES + 1

        val values = ContentValues().apply {
            put(DB_COLUMN_TITRE, i.titre)
            put(DB_COLUMN_DESCRIPTION, i.description)
            put(DB_COLUMN_IMAGE, i.image)
            put(LIKES, newLike)  // Mettre à jour le nombre de likes
        }

        val result = db.update(DB_TABLE_POST, values, "$DB_COLUMN_ID_POST = ?", arrayOf(i.id.toString()))

        if (result == -1) {
            Log.e("DATABASE", "❌ Erreur lors de la mise à jour du post")
        } else {
            Log.d("DATABASE", "✅ Post mis à jour avec succès, nouveaux likes : $newLike")
        }

        db.close()
    }



    companion object {
        private const val DB_NAME = "facebook.db"
        private const val DB_VERSION =111
        private const val DB_TABLE = "user"
        private const val DB_COLUMN_ID = "id"
        private const val DB_COLUMN_NAME = "name"
        private const val DB_COLUMN_EMAIL = "email"
        private const val DB_COLUMN_PASSWORD = "password"
        private const val DB_TABLE_POST = "post"
        private const val DB_COLUMN_ID_POST = "post_id"
        private const val DB_COLUMN_TITRE = "titre"
        private const val DB_COLUMN_DESCRIPTION = "description"
        private const val DB_COLUMN_IMAGE = "image"
        private const val LIKES = "jaime"
    }
}
