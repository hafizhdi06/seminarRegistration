package com.example.seminarapp

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity : AppCompatActivity() {

    private val seminarList = listOf(
        Pair("AI & Machine Learning", "15 Jun 2025 · Aula Utama UTB"),
        Pair("Cybersecurity & Ethical Hacking", "22 Jun 2025 · Lab Komputer Lt.3"),
        Pair("Cloud Computing & DevOps", "29 Jun 2025 · Ruang Seminar B")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val userName = intent.getStringExtra("USER_NAME") ?: "Mahasiswa"
        findViewById<TextView>(R.id.tvWelcomeName).text = userName

        // Tampilkan preview 3 seminar pertama
        val items = listOf(R.id.item1, R.id.item2, R.id.item3)
        items.forEachIndexed { index, viewId ->
            val itemView = findViewById<android.view.View>(viewId)
            itemView.findViewById<TextView>(R.id.tvSeminarName).text = seminarList[index].first
            itemView.findViewById<TextView>(R.id.tvSeminarDate).text = seminarList[index].second
        }
// Tombol Daftar Seminar
        findViewById<MaterialButton>(R.id.btnDaftarSeminar).setOnClickListener {
            startActivity(Intent(this, FormPendaftaranActivity::class.java).apply {
                putExtra("USER_NAME", userName)
            })
        }

// Tombol Seminar Saya
        findViewById<MaterialButton>(R.id.btnSeminarSaya).setOnClickListener {
            startActivity(Intent(this, SeminarSayaActivity::class.java))
        }


        findViewById<MaterialButton>(R.id.btnLogout).setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle("Konfirmasi Keluar")
                .setMessage("Apakah Anda yakin ingin keluar?")
                .setPositiveButton("Keluar") { _, _ ->
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
                .setNegativeButton("Batal", null)
                .show()

        }
    }
}
