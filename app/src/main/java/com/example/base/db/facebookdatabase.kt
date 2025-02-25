package com.example.base.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.base.UsersActivity
import com.example.base.data.users

class  facebookdatabase(var context: Context, var factory: SQLiteDatabase.CursorFactory?) : SQLiteOpenHelper(context, DB_NAME, factory, DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        //Creation des tables
        val createTableuser = """
            CREATE TABLE user(
            $DB_COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $DB_COLUMN_NAME VARCHAR(30),
            $DB_COLUMN_EMAIL VARCHAR(50),
            $DB_COLUMN_PASSWORD VARCHAR(50)
            )
             """.trimIndent()
        db?.execSQL(createTableuser)

    }

    override fun onUpgrade(
        db: SQLiteDatabase?,
        p1: Int,
        p2: Int
    ) {
        //Mise à jour des tables
        db?.execSQL("DROP TABLE IF EXISTS $DB_TABLE")
        onCreate(db)

    }

    fun adduser(user: users): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(DB_COLUMN_NAME, user.name)
        values.put(DB_COLUMN_EMAIL, user.email)
        values.put(DB_COLUMN_PASSWORD, user.password)
        val result = db.insert(DB_TABLE, null, values).toInt()
        db.close()
        return result != -1
    }

    fun finduser(email: String,password: String): users? {
        var user : users? = null
    val select = "SELECT * FROM $DB_TABLE WHERE $DB_COLUMN_EMAIL = ? AND $DB_COLUMN_PASSWORD = ?"
        val db = this.readableDatabase
        val selectionArgs = arrayOf(email, password)
        val cursor = db.query("$DB_TABLE", null, "$DB_COLUMN_EMAIL = ? AND $DB_COLUMN_PASSWORD = ?",selectionArgs ,null, null, null)
        if(cursor!=null) {
            if (cursor.moveToFirst()) {
                var id = cursor.getInt(0)
                var name = cursor.getString(1)
                var email = cursor.getString(2)
                var password = cursor.getString(3)
                val user = users(name, email, password)
                return user
            }
        }
        db.close()
        return user
    }

    companion object{
        private val DB_NAME = "facebook.db"
        private val DB_VERSION = 1
        private val DB_TABLE = "user"
        private val DB_COLUMN_ID = "id"
        private val DB_COLUMN_NAME = "name"
        private val DB_COLUMN_EMAIL = "email"
        private val DB_COLUMN_PASSWORD = "password"


    }

}