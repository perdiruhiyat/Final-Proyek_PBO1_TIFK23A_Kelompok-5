/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.final_proyek;

/**
 *
 * @author Perdi Ruhiyat
 */
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MahasiswaOperation extends Operations<Mahasiswa> {

    @Override
    public void tambah(Mahasiswa mhs) {
        String query = "INSERT INTO mahasiswa (nim, nama, kelas, jeniskelamin, prodi, semester, angkatan, sistem_kuliah) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, mhs.getNim());
            stmt.setString(2, mhs.getNama());
            stmt.setString(3, mhs.getKelas());
            stmt.setString(4, mhs.getJenisKelamin());
            stmt.setString(5, mhs.getProdi());
            stmt.setInt(6, mhs.getSemester());
            stmt.setInt(7, mhs.getAngkatan());
            stmt.setString(8, mhs.getSistemKuliah());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Mahasiswa mhs) {
        String query = "UPDATE mahasiswa SET nama=?, kelas=?, jeniskelamin=?, prodi=?, semester=?, angkatan=?, sistem_kuliah=? WHERE nim=?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, mhs.getNama());
            stmt.setString(2, mhs.getKelas());
            stmt.setString(3, mhs.getJenisKelamin());
            stmt.setString(4, mhs.getProdi());
            stmt.setInt(5, mhs.getSemester());
            stmt.setInt(6, mhs.getAngkatan());
            stmt.setString(7, mhs.getSistemKuliah());
            stmt.setString(8, mhs.getNim());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void hapus(Mahasiswa mhs) {
        String query = "DELETE FROM mahasiswa WHERE nim=?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, mhs.getNim());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ObservableList<Mahasiswa> showTable() {
        ObservableList<Mahasiswa> mahasiswaList = FXCollections.observableArrayList();
        String query = "SELECT * FROM mahasiswa";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Mahasiswa mhs = new Mahasiswa(
                        rs.getString("nim"),
                        rs.getString("nama"),
                        rs.getString("kelas"),
                        rs.getString("jeniskelamin"),
                        rs.getString("prodi"),
                        rs.getInt("semester"),
                        rs.getInt("angkatan"),
                        rs.getString("sistem_kuliah")
                );
                mahasiswaList.add(mhs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mahasiswaList;
    }
}
