package com.example.crudapp

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "crud_database"
        private const val DATABASE_VERSION = 1
        const val TABLE_NAME = "users"
        const val COL_ID = "id"
        const val COL_NAME = "name"
        const val COL_EMAIL = "email"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = """
            CREATE TABLE $TABLE_NAME (
                $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_NAME TEXT,
                $COL_EMAIL TEXT
            )
        """.trimIndent()
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    // Métodos para las operaciones CRUD
    fun insertUser(name: String, email: String) {
        val db = writableDatabase
        val query = "INSERT INTO $TABLE_NAME ($COL_NAME, $COL_EMAIL) VALUES ('$name', '$email')"
        db.execSQL(query)
        db.close()
    }

    fun getAllUsers(): List<User> {
        val users = mutableListOf<User>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(COL_ID))
                val name = cursor.getString(cursor.getColumnIndex(COL_NAME))
                val email = cursor.getString(cursor.getColumnIndex(COL_EMAIL))
                users.add(User(id, name, email))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return users
    }

    fun updateUser(id: Int, name: String, email: String) {
        val db = writableDatabase
        val query = "UPDATE $TABLE_NAME SET $COL_NAME = '$name', $COL_EMAIL = '$email' WHERE $COL_ID = $id"
        db.execSQL(query)
        db.close()
    }

    fun deleteUser(id: Int) {
        val db = writableDatabase
        val query = "DELETE FROM $TABLE_NAME WHERE $COL_ID = $id"
        db.execSQL(query)
        db.close()
    }
}
