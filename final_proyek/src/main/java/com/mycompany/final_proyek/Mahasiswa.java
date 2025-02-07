/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.final_proyek;

/**
 *
 * @author Perdi Ruhiyat
 */
public class Mahasiswa {
    private String nim;
    private String nama;
    private String prodi;
    private String kelas;
    private String jenisKelamin;
    private int semester;
    private String sistemKuliah;
    private int angkatan;

    public Mahasiswa(String nim, String nama, String kelas, String jenisKelamin, String prodi,
                     int semester, int angkatan, String sistemKuliah) {
        this.nim = nim;
        this.nama = nama;
        this.prodi = prodi;
        this.kelas = kelas;
        this.jenisKelamin = jenisKelamin;
        this.semester = semester;
        this.sistemKuliah = sistemKuliah;
        this.angkatan = angkatan;
    }

    public String getNim() {
        return nim;
    }
    
    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getNama() {
        return nama;
    }
    
    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getProdi() {
        return prodi;
    }

    public String getKelas() {
        return kelas;
    }
    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public int getSemester() {
        return semester;
    }

    public String getSistemKuliah() {
        return sistemKuliah;
    }

    public int getAngkatan() {
        return angkatan;
    }
}
