/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.final_proyek;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Perdi Ruhiyat
 */
public class KhsOperation extends Operations<Khs>{
    @Override
    public void tambah(Khs khs) {
        String query = "INSERT INTO khs (nim, kode_mk, grade) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, khs.getNim());
            stmt.setString(2, khs.getKodeMk());
            stmt.setString(3, khs.getGrade());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Khs khs) {
        String query = "UPDATE khs SET nim = ?, kode_mk = ?, grade = ? WHERE id_khs = ?";;
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, khs.getNim());
            stmt.setString(2, khs.getKodeMk());
            stmt.setString(3, khs.getGrade());
            stmt.setInt(4, khs.getIdKhs());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void hapus(Khs khs) {
        String query = "DELETE FROM khs WHERE id_khs = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, khs.getIdKhs());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ObservableList<Khs> showTable() {
        ObservableList<Khs> khsList = FXCollections.observableArrayList();
        String query = "SELECT k.id_khs,k.nim, k.kode_mk, m.mata_kuliah, m.sks, k.grade " +
                       "FROM khs k " +
                       "JOIN matakuliah m ON k.kode_mk = m.kode_mk";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Khs khs = new Khs(
                        rs.getInt("id_khs"),
                        rs.getString("nim"),
                        rs.getString("kode_mk"),
                        rs.getString("mata_kuliah"),
                        rs.getInt("sks"),
                        rs.getString("grade")
                    );
                khsList.add(khs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return khsList;
    }
}