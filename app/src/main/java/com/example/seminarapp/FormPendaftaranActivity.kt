package com.example.seminarapp

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class FormPendaftaranActivity : AppCompatActivity() {

    // Daftar seminar hardcode sesuai ketentuan soal (minimal 5)
    private val daftarSeminar = arrayOf(
        "-- Pilih Seminar --",
        "Artificial Intelligence & Machine Learning",
        "Cybersecurity & Ethical Hacking",
        "Cloud Computing & DevOps",
        "Mobile App Development (Android & iOS)",
        "Data Science & Big Data Analytics",
        "Internet of Things (IoT) Terapan"
    )

    private lateinit var tilNama: TextInputLayout
    private lateinit var tilEmail: TextInputLayout
    private lateinit var tilHp: TextInputLayout
    private lateinit var etNama: TextInputEditText
    private lateinit var etEmail: TextInputEditText
    private lateinit var etHp: TextInputEditText
    private lateinit var rgJenisKelamin: RadioGroup
    private lateinit var tvJkError: TextView
    private lateinit var spinnerSeminar: Spinner
    private lateinit var tvSeminarError: TextView
    private lateinit var cbAgree: CheckBox
    private lateinit var tvAgreeError: TextView
    private lateinit var btnSubmit: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_pendaftaran)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        tilNama        = findViewById(R.id.tilNama)
        tilEmail       = findViewById(R.id.tilEmail)
        tilHp          = findViewById(R.id.tilHp)
        etNama         = findViewById(R.id.etNama)
        etEmail        = findViewById(R.id.etEmail)
        etHp           = findViewById(R.id.etHp)
        rgJenisKelamin = findViewById(R.id.rgJenisKelamin)
        tvJkError      = findViewById(R.id.tvJkError)
        spinnerSeminar = findViewById(R.id.spinnerSeminar)
        tvSeminarError = findViewById(R.id.tvSeminarError)
        cbAgree        = findViewById(R.id.cbAgree)
        tvAgreeError   = findViewById(R.id.tvAgreeError)
        btnSubmit      = findViewById(R.id.btnSubmit)

        setupSpinner()
        setupRealTimeValidation()

        btnSubmit.setOnClickListener { validateAndSubmit() }
    }

    private fun setupSpinner() {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, daftarSeminar)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerSeminar.adapter = adapter

        spinnerSeminar.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p: AdapterView<*>?, v: View?, pos: Int, id: Long) {
                if (pos > 0) {
                    tvSeminarError.visibility = View.GONE
                }
            }
            override fun onNothingSelected(p: AdapterView<*>?) {}
        }
    }

    private fun setupRealTimeValidation() {
        // Nama — real-time
        etNama.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(s: Editable?) {
                val v = s.toString().trim()
                tilNama.error = if (v.isEmpty()) "Nama tidak boleh kosong" else null
            }
        })

        // Email — real-time
        etEmail.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(s: Editable?) {
                val v = s.toString().trim()
                tilEmail.error = when {
                    v.isEmpty()       -> "Email tidak boleh kosong"
                    !v.contains("@") -> "Email harus mengandung @"
                    else              -> null
                }
            }
        })

        // Nomor HP — real-time dengan semua aturan validasi
        etHp.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(s: Editable?) {
                val v = s.toString().trim()
                tilHp.error = validateHp(v)
            }
        })

        // Jenis Kelamin
        rgJenisKelamin.setOnCheckedChangeListener { _, _ ->
            tvJkError.visibility = View.GONE
        }

        // Checkbox
        cbAgree.setOnCheckedChangeListener { _, checked ->
            if (checked) tvAgreeError.visibility = View.GONE
        }
    }

    private fun validateHp(v: String): String? {
        return when {
            v.isEmpty()             -> "Nomor HP tidak boleh kosong"
            !v.all { it.isDigit() } -> "Nomor HP hanya boleh berisi angka"
            !v.startsWith("08")     -> "Nomor HP harus diawali dengan 08"
            v.length < 10           -> "Nomor HP minimal 10 digit"
            v.length > 13           -> "Nomor HP maksimal 13 digit"
            else                    -> null
        }
    }

    private fun validateAndSubmit() {
        val nama   = etNama.text.toString().trim()
        val email  = etEmail.text.toString().trim()
        val hp     = etHp.text.toString().trim()
        val semPos = spinnerSeminar.selectedItemPosition

        var isValid = true

        // Validasi Nama
        if (nama.isEmpty()) {
            tilNama.error = "Nama tidak boleh kosong"
            isValid = false
        }

        // Validasi Email
        val emailErr = when {
            email.isEmpty()       -> "Email tidak boleh kosong"
            !email.contains("@") -> "Email harus mengandung @"
            else                  -> null
        }
        if (emailErr != null) { tilEmail.error = emailErr; isValid = false }

        // Validasi HP
        val hpErr = validateHp(hp)
        if (hpErr != null) { tilHp.error = hpErr; isValid = false }

        // Validasi Jenis Kelamin
        if (rgJenisKelamin.checkedRadioButtonId == -1) {
            tvJkError.text = "Pilih jenis kelamin terlebih dahulu"
            tvJkError.visibility = View.VISIBLE
            isValid = false
        }

        // Validasi Spinner Seminar
        if (semPos == 0) {
            tvSeminarError.text = "Pilih seminar terlebih dahulu"
            tvSeminarError.visibility = View.VISIBLE
            isValid = false
        }

        // Validasi Checkbox
        if (!cbAgree.isChecked) {
            tvAgreeError.text = "Anda harus menyetujui pernyataan ini"
            tvAgreeError.visibility = View.VISIBLE
            isValid = false
        }

        if (!isValid) return

        // Semua valid → tampilkan Dialog Konfirmasi
        showConfirmationDialog(nama, email, hp, semPos)
    }

    private fun showConfirmationDialog(nama: String, email: String, hp: String, semPos: Int) {
        val jk      = if (rgJenisKelamin.checkedRadioButtonId == R.id.rbLakiLaki) "Laki-laki" else "Perempuan"
        val seminar = daftarSeminar[semPos]

        MaterialAlertDialogBuilder(this)
            .setTitle("Konfirmasi Pendaftaran")
            .setMessage("Apakah data yang Anda isi sudah benar?")
            .setPositiveButton("Ya") { _, _ ->
                // Lanjut ke halaman hasil
                val intent = Intent(this, HasilPendaftaranActivity::class.java).apply {
                    putExtra("NAMA", nama)
                    putExtra("EMAIL", email)
                    putExtra("HP", hp)
                    putExtra("JK", jk)
                    putExtra("SEMINAR", seminar)
                }
                startActivity(intent)
            }
            .setNegativeButton("Tidak", null)
            .show()
    }
}
