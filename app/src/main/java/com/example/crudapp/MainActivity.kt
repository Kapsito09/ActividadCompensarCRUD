package com.example.crudapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var etName: EditText
    private lateinit var etEmail: EditText
    private lateinit var btnAddUser: Button
    private lateinit var btnUpdateUser: Button
    private lateinit var btnDeleteUser: Button
    private lateinit var recyclerViewUsers: RecyclerView
    private lateinit var usersAdapter: UsersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = DatabaseHelper(this)
        etName = findViewById(R.id.etName)
        etEmail = findViewById(R.id.etEmail)
        btnAddUser = findViewById(R.id.btnAddUser)
        btnUpdateUser = findViewById(R.id.btnUpdateUser)
        btnDeleteUser = findViewById(R.id.btnDeleteUser)
        recyclerViewUsers = findViewById(R.id.recyclerViewUsers)

        recyclerViewUsers.layoutManager = LinearLayoutManager(this)
        usersAdapter = UsersAdapter()
        recyclerViewUsers.adapter = usersAdapter

        loadUsers()

        btnAddUser.setOnClickListener {
            val name = etName.text.toString()
            val email = etEmail.text.toString()
            dbHelper.insertUser(name, email)
            loadUsers()
        }

        btnUpdateUser.setOnClickListener {
            // Lógica para actualizar usuario (ej. obtener ID y modificar)
            val name = etName.text.toString()
            val email = etEmail.text.toString()
            dbHelper.updateUser(1, name, email) // ID fijo solo por ejemplo
            loadUsers()
        }

        btnDeleteUser.setOnClickListener {
            // Lógica para eliminar usuario (ej. obtener ID y eliminar)
            dbHelper.deleteUser(1) // ID fijo solo por ejemplo
            loadUsers()
        }
    }

    private fun loadUsers() {
        val users = dbHelper.getAllUsers()
        usersAdapter.submitList(users)
    }
}