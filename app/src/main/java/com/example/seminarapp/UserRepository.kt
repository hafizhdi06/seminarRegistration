package com.example.seminarapp

data class User(val username: String, val password: String, val nama: String)

object UserRepository {

    // Data hardcode bawaan
    private val users = mutableListOf(
        User("mahasiswa", "12345", "Budi Santoso"),
        User("admin", "admin123", "Admin UTB")
    )

    fun getUser(username: String, password: String): User? {
        return users.find { it.username == username && it.password == password }
    }

    fun addUser(user: User) {
        users.add(user)
    }

    fun isUsernameTaken(username: String): Boolean {
        return users.any { it.username == username }
    }
}