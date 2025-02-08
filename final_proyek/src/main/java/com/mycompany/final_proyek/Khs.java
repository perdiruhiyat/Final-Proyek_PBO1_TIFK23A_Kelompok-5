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
public class Khs extends Operations{
    private int idKhs;
    private String nim;
    private String kodeMk;
    private String mataKuliah;
    private int sks;
    private String grade;
    private double bobot;
    private double gradeMutu;

    public Khs(){}
    
    public Khs(String nim, String kodeMk, String grade) {
        this.nim = nim;
        this.kodeMk = kodeMk;
        this.grade = grade;
    }
    
    public Khs(int idKhs, String nim, String kodeMk, String grade) {
        this.idKhs = idKhs;
        this.nim = nim;
        this.kodeMk = kodeMk;
        this.grade = grade;
    }
    
    public Khs(int idKhs, String nim, String kodeMk, String mataKuliah, int sks, String grade){
        this.idKhs = idKhs;
        this.nim = nim;
        this.kodeMk = kodeMk;
        this.mataKuliah = mataKuliah;
        this.sks = sks;
        this.grade = grade;
    }
    
    public Khs(int idKhs, String kodeMk, String mataKuliah, int sks, String grade){
        this.idKhs = idKhs;
        this.kodeMk = kodeMk;
        this.mataKuliah = mataKuliah;
        this.sks = sks;
        this.grade = grade;
        this.bobot = konversiGradeKeBobot(grade); 
        this.gradeMutu = this.bobot * this.sks;
    }

    public int getIdKhs() {
        return idKhs;
    }

    public void setIdKhs(int idKhs) {
        this.idKhs = idKhs;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getKodeMk() {
        return kodeMk;
    }

    public void setKodeMk(String kodeMk) {
        this.kodeMk = kodeMk;
    }
    
    public String getMataKuliah() { 
        return mataKuliah; 
    }
    
    public int getSks() { 
        return sks; 
    }
    
    public void setSks(int sks) {
        this.sks = sks;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public double konversiGradeKeBobot(String grade) {
        switch (grade) {
            case "A":
                return 4.00;
            case "AB":
                return 3.50;
            case "B":
                return 3.00;
            case "BC":
                return 2.50;
            case "C":
                return 2.00;
            case "D":
                return 1.00;
            case "E":
                return 0.00;
            default:
                return 0.00;
        }
    }
    public Double getBobot() {
        return bobot;
    }
    public double getNilaiMutu(){
        return gradeMutu;
    }
    
    @Override
    public void tambah() {
        String query = "INSERT INTO khs (nim, kode_matkul, nama_matkul, sks, grade) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, nim);
            stmt.setString(2, kodeMk);
            stmt.setString(3, mataKuliah);
            stmt.setInt(4, sks);
            stmt.setString(5, grade);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        String query = "UPDATE khs SET nama_matkul=?, sks=?, grade=? WHERE nim=? AND kode_matkul=?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, mataKuliah);
            stmt.setInt(2, sks);
            stmt.setString(3, grade);
            stmt.setString(4, nim);
            stmt.setString(5, kodeMk);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void hapus() {
        String query = "DELETE FROM khs WHERE nim=? AND kode_matkul=?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, nim);
            stmt.setString(2, kodeMk);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ObservableList<Khs> showTable() {
        ObservableList<Khs> list = FXCollections.observableArrayList();
        String query = "SELECT k.id_khs, k.nim, k.kode_mk, m.mata_kuliah, m.sks, k.grade " +
                       "FROM khs k " +
                       "JOIN matakuliah m ON k.kode_mk = m.kode_mk";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(new Khs(
                    rs.getInt("id_khs"),
                    rs.getString("nim"),
                    rs.getString("kode_mk"),
                    rs.getString("mata_kuliah"),
                    rs.getInt("sks"),
                    rs.getString("grade")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}