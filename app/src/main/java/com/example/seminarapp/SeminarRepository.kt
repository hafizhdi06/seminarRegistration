package com.example.seminarapp

data class PendaftaranData(
    val nama: String,
    val email: String,
    val hp: String,
    val jenisKelamin: String,
    val seminar: String,
    val tanggalDaftar: String
)

object SeminarRepository {
    private val daftarPendaftaran = mutableListOf<PendaftaranData>()

    fun tambahPendaftaran(data: PendaftaranData) {
        daftarPendaftaran.add(data)
    }

    fun getPendaftaran(): List<PendaftaranData> {
        return daftarPendaftaran.toList()
    }

    fun jumlahPendaftaran(): Int {
        return daftarPendaftaran.size
    }
}