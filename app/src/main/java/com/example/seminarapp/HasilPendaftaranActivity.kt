package com.example.seminarapp

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.button.MaterialButton
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HasilPendaftaranActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hasil_pendaftaran)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // Ambil data dari Intent
        val nama    = intent.getStringExtra("NAMA")    ?: "-"
        val email   = intent.getStringExtra("EMAIL")   ?: "-"
        val hp      = intent.getStringExtra("HP")      ?: "-"
        val jk      = intent.getStringExtra("JK")      ?: "-"
        val seminar = intent.getStringExtra("SEMINAR") ?: "-"

        // Simpan ke repository ← letaknya di sini, BUKAN di dalam listener
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        val tanggal = sdf.format(Date())
        SeminarRepository.tambahPendaftaran(
            PendaftaranData(nama, email, hp, jk, seminar, tanggal)
        )

        // Set data ke tampilan
        setRow(R.id.rowNama,    "Nama",          nama)
        setRow(R.id.rowEmail,   "Email",         email)
        setRow(R.id.rowHp,      "Nomor HP",      hp)
        setRow(R.id.rowJk,      "Jenis Kelamin", jk)
        setRow(R.id.rowSeminar, "Seminar",       seminar)

        // Tombol kembali ke halaman utama
        findViewById<MaterialButton>(R.id.btnKembali).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }
    }

    private fun setRow(viewId: Int, key: String, value: String) {
        val row = findViewById<android.view.View>(viewId)
        row.findViewById<TextView>(R.id.tvKey).text = key
        row.findViewById<TextView>(R.id.tvValue).text = value
    }
}