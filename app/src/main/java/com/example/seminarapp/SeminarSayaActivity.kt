package com.example.seminarapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SeminarSayaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seminar_saya)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        val recyclerView = findViewById<RecyclerView>(R.id.rvSeminarSaya)
        val layoutEmpty = findViewById<LinearLayout>(R.id.layoutEmpty)
        val tvJumlah = findViewById<TextView>(R.id.tvJumlahDaftar)

        val data = SeminarRepository.getPendaftaran()

        if (data.isEmpty()) {
            layoutEmpty.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
            tvJumlah.text = "0 seminar terdaftar"
        } else {
            layoutEmpty.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
            tvJumlah.text = "${data.size} seminar terdaftar"
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = SeminarSayaAdapter(data)
        }
    }
}

class SeminarSayaAdapter(private val list: List<PendaftaranData>) :
    RecyclerView.Adapter<SeminarSayaAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNomorUrut: TextView = view.findViewById(R.id.tvNomorUrut)
        val tvNamaSeminar: TextView = view.findViewById(R.id.tvNamaSeminar)
        val tvNamaPendaftar: TextView = view.findViewById(R.id.tvNamaPendaftar)
        val tvEmail: TextView = view.findViewById(R.id.tvEmailItem)
        val tvHp: TextView = view.findViewById(R.id.tvHpItem)
        val tvJk: TextView = view.findViewById(R.id.tvJkItem)
        val tvTanggal: TextView = view.findViewById(R.id.tvTanggalDaftar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_seminar_saya, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.tvNomorUrut.text = "${position + 1}"
        holder.tvNamaSeminar.text = item.seminar
        holder.tvNamaPendaftar.text = item.nama
        holder.tvEmail.text = item.email
        holder.tvHp.text = item.hp
        holder.tvJk.text = item.jenisKelamin
        holder.tvTanggal.text = "Didaftarkan: ${item.tanggalDaftar}"
    }

    override fun getItemCount() = list.size
}