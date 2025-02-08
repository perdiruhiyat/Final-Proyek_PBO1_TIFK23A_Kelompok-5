/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.final_proyek;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author Perdi Ruhiyat
 */
public class Mahasiswa extends Operations {
    private String nim, nama, kelas, jenisKelamin, prodi, sistemKuliah;
    private int semester, angkatan;

    public Mahasiswa() {}

    public Mahasiswa(String nim, String nama, String kelas, String jenisKelamin, String prodi, int semester, int angkatan, String sistemKuliah) {
        this.nim = nim;
        this.nama = nama;
        this.kelas = kelas;
        this.jenisKelamin = jenisKelamin;
        this.prodi = prodi;
        this.semester = semester;
        this.angkatan = angkatan;
        this.sistemKuliah = sistemKuliah;
    }

    // Getter dan Setter
    public String getNim() { return nim; }
    public void setNim(String nim) { this.nim = nim; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public String getKelas() { return kelas; }
    public void setKelas(String kelas) { this.kelas = kelas; }

    public String getJenisKelamin() { return jenisKelamin; }
    public void setJenisKelamin(String jenisKelamin) { this.jenisKelamin = jenisKelamin; }

    public String getProdi() { return prodi; }
    public void setProdi(String prodi) { this.prodi = prodi; }

    public int getSemester() { return semester; }
    public void setSemester(int semester) { this.semester = semester; }

    public int getAngkatan() { return angkatan; }
    public void setAngkatan(int angkatan) { this.angkatan = angkatan; }

    public String getSistemKuliah() { return sistemKuliah; }
    public void setSistemKuliah(String sistemKuliah) { this.sistemKuliah = sistemKuliah; }

    @Override
    public void tambah() {
        String query = "INSERT INTO mahasiswa (nim, nama, kelas, jeniskelamin, prodi, semester, angkatan, sistem_kuliah) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, nim);
            stmt.setString(2, nama);
            stmt.setString(3, kelas);
            stmt.setString(4, jenisKelamin);
            stmt.setString(5, prodi);
            stmt.setInt(6, semester);
            stmt.setInt(7, angkatan);
            stmt.setString(8, sistemKuliah);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        String query = "UPDATE mahasiswa SET nama=?, kelas=?, jeniskelamin=?, prodi=?, semester=?, angkatan=?, sistem_kuliah=? WHERE nim=?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, nama);
            stmt.setString(2, kelas);
            stmt.setString(3, jenisKelamin);
            stmt.setString(4, prodi);
            stmt.setInt(5, semester);
            stmt.setInt(6, angkatan);
            stmt.setString(7, sistemKuliah);
            stmt.setString(8, nim);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void hapus() {
        String query = "DELETE FROM mahasiswa WHERE nim=?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, nim);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ObservableList<Mahasiswa> showTable() {
        ObservableList<Mahasiswa> list = FXCollections.observableArrayList();
        String query = "SELECT * FROM mahasiswa";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(new Mahasiswa(
                    rs.getString("nim"),
                    rs.getString("nama"),
                    rs.getString("kelas"),
                    rs.getString("jeniskelamin"),
                    rs.getString("prodi"),
                    rs.getInt("semester"),
                    rs.getInt("angkatan"),
                    rs.getString("sistem_kuliah")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}