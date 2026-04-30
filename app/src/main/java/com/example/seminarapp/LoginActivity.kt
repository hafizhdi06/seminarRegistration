package com.example.seminarapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {

    private lateinit var tilUsername: TextInputLayout
    private lateinit var tilPassword: TextInputLayout
    private lateinit var etUsername: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var btnLogin: MaterialButton
    private lateinit var tvGoRegister: android.widget.TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        tilUsername  = findViewById(R.id.tilUsername)
        tilPassword  = findViewById(R.id.tilPassword)
        etUsername   = findViewById(R.id.etUsername)
        etPassword   = findViewById(R.id.etPassword)
        btnLogin     = findViewById(R.id.btnLogin)
        tvGoRegister = findViewById(R.id.tvGoRegister)

        btnLogin.setOnClickListener { doLogin() }

        tvGoRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        etUsername.addTextChangedListener(object : SimpleTextWatcher() {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                tilUsername.error = null
            }
        })
        etPassword.addTextChangedListener(object : SimpleTextWatcher() {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                tilPassword.error = null
            }
        })
    }

    private fun doLogin() {
        val username = etUsername.text.toString().trim()
        val password = etPassword.text.toString()

        var isValid = true
        if (username.isEmpty()) {
            tilUsername.error = "Username tidak boleh kosong"
            isValid = false
        }
        if (password.isEmpty()) {
            tilPassword.error = "Password tidak boleh kosong"
            isValid = false
        }
        if (!isValid) return

        // Cek dari data hardcode DAN data yang didaftarkan
        val user = UserRepository.getUser(username, password)
        if (user == null) {
            tilUsername.error = " "
            tilPassword.error = "Username atau password salah"
            return
        }

        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("USER_NAME", user.nama)
        startActivity(intent)
        finish()
    }
}