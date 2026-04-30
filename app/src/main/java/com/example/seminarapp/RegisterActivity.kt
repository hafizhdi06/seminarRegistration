package com.example.seminarapp

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class RegisterActivity : AppCompatActivity() {

    private lateinit var tilRegNama: TextInputLayout
    private lateinit var tilRegUsername: TextInputLayout
    private lateinit var tilRegEmail: TextInputLayout
    private lateinit var tilRegPassword: TextInputLayout
    private lateinit var etRegNama: TextInputEditText
    private lateinit var etRegUsername: TextInputEditText
    private lateinit var etRegEmail: TextInputEditText
    private lateinit var etRegPassword: TextInputEditText
    private lateinit var btnRegister: MaterialButton
    private lateinit var tvGoLogin: android.widget.TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        tilRegNama     = findViewById(R.id.tilRegNama)
        tilRegUsername = findViewById(R.id.tilRegUsername)
        tilRegEmail    = findViewById(R.id.tilRegEmail)
        tilRegPassword = findViewById(R.id.tilRegPassword)
        etRegNama      = findViewById(R.id.etRegNama)
        etRegUsername  = findViewById(R.id.etRegUsername)
        etRegEmail     = findViewById(R.id.etRegEmail)
        etRegPassword  = findViewById(R.id.etRegPassword)
        btnRegister    = findViewById(R.id.btnRegister)
        tvGoLogin      = findViewById(R.id.tvGoLogin)

        // Real-time validation
        etRegNama.addTextChangedListener(object : SimpleTextWatcher() {
            override fun onTextChanged(s: CharSequence?, st: Int, b: Int, c: Int) {
                if (!s.isNullOrEmpty()) tilRegNama.error = null
            }
        })
        etRegUsername.addTextChangedListener(object : SimpleTextWatcher() {
            override fun onTextChanged(s: CharSequence?, st: Int, b: Int, c: Int) {
                if (!s.isNullOrEmpty()) tilRegUsername.error = null
            }
        })
        etRegEmail.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(s: Editable?) {
                val v = s.toString()
                if (v.isEmpty()) return
                tilRegEmail.error = if (v.contains("@")) null else "Email harus mengandung @"
            }
        })
        etRegPassword.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(s: Editable?) {
                val v = s.toString()
                if (v.isEmpty()) return
                tilRegPassword.error = if (v.length >= 6) null else "Password minimal 6 karakter"
            }
        })

        btnRegister.setOnClickListener { doRegister() }
        tvGoLogin.setOnClickListener { finish() }
    }

    private fun doRegister() {
        val nama     = etRegNama.text.toString().trim()
        val username = etRegUsername.text.toString().trim()
        val email    = etRegEmail.text.toString().trim()
        val password = etRegPassword.text.toString()

        var isValid = true

        if (nama.isEmpty()) {
            tilRegNama.error = "Nama tidak boleh kosong"
            isValid = false
        }
        if (username.isEmpty()) {
            tilRegUsername.error = "Username tidak boleh kosong"
            isValid = false
        }
        if (email.isEmpty()) {
            tilRegEmail.error = "Email tidak boleh kosong"
            isValid = false
        } else if (!email.contains("@")) {
            tilRegEmail.error = "Email harus mengandung @"
            isValid = false
        }
        if (password.length < 6) {
            tilRegPassword.error = "Password minimal 6 karakter"
            isValid = false
        }

        if (!isValid) return
        UserRepository.addUser(User(username, password, nama))
        Snackbar.make(btnRegister, "Registrasi berhasil! Silakan login.", Snackbar.LENGTH_LONG).show()

        // Kembali ke halaman login setelah 1.5 detik
        btnRegister.postDelayed({ finish() }, 1500)
    }
}
